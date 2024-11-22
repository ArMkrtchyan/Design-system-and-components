package am.acba.component.cardInput

import am.acba.component.R
import am.acba.component.databinding.CardInputBinding
import am.acba.component.extensions.getColorFromAttr
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.extensions.inflater
import am.acba.component.extensions.log
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ClipboardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Editable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent
import java.net.IDN


@SuppressLint("CustomViewStyleable")
class PrimaryCardInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, 0) {
    private val binding by lazy { CardInputBinding.inflate(context.inflater(), this, false) }

    private var isFormatting = false
    private var current = ""
    private var errorText: String
    private var hintText: String
    private var helpText: String
    private var maxLength: Int
    private var minLength: Int
    private var logoDetectCount: Int

    private var logoBackground: Drawable?
    private var errorIcon: Drawable?
    private var endIcon: Drawable?
    private var errorIconColor: ColorStateList? = null
    private var isValidNumber = true
    private var isFocusable = false
    private var cardInputEndIconAction: Int

    private val SCAN_REQUEST_CODE = 1
    private val CAMERA_PERMISSION_CODE = 1002

    private var onCardScanDataAction: ((CardDataModel) -> Unit)? = null
    lateinit var fragment: Fragment

    init {
        addView(binding.root)
        context.obtainStyledAttributes(attrs, R.styleable.CardInput).apply {
            try {
                errorText = getString(R.styleable.CardInput_cardInputErrorText) ?: ""
                hintText = getString(R.styleable.CardInput_cardInputHintText) ?: ""
                helpText = getString(R.styleable.CardInput_cardInputHelpText) ?: ""
                maxLength = getInt(R.styleable.CardInput_cardInputMaxLength, 0)
                minLength = getInt(R.styleable.CardInput_cardInputMinLength, 0)
                logoDetectCount = getInt(R.styleable.CardInput_cardInputLogoDetectCount, 0)
                logoBackground = getDrawable(R.styleable.CardInput_cardInputLogoBackground)
                errorIcon = getDrawable(R.styleable.CardInput_cardInputErrorIcon)
                endIcon = getDrawable(R.styleable.CardInput_cardInputEndIcon)
                errorIconColor = getColorStateList(R.styleable.CardInput_cardInputErrorIconTint)
                cardInputEndIconAction = getInt(R.styleable.CardInput_cardInputEndIconAction, 0)
            } finally {
                recycle()
            }
        }
        setupUi()
        setupBackgroundsByFocus()
        copyPaste()
        setupHelpErrorText(true)
        onCardNumberTextChange()
        binding.clear.setOnClickListener { binding.cardNumber.setText("") }
    }

    override fun setEnabled(isEnable: Boolean) {
        binding.clear.isVisible = if (binding.cardNumber.text?.isNotEmpty() == true) isEnable else false
        binding.cardInputLayout.isEnabled = isEnable
        binding.endIcon.isEnabled = isEnable
        binding.cardNumber.isEnabled = isEnable
        binding.cardNumber.setTextColor(context.getColorFromAttr(if (isEnable) R.attr.contentPrimary else R.attr.contentPrimaryTonal1Disable))
        binding.cardNumber.setHintTextColor(context.getColorFromAttr(if (isEnable) R.attr.contentPrimaryTonal1 else R.attr.contentPrimaryTonal1Disable))
        binding.helpText.setTextColor(context.getColorFromAttr(if (isEnable) R.attr.contentPrimaryTonal1 else R.attr.contentPrimaryTonal1Disable))
    }

    fun setEndIconClickListener(onClickListener: OnClickListener) {
        binding.endIcon.setOnClickListener {
            if (binding.endIcon.drawable != null) onClickListener.onClick(it)
        }
    }

    private fun setupUi() {
        binding.systemLogoBackground.background = logoBackground
        binding.icError.setImageDrawable(errorIcon)
        binding.endIcon.setImageDrawable(endIcon)
        binding.icError.imageTintList = errorIconColor
    }

    @SuppressLint("SetTextI18n")
    private fun clearText(text: CharSequence) {
        if (text.isEmpty()) {
            setupBackgroundByFocusable()
            binding.clear.visibility = GONE
            isValidNumber = true
            setupHelpErrorText(true)
        } else {
            binding.clear.visibility = if (isEnabled) VISIBLE else GONE
        }
        binding.clear.setOnClickListener { binding.cardNumber.setText("") }
    }

    private fun setupBackgroundByFocusable() {
        binding.cardInputLayout.background = ContextCompat.getDrawable(
            context, if (isFocusable) R.drawable.background_primary_input_border else R.drawable.background_primary_input_corner
        )
    }

    private fun setErrorBackground() {
        binding.cardInputLayout.background = ContextCompat.getDrawable(context, R.drawable.background_primary_input_error_border)
    }

    private fun onCardNumberTextChange() {
        binding.cardNumber.doAfterTextChanged { text ->
            clearText(text ?: "")
            formattingCardNumber(text)
            detectLogoAndSet(text)
            if (!isValidNumber)
                isValidNumber(checkByMaxMinLength())
        }
    }

    private fun detectLogoAndSet(text: Editable?) {
        if ((text?.length ?: 0) >= logoDetectCount) {
            val cardType = detectCardSystem(text.toString().trim())
            if (cardType != CardSystemTypes.UNKNOWN)
                binding.icCardSystem.imageTintList = null
            binding.icCardSystem.setImageResource(cardType.getLogoResId(context))
        } else {
            binding.icCardSystem.setImageResource(CardSystemTypes.UNKNOWN.getLogoResId(context))
            binding.icCardSystem.imageTintList = context.getColorStateListFromAttr(R.attr.backgroundPendingTonal1)
        }
    }

    private fun setupBackgroundsByFocus() {
        binding.cardNumber.setOnFocusChangeListener { _, isFocusable ->
            this.isFocusable = isFocusable
            if (!isFocusable) {
                isValidNumber = if (binding.cardNumber.text?.isNotEmpty() == true) checkByMaxMinLength() else true
                isValidNumber(checkByMaxMinLength())
            }
            if (isValidNumber) {
                setupBackgroundByFocusable()
            } else {
                setErrorBackground()
            }
            setupHelpErrorText(isValidNumber)
        }
    }

    private fun checkByMaxMinLength(): Boolean {
        val cleanString = binding.cardNumber.text.toString().replace("\\D".toRegex(), "")
        return cleanString.length in minLength..maxLength
    }

    private fun isValidNumber(isValid: Boolean) {
        if (!isValid) {
            setErrorBackground()
        } else {
            setupBackgroundByFocusable()
        }
        setupHelpErrorText(isValid)
    }

    private fun setupHelpErrorText(isValid: Boolean) {
        if (isValid) {
            binding.helpText.setTextColor(context.getColorFromAttr(R.attr.contentPrimaryTonal1))
            binding.helpText.text = helpText
            binding.icError.visibility = GONE
            binding.helpText.isVisible = helpText.isNotEmpty() == true
        } else {
            binding.helpText.text = errorText
            binding.helpText.setTextColor(context.getColorFromAttr(R.attr.contentDangerTonal1))
            binding.icError.visibility = VISIBLE
        }
    }

    private fun copyPaste() {
        binding.cardNumber.setOnCutCopyPasteListener { action ->
            when (action) {
                android.R.id.paste -> pastTextWithClipBoard()
            }
        }
    }


    private fun findFragmentParent() {
        var parent = this.parent
        while (parent != null) {
            if (parent is FragmentContainerView) {
                fragment = findFragment(parent.id).childFragmentManager.fragments[0]
                break
            }
            parent = if (parent is View) parent.getParent() else null

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

    @SuppressLint("SetTextI18n")
    private fun pastTextWithClipBoard() {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboardManager.hasPrimaryClip()) {
            val clip = clipboardManager.primaryClip
            if (clip != null && clip.itemCount > 0) {
                val pastedText = clip.getItemAt(0).text.toString()
                pastedText.log("pastedText")
                binding.cardNumber.setText(pastedText)
                binding.clear.setOnClickListener { binding.cardNumber.setText("") }
            }
        }
    }

    enum class CardSystemTypes(private val logoResId: Int) {

        VISA(R.attr.visaLogo),
        MASTER(R.attr.masterLogo),
        AMEX(R.attr.amexLogo),
        UNION(R.attr.unionLogo),
        ARCA(R.attr.arcaLogo),
        UNKNOWN(R.attr.emptyLogo);


        fun getLogoResId(context: Context): Int {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(logoResId, typedValue, true)
            return typedValue.resourceId
        }
    }

    private fun detectCardSystem(cardNumber: String): CardSystemTypes {
        val digitsOnly = cardNumber.filter { it.isDigit() }
        return when {
            digitsOnly.startsWith("4") -> CardSystemTypes.VISA
            digitsOnly.take(2).toIntOrNull()?.let { it in 51..55 } == true -> CardSystemTypes.MASTER
            digitsOnly.take(4).toIntOrNull()?.let { it in 2221..2720 } == true -> CardSystemTypes.MASTER
            digitsOnly.startsWith("34") || digitsOnly.startsWith("37") -> CardSystemTypes.AMEX
            digitsOnly.startsWith("9") -> CardSystemTypes.ARCA
            digitsOnly.startsWith("62") -> CardSystemTypes.UNION
            else -> CardSystemTypes.UNKNOWN
        }
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

    private fun formattingCardNumber(text: Editable?) {
        if (isFormatting) return
        isFormatting = true

        val cleanString = text.toString().replace("\\D".toRegex(), "")
        val formatted = StringBuilder()

        for (i in cleanString.indices) {
            if (cleanString.startsWith("34") || cleanString.startsWith("37")) {
                // this for AMEX
                if ((i == 4 || i == 10) && i < maxLength) {
                    formatted.append(" ")
                }
            } else {
                if (i > 0 && i % 4 == 0 && i < maxLength) {
                    formatted.append(" ")
                }
            }
            formatted.append(cleanString[i])
        }
        current = formatted.toString()
        binding.cardNumber.setText(current)
        binding.cardNumber.setSelection(current.length)
        isFormatting = false
    }

    fun getCardScanData(cardData: ((CardDataModel) -> Unit)? = null) {
        onCardScanDataAction = cardData
    }


    fun initiateCardScan() {
        if (checkCameraPermission()) {
            startCardScan()
        } else {
            requestCameraPermission()
        }
    }

    private fun startCardScan() {
        val intent = ScanCardIntent.Builder(fragment.requireActivity()).build()
        fragment.activity?.startActivityFromFragment(fragment, intent, SCAN_REQUEST_CODE)
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        fragment.requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
    }

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCardScan()
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
            val card = data?.getParcelableExtra<Card>(ScanCardIntent.RESULT_PAYCARDS_CARD)

            if (card != null) {
                binding.cardNumber.setText(card.cardNumber)

                val expirationDate = card.expirationDate
                val cardHolderName = card.cardHolderName

                if (expirationDate != null && cardHolderName != null) {
                    val cardData = CardDataModel(
                        cardNumber = card.cardNumber,
                        cardCVV = "",
                        cardOwner = cardHolderName,
                        cardExDate = expirationDate
                    )
                    onCardScanDataAction?.invoke(cardData)
                }
            }
        }
    }


}