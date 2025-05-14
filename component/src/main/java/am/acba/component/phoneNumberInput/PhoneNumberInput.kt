package am.acba.component.phoneNumberInput

import am.acba.component.R
import am.acba.component.databinding.CountryPickerLayoutBinding
import am.acba.component.databinding.PhoneNumberInputBinding
import am.acba.component.dialog.ContactBooksBottomSheetDialog
import am.acba.component.dialog.CountryBottomSheetDialog
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.extensions.log
import am.acba.component.extensions.openKeyboard
import am.acba.component.extensions.saveCountryLastAction
import am.acba.component.extensions.shakeViewHorizontally
import am.acba.component.extensions.vibrate
import am.acba.component.input.PrimaryInput.Companion.SHAKE_AMPLITUDE
import am.acba.component.input.PrimaryInput.Companion.VIBRATION_AMPLITUDE
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipboardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import android.text.Editable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hbb20.CCPCountry
import com.hbb20.CountryCodePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("ClickableViewAccessibility")
class PhoneNumberInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, 0) {

    var enableErrorAnimation = false
    private val binding by lazy { PhoneNumberInputBinding.inflate(context.inflater(), this, false) }

    private val ccpBinding by lazy {
        CountryPickerLayoutBinding.inflate(
            context.inflater(),
            this,
            false
        )
    }
    private lateinit var countriesList: List<CountryModel>
    private var topCountryChipList: MutableList<CountryModel> = mutableListOf()
    private var isFocusable = false
    private var isFirstFocusable = true
    private var isKeyboardActionClicked = false
    private var isValidNumber = true
    private var countryTopShips: String? = null
    private val CONTACT_PERMISSION_REQUEST = 100
    private val PICK_CONTACT_REQUEST = 101
    lateinit var fragment: Fragment
    var onSelectedContactSet: (() -> Unit)? = null
    private var onAcbaContactClick: (() -> Unit)? = null
    private var searchInputHint = context.getString(R.string.search)
    private var bottomSheetTitle: String = context.getString(R.string.select_country_code)
    private var phoneBookBottomSheetTitle = context.getString(R.string.select_phone_number)


    var errorText: String? = null
    var helpText: String? = null

    var onFocusChanged: ((isFocusable: Boolean) -> Unit)? = null
    var doAfterTextChanged: ((text: Editable?) -> Unit)? = null

    init {
        addView(binding.root)
        context.obtainStyledAttributes(attrs, R.styleable.PhoneNumberInput).apply {
            try {
                errorText = getString(R.styleable.PhoneNumberInput_phoneInputErrorText)
                helpText = getString(R.styleable.PhoneNumberInput_phoneInputHelpText)
                countryTopShips = getString(R.styleable.PhoneNumberInput_countryTopChips)
                enableErrorAnimation = getBoolean(R.styleable.PhoneNumberInput_enableErrorAnimation, false)
                searchInputHint = getString(R.styleable.PhoneNumberInput_phoneNumberSearchInputHint) ?: searchInputHint
                bottomSheetTitle = getString(R.styleable.PhoneNumberInput_phoneNumberInputBottomSheetTitle) ?: bottomSheetTitle
                phoneBookBottomSheetTitle = getString(R.styleable.PhoneNumberInput_phoneBookBottomSheetTitle) ?: phoneBookBottomSheetTitle
            } finally {
                recycle()
            }
        }

        if (!isInEditMode) {
            ccpBinding.countryCodeLib.registerCarrierNumberEditText(binding.phoneNumber)
            ccpBinding.countryCodeLib.setNumberAutoFormattingEnabled(true)
            countriesMapping()
            ccpBinding.countryCodeLib.setPhoneNumberValidityChangeListener { if (!isFirstFocusable) isValidNumber(it) }
            ccpBinding.countryCodeLib.changeDefaultLanguage(CountryCodePicker.Language.RUSSIAN)
        }
        setupHelpErrorText()
        setupBackgrounds()
        binding.phoneNumber.initListeners()
        binding.countryCodeLayout.setOnClickListener { openCountryDialog() }
        binding.icPhoneBook.setOnClickListener { contactIconClick() }
        setCountryChipsFromDigital()
        copyPaste()
    }

    private fun CutCopyPasteEditText.initListeners() {
        doOnTextChanged { text, _, _, _ ->
            if (text?.isEmpty() == true) {
                isFirstFocusable = true
                isValidNumber(true)
            }
        }
        doAfterTextChanged { view -> doAfterTextChanged?.invoke(view) }
        onKeyboardDoneButtonClick { setErrorAnimation() }
        onKeyboardActionButtonClick { actionId ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                isKeyboardActionClicked = true
                setErrorAnimation()
            }
        }
    }

    private fun copyPaste() {
        binding.phoneNumber.setOnCutCopyPasteListener { action ->
            when (action) {
                android.R.id.paste -> pastTextWithClipBoard()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun pastTextWithClipBoard() {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboardManager.hasPrimaryClip()) {
            val clip = clipboardManager.primaryClip
            if (clip != null && clip.itemCount > 0) {
                val pastedText = clip.getItemAt(0).text.toString()
                pastedText.log("pastedText")
                setPhoneNumber(pastedText)
            }
        }
    }

    private fun setupBackgrounds() {
        binding.phoneNumber.setOnFocusChangeListener { _, isFocusable ->
            this.isFocusable = isFocusable

            if (!isFocusable) {
                isValidNumber(ccpBinding.countryCodeLib.isValidFullNumber)
            }
            if (isValidNumber) {
                setupBackgroundByFocusable()
            } else {
                if (isKeyboardActionClicked) setErrorAnimation()
                setErrorBackground()
            }
            onFocusChanged?.invoke(isFocusable)
        }
    }

    // Ex..hy,ru,us
    private fun setCountryChipsFromDigital() {
        val topChipsCountryNameCodes = countryTopShips?.split(",")
        countriesList.forEach { country ->
            topChipsCountryNameCodes?.forEach {
                if (country.nameCode == it.uppercase())
                    topCountryChipList.add(country)
            }
        }
    }

    fun handleAcbaContactClick(onAcbaContactClick: () -> Unit) {
        this.onAcbaContactClick = onAcbaContactClick
    }

    fun getFullNumber(): String {
        return ccpBinding.countryCodeLib.fullNumber
    }

    fun getFormattedFullNumber(): String {
        return ccpBinding.countryCodeLib.formattedFullNumber
    }

    fun getFullNumberWithPlus(): String {
        return ccpBinding.countryCodeLib.fullNumberWithPlus
    }

    fun isPhoneValid(): Boolean {
        return ccpBinding.countryCodeLib.isValidFullNumber
    }

    private fun countriesMapping() {
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val simCountryISO = telephonyManager.simCountryIso.ifEmpty { "am" }

        Log.i("simCountry", simCountryISO)
        countriesList = ArrayList<CountryModel>().let {
            CCPCountry.getLibraryMasterCountryList(context, CountryCodePicker.Language.ENGLISH)
                .map { country ->
                    CountryModel(
                        englishName = country.englishName,
                        flagResId = country.flagID,
                        name = country.name,
                        nameCode = country.nameCode,
                        phoneCode = country.phoneCode
                    )
                }
        }.onEach {   //Default country
            if (it.nameCode?.lowercase() == simCountryISO) {
                selectCountry(it)
                it.isSelected = true
            }
        }
    }

    private fun setupHelpErrorText() {
        if (isValidNumber) {
            binding.helpText.setTextColor(context.getColorFromAttr(R.attr.contentPrimaryTonal1))
            binding.helpText.text = helpText
            binding.icError.visibility = GONE
            binding.helpText.isVisible = helpText?.isNotEmpty() == true
        } else {
            binding.helpText.text = errorText
            binding.helpText.setTextColor(context.getColorFromAttr(R.attr.contentDangerTonal1))
            binding.icError.isVisible = !errorText.isNullOrEmpty()
        }
    }

    private fun setErrorBackground() {
        isFirstFocusable = false
        binding.countryCodeLayout.background = ContextCompat.getDrawable(
            context,
            R.drawable.background_primary_input_error_left_border
        )
        binding.phoneNumberLayout.background = ContextCompat.getDrawable(
            context,
            R.drawable.background_primary_input_error_right_border
        )
    }

    private fun setupBackgroundByFocusable() {
        binding.phoneNumberLayout.background = ContextCompat.getDrawable(
            context,
            if (isFocusable) R.drawable.background_primary_input_right_border else R.drawable.background_primary_input_right_corner
        )
        binding.countryCodeLayout.background = ContextCompat.getDrawable(
            context,
            if (isFocusable) R.drawable.background_primary_input_left_border else R.drawable.background_primary_input_left_corner
        )
    }

    private fun handleCountryDialogSelect(countryModel: CountryModel) {
        selectCountry(countryModel)
        isValidNumber(ccpBinding.countryCodeLib.isValidFullNumber)
    }

    @SuppressLint("SetTextI18n")
    private fun selectCountry(countryModel: CountryModel) {
        binding.countryCode.text = "+${countryModel.phoneCode}"
        ccpBinding.countryCodeLib.setCountryForNameCode(countryModel.nameCode)
        Glide.with(context).asBitmap().load(countryModel.flagResId)
            .apply(RequestOptions.circleCropTransform().override(22.dpToPx(), 22.dpToPx()))
            .into(binding.countryFlag)
    }

    private fun handleBookClickActions(type: Int) {
        when (type) {
            //ACBA Contacts
            1 -> {
                onAcbaContactClick?.invoke()
            }
            // Phone Book
            2 -> {
                pickContactPromPhoneBook()
            }
        }
    }


    private fun pickContactPromPhoneBook() {
        val permissionGranted = checkContactPermission()
        if (permissionGranted) {
            pickContact()
        } else {
            requestContactPermission()
        }
    }

    private fun checkContactPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactPermission() {
        fragment.requestPermissions(
            arrayOf(Manifest.permission.READ_CONTACTS),
            CONTACT_PERMISSION_REQUEST
        )
    }

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == CONTACT_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickContact()
            }
        }
    }


    private fun isValidNumber(isValid: Boolean) {
        isValidNumber = if (binding.phoneNumber.text?.isNotEmpty() == true) isValid else true
        if (!isValid && binding.phoneNumber.text?.isNotEmpty() == true) {
            setErrorBackground()
        } else {
            setupBackgroundByFocusable()
        }
        setupHelpErrorText()
    }


    private fun contactIconClick() {
        val bundle = Bundle()
        bundle.putString("title", phoneBookBottomSheetTitle)
        ContactBooksBottomSheetDialog.show(getFragmentManager(), bundle, ::handleBookClickActions)
    }

    override fun setEnabled(isEnable: Boolean) {
        binding.countryCodeLayout.isEnabled = isEnable
        binding.phoneNumberLayout.isEnabled = isEnable
        binding.icPhoneBook.isEnabled = isEnable
        binding.phoneNumber.isEnabled = isEnable
        binding.countryCode.setTextColor(context.getColorFromAttr(if (isEnable) R.attr.contentPrimary else R.attr.contentPrimaryTonal1Disable))
        binding.icArrow.imageTintList =
            context.getColorStateListFromAttr(if (isEnable) R.attr.contentPrimary else R.attr.contentPrimaryTonal1Disable)
        binding.phoneNumber.setTextColor(context.getColorFromAttr(if (isEnable) R.attr.contentPrimary else R.attr.contentPrimaryTonal1Disable))
        binding.phoneNumber.setHintTextColor(context.getColorFromAttr(if (isEnable) R.attr.contentPrimaryTonal1 else R.attr.contentPrimaryTonal1Disable))
        binding.icPhoneBook.imageTintList =
            context.getColorStateListFromAttr(if (isEnable) R.attr.contentPrimary else R.attr.contentPrimaryTonal1Disable)
        binding.countryFlag.alpha = if (isEnable) 1f else 0.4f
        binding.helpText.setTextColor(context.getColorFromAttr(if (isEnable) R.attr.contentPrimaryTonal1 else R.attr.contentPrimaryTonal1Disable))
    }

    fun fixCountryCode() {
        binding.icArrow.isVisible = false
        binding.countryCodeLayout.isClickable = false
    }

    private fun openCountryDialog() {
        val bundle = Bundle()
        bundle.putBoolean("needToSavActionsOnDB", true)
        bundle.putBoolean("isSearchInputVisible", true)
        bundle.putString("title", bottomSheetTitle)
        bundle.putInt("bottomSheetType", 1)
        bundle.putParcelableArrayList("CountriesList", countriesList as ArrayList)
        CountryBottomSheetDialog.show(
            getFragmentManager(),
            bundle,
            ::handleCountryDialogSelect,
            topCountryChipList
        )
    }

    private fun getFragmentManager(): FragmentManager {
        try {
            val fragmentActivity = context as? FragmentActivity
            fragmentActivity?.let {
                val fragmentManager = it.supportFragmentManager
                return fragmentManager
            }
        } catch (e: ClassCastException) {
            Log.d(TAG, "Can't get the fragment manager with this")
        }
        return getFragmentManager()
    }

    private fun findFragmentParent() {
        var parent = this.parent
        while (parent != null) {
            if (parent is FragmentContainerView) {
                fragment = findFragment(parent.id).childFragmentManager.fragments[0]
                break
            }
            parent = if (parent is View) {
                parent.getParent()
            } else {
                null
            }
        }
    }

    private fun findFragment(containerViewId: Int): Fragment {
        val fragment = getFragmentManager().findFragmentById(containerViewId)!!
        return fragment
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        findFragmentParent()
    }

    private fun pickContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        fragment.startActivityForResult(intent, PICK_CONTACT_REQUEST)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { contactUri ->
                fragment.lifecycleScope.launch {
                    ContactManager.getContactFromUri(contactUri, context).catch {
                        it.localizedMessage.log("ContactTag", "fail -> ")
                        // onContactResultFailed()
                    }.collectLatest { contact ->
                        withContext(Dispatchers.Main) {
                            contact.phoneNumber.log("ContactTag", "Success -> ")
                            contact.name.log("ContactTag", "Success -> ")
                            contact.email.log("ContactTag", "Success -> ")
                            setPhoneNumber(contact.phoneNumber ?: "")
                            onSelectedContactSet?.invoke()
                        }
                    }
                }
            }
        }
    }

    fun setPhoneNumber(phoneNumber: String) {
        binding.phoneNumber.setText(phoneNumber)
        ccpBinding.countryCodeLib.fullNumber = phoneNumber
        ccpBinding.countryCodeLib.selectedCountryNameCode
        countriesList.forEach {
            if (it.nameCode == ccpBinding.countryCodeLib.selectedCountryNameCode) {
                context.saveCountryLastAction(it)
                selectCountry(it)
            }
        }
    }

    private fun setErrorAnimation() {
        if (enableErrorAnimation && !isValidNumber) {
            isKeyboardActionClicked = false
            context.vibrate(VIBRATION_AMPLITUDE)
            shakeViewHorizontally(SHAKE_AMPLITUDE)
        }
    }

    fun openKeyboard() {
        binding.phoneNumber.openKeyboard(context)
    }
}