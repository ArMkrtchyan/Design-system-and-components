package am.acba.component.phoneNumberInput

import am.acba.component.R
import am.acba.component.databinding.PhoneNumberInputBinding
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.inflater
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hbb20.CCPCountry

class PhoneNumberInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, 0) {
    private val binding by lazy { PhoneNumberInputBinding.inflate(context.inflater(), this, false) }
    private lateinit var countriesList: List<CountryModel>

    init {
        addView(binding.root)
        countriesMapping()
        setupBackgrounds()
        clearText()
        openCountryDialog()
    }

    private fun setupBackgrounds() {
        binding.countryCodeLayout.background = ContextCompat.getDrawable(context, R.drawable.background_primary_input_left_corner)
        binding.phoneNumberLayout.background = ContextCompat.getDrawable(context, R.drawable.background_primary_input_right_corner)
        binding.phoneNumber.setOnFocusChangeListener { _, isFocusable ->
            binding.phoneNumberLayout.background = ContextCompat.getDrawable(
                context, if (isFocusable) R.drawable.background_primary_input_right_border else R.drawable.background_primary_input_right_corner
            )
            binding.countryCodeLayout.background = ContextCompat.getDrawable(
                context, if (isFocusable) R.drawable.background_primary_input_left_border else R.drawable.background_primary_input_left_corner
            )
        }
        binding.phoneNumber.inputType = InputType.TYPE_CLASS_NUMBER
        binding.countryCodeLib.registerCarrierNumberEditText(binding.phoneNumber)
    }

    private fun countriesMapping() {
        countriesList = ArrayList<CountryModel>().let {
            CCPCountry.getLoadedLibraryMaterList().map { country ->
                CountryModel(
                    englishName = country.englishName,
                    flagResId = country.flagID,
                    name = country.name,
                    nameCode = country.nameCode,
                    phoneCode = country.phoneCode
                )
            }
        }.onEach {   //Default country
            if (it.nameCode == "AM") {
                selectCountry(it)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun clearText() {
        binding.phoneNumber.doOnTextChanged { text, _, _, _ ->
            binding.clear.visibility = if (text?.isEmpty() == true) GONE else VISIBLE
        }
        binding.clear.setOnClickListener { binding.phoneNumber.setText("") }
    }

    @SuppressLint("SetTextI18n")
    private fun selectCountry(countryModel: CountryModel) {
        binding.countryCode.text = "+${countryModel.phoneCode}"
        Glide.with(context).asBitmap().load(countryModel.flagResId).apply(RequestOptions.circleCropTransform().override(24.dpToPx(), 24.dpToPx()))
            .into(binding.countryFlag)
    }


    private fun openCountryDialog() {
        binding.countryCodeLayout.setOnClickListener {
            try {
                val fragmentActivity = context as? FragmentActivity
                fragmentActivity?.let {
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("CountriesList", countriesList as ArrayList)
                    val fragmentManager = it.supportFragmentManager
                    CountryBottomSheetDialog.show(fragmentManager, bundle,::selectCountry)
                }
            } catch (e: ClassCastException) {
                Log.d(TAG, "Can't get the fragment manager with this");
            }
        }
    }

}
