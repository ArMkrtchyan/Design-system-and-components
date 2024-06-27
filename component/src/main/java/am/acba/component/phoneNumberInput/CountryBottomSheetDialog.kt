package am.acba.component.phoneNumberInput

import am.acba.component.databinding.CountryBottomSheetBinding
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CountryBottomSheetDialog() : BottomSheetDialogFragment() {
    private lateinit var _binding: CountryBottomSheetBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = CountryBottomSheetBinding.inflate(inflater, container, false)
        binding.btnClose.setOnClickListener { dismiss() }
        val countriesList = arguments?.getParcelableArrayList<CountryModel>("CountriesList")
        setupAdapter(countriesList as ArrayList)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog = BottomSheetDialog(requireContext(), theme).apply {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
    }


    companion object {
        private var instance: CountryBottomSheetDialog? = null
        fun show(
            fragmentManager: FragmentManager,
            bundle: Bundle? = null,
            action: ((CountryModel) -> Unit)? = null,
        ): CountryBottomSheetDialog {
            if (instance == null) {
                instance = CountryBottomSheetDialog().apply { arguments = bundle;mAction = action }
                instance?.show(fragmentManager, CountryBottomSheetDialog::class.java.simpleName)
            }
            return instance as CountryBottomSheetDialog
        }
    }

    private fun setupAdapter(countriesList: List<CountryModel>) {
        val countriesAdapter = CountriesAdapter(::selectCountry, countriesList)
        binding.rvCountries.apply {
            adapter = countriesAdapter
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        instance = null
        super.onDismiss(dialog)
    }

    private fun selectCountry(country: CountryModel) {
        mAction?.invoke(country)
        dismiss()
    }

    private var mAction: ((CountryModel) -> Unit?)? = null
}
