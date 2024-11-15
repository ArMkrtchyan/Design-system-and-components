package am.acba.component.dialog

import am.acba.component.R
import am.acba.component.bottomsheet.PrimaryBottomSheetDialog
import am.acba.component.databinding.CountryBottomSheetBinding
import am.acba.component.extensions.Inflater
import am.acba.component.extensions.getCountryLastActions
import am.acba.component.extensions.log
import am.acba.component.extensions.parcelableArrayList
import am.acba.component.extensions.saveCountryLastAction
import am.acba.component.phoneNumberInput.CountryModel
import am.acba.component.phoneNumberInput.adapter.CountriesChipsAdapter
import am.acba.component.phoneNumberInput.adapter.CountriesListAdapter
import am.acba.component.phoneNumberInput.adapter.TitleAdapter
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior


class CountryBottomSheetDialog : PrimaryBottomSheetDialog<CountryBottomSheetBinding>() {
    override val inflate: Inflater<CountryBottomSheetBinding>
        get() = CountryBottomSheetBinding::inflate

    override val state: Int
        get() = BottomSheetBehavior.STATE_EXPANDED
    private var dBActionsList: MutableList<CountryModel> = mutableListOf()
    private var topChipsListByDigital: MutableList<CountryModel> = mutableListOf()
    private lateinit var countriesAdapter: CountriesListAdapter
    private var needToSaveActions: Boolean = true
    private var isSearchInputVisible: Boolean = true

    override val contentPaddingStart: Int
        get() = 0
    override val contentPaddingEnd: Int
        get() = 0

    override fun CountryBottomSheetBinding.initView() {
        getBundleVariablesAndSetupUi()
        setUpShadow()
    }


    companion object {
        private var instance: CountryBottomSheetDialog? = null
        fun show(
            fragmentManager: FragmentManager,
            bundle: Bundle? = null,
            action: ((CountryModel) -> Unit)? = null,
            topChipsList: MutableList<CountryModel>
        ): CountryBottomSheetDialog {
            if (instance == null) {
                instance = CountryBottomSheetDialog().apply { arguments = bundle;mAction = action;setTopChipsList(topChipsList) }
                instance?.show(fragmentManager, CountryBottomSheetDialog::class.java.simpleName)
            }
            return instance as CountryBottomSheetDialog
        }
    }

    private fun CountryBottomSheetBinding.getBundleVariablesAndSetupUi() {
        val countriesList = arguments?.parcelableArrayList<CountryModel>("CountriesList")
        needToSaveActions = arguments?.getBoolean("needToSavActionsOnDB") ?: false
        isSearchInputVisible = arguments?.getBoolean("isSearchInputVisible") ?: false
        if (needToSaveActions) getCountryChipListFromDb()
        search.isVisible = isSearchInputVisible
        setupAdapter(countriesList as ArrayList)
        searchCountry(countriesList)
    }

    private fun getCountryChipListFromDb() {
        dBActionsList = context?.getCountryLastActions() ?: mutableListOf()
        if (dBActionsList.isEmpty()) {
            dBActionsList.addAll(topChipsListByDigital)
            topChipsListByDigital.forEach {
                context?.saveCountryLastAction(it)
            }
        }
    }

    private fun CountryBottomSheetBinding.setUpShadow() {
        rvCountries.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val scrollY = recyclerView.computeVerticalScrollOffset()
                if (scrollY in 0..350) {
                    scrollY.log()
                    val alpha: Double = (scrollY / 350.0).log()
                    shadow.alpha = alpha.toFloat()
                } else if (scrollY > 350) shadow.alpha = 1f
            }
        })
    }

    private fun CountryBottomSheetBinding.setupAdapter(countriesList: List<CountryModel>) {
        if (rvCountries.adapter == null) {
            countriesAdapter = CountriesListAdapter(::selectCountry, countriesList)
            val titleAdapter = TitleAdapter(getString(R.string.frequendly_search))
            val countriesTitleAdapter = TitleAdapter(getString(R.string.history_filter_button_all))
            val chipList = dBActionsList
            val chipsAdapter = CountriesChipsAdapter(chipList, ::selectCountry)
            val concatAdapter = if (dBActionsList.isNotEmpty()) {
                ConcatAdapter(titleAdapter, chipsAdapter, countriesTitleAdapter, countriesAdapter)
            } else {
                ConcatAdapter(countriesAdapter)
            }
            rvCountries.adapter = concatAdapter
        } else {
            countriesAdapter.replaceList(countriesList)
        }
    }

    private fun CountryBottomSheetBinding.searchCountry(countriesList: List<CountryModel>) {
        search.editText?.doAfterTextChanged { text ->
            val searchText = text?.toString().orEmpty()
            if (searchText.isNotEmpty()) {
                filterAction(countriesList, searchText)
            } else {
                setupAdapter(countriesList)
            }
        }
    }

    private fun CountryBottomSheetBinding.filterAction(countriesList: List<CountryModel>, char: CharSequence) {
        val filterList = countriesList.filter { country ->
            country.name?.lowercase()?.startsWith(char) == true ||
                    country.englishName?.startsWith(char) == true ||
                    country.phoneCode?.startsWith(char) == true
        }.toMutableList()
        setupAdapter(filterList)
    }

    override fun onDismiss(dialog: DialogInterface) {
        instance = null
        super.onDismiss(dialog)
    }

    private fun selectCountry(country: CountryModel) {
        mAction?.invoke(country)
        if (needToSaveActions) context?.saveCountryLastAction(country)
        dismiss()
    }

    private fun setTopChipsList(topChipsList: MutableList<CountryModel>) {
        topChipsListByDigital.addAll(topChipsList)
    }

    private var mAction: ((CountryModel) -> Unit?)? = null
}
