package am.acba.component.phoneNumberInput

import am.acba.component.R
import am.acba.component.databinding.CountryBottomSheetBinding
import am.acba.component.extensions.parcelableArrayList
import am.acba.component.phoneNumberInput.adapter.CountriesChipsAdapter
import am.acba.component.phoneNumberInput.adapter.CountriesListAdapter
import am.acba.component.phoneNumberInput.adapter.TitleAdapter
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CountryBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var _binding: CountryBottomSheetBinding
    private val binding get() = _binding
    private var dBActionsList: MutableList<CountryModel> = mutableListOf()
    private lateinit var countriesAdapter: CountriesListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = CountryBottomSheetBinding.inflate(inflater, container, false)
        binding.btnClose.setOnClickListener { dismiss() }
        val countriesList = arguments?.parcelableArrayList<CountryModel>("CountriesList")
        getLastActions()
        setupAdapter(countriesList as ArrayList)
        searchCountry(countriesList)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog = BottomSheetDialog(requireContext(), theme).apply {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = true
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.rvCountries.addOnScrollListener(object : OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (dy in 0..350) {
//                    Log.i("scrollY", "${dy}")
//                    val alpha: Double = (dy / 350.0)
//                    Log.i("scrollY", "${dy / 350.0}")
//                    binding.shadow.alpha = alpha.toFloat()
//                } else if (dy > 350) binding.shadow.alpha = 1f
//            }
//        })
        view.post {
            val parent = view.parent as View
            val params = parent.layoutParams
            val behavior = (params as ViewGroup.LayoutParams).apply {
                height = (resources.displayMetrics.heightPixels)
            }
            parent.layoutParams = behavior
        }
    }

    private fun setupAdapter(countriesList: List<CountryModel>) {
        if (binding.rvCountries.adapter == null) {
            countriesAdapter = CountriesListAdapter(::selectCountry, countriesList)
            val titleAdapter = TitleAdapter(getString(R.string.frequendly_search))
            val countriesTitleAdapter = TitleAdapter(getString(R.string.history_filter_button_all))
            val chipList = dBActionsList
            val chipsAdapter = CountriesChipsAdapter(chipList, ::selectCountry)
            val concatAdapter = ConcatAdapter(titleAdapter, chipsAdapter, countriesTitleAdapter, countriesAdapter)
            binding.rvCountries.apply {
                adapter = concatAdapter
            }
        } else {
            countriesAdapter.replaceList(countriesList)
        }
    }

    private fun searchCountry(countriesList: List<CountryModel>) {
        binding.search.editText?.doAfterTextChanged {
            if (it?.length!! > 2) {
                filterAction(countriesList, it)
            } else {
                setupAdapter(countriesList)
            }
        }

    }

    private fun filterAction(countriesList: List<CountryModel>, char: CharSequence) {
        val filterList = ArrayList<CountryModel>()
        countriesList.forEach {
            if (it.name?.lowercase()?.startsWith(char)!!) {
                filterList.add(it)
            }
        }
        setupAdapter(filterList)
    }

    override fun onDismiss(dialog: DialogInterface) {
        instance = null
        super.onDismiss(dialog)
    }

    private fun selectCountry(country: CountryModel) {
        mAction?.invoke(country)
        saveLastActions(country)
        dismiss()
    }

    private fun getLastActions() {
        val sharedPreferences = context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences?.getString("LastActionCountryList", "")
        val type = object : TypeToken<ArrayList<CountryModel>>() {}.type
        dBActionsList = gson.fromJson(json, type) ?: ArrayList()
    }

    private fun saveLastActions(country: CountryModel) {
        dBActionsList.remove(dBActionsList.find { it.name == country.name })
        if (dBActionsList.size > 4) {
            dBActionsList.removeAt(dBActionsList.size - 1)
        }

        dBActionsList.add(0, country)

        val sharedPreferences = context?.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val gson = Gson()
        val json = gson.toJson(dBActionsList)
        editor?.putString("LastActionCountryList", json)
        editor?.apply()
    }

    private var mAction: ((CountryModel) -> Unit?)? = null
}
