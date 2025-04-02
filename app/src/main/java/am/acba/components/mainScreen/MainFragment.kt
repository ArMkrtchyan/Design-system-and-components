package am.acba.components.mainScreen

import am.acba.components.ComponentTypeEnum
import am.acba.components.R
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentMainBinding
import androidx.navigation.fragment.findNavController

class MainFragment : BaseViewBindingFragment<FragmentMainBinding>() {
    private val adapter = ComponentsAdapter(::onItemClick)
    override val inflate: Inflater<FragmentMainBinding>
        get() = FragmentMainBinding::inflate

    override fun FragmentMainBinding.initView() {
        switcher.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                MainActivity.darkTheme = isChecked
                mActivity.recreate()
            }
        }
        componentsRecycler.adapter = adapter
        adapter.submitList(getComponents())
    }

    private fun onItemClick(component: Component) {
        when (component.type) {
            ComponentTypeEnum.BUTTONS -> findNavController().navigate(R.id.action_mainFragment_to_buttonsFragment)
            ComponentTypeEnum.BUTTONS_COMPOSE -> findNavController().navigate(R.id.action_mainFragment_to_buttonsComposeFragment)
            ComponentTypeEnum.INPUTS -> findNavController().navigate(R.id.action_mainFragment_to_inputsFragment)
            ComponentTypeEnum.ALERTS -> findNavController().navigate(R.id.action_mainFragment_to_alertsFragment)
            ComponentTypeEnum.BADGES -> findNavController().navigate(R.id.action_mainFragment_to_badgesFragment)
            ComponentTypeEnum.BANNERS -> findNavController().navigate(R.id.action_mainFragment_to_bannersFragment)
            ComponentTypeEnum.CHIPS -> findNavController().navigate(R.id.action_mainFragment_to_chipsFragment)
            ComponentTypeEnum.CONTROLS -> findNavController().navigate(R.id.action_mainFragment_to_controlsFragment)
            ComponentTypeEnum.DATE_PICKER -> findNavController().navigate(R.id.action_mainFragment_to_datePickerFragment)
            ComponentTypeEnum.DIALOGS -> findNavController().navigate(R.id.action_mainFragment_to_dialogsFragment)
            ComponentTypeEnum.DIVIDER -> findNavController().navigate(R.id.action_mainFragment_to_dividerFragment)
            ComponentTypeEnum.DROP_DOWN -> findNavController().navigate(R.id.action_mainFragment_to_dropDownFragment)
            ComponentTypeEnum.EXCHANGE -> findNavController().navigate(R.id.action_mainFragment_to_exchangeFragment)
            ComponentTypeEnum.LIST_ITEMS -> findNavController().navigate(R.id.action_mainFragment_to_listItemFragment)
            ComponentTypeEnum.LIST_ITEMS_2 -> findNavController().navigate(R.id.action_mainFragment_to_listItem2Fragment)
            ComponentTypeEnum.PHONE_NUMBER_INPUT -> findNavController().navigate(R.id.action_mainFragment_to_phoneNumberInputFragment)
            ComponentTypeEnum.CARD_INPUT -> findNavController().navigate(R.id.action_mainFragment_to_cardInputFragment)
            ComponentTypeEnum.CURRENCY_INPUT -> findNavController().navigate(R.id.action_mainFragment_to_currencyInputFragment)
            ComponentTypeEnum.PROGRESS_CARD_WIEW -> findNavController().navigate(R.id.action_mainFragment_to_progressViewFragment)
            ComponentTypeEnum.EXPANDABLE_VIEW -> findNavController().navigate(R.id.action_mainFragment_to_expandableViewFragment)
            ComponentTypeEnum.PRIMARY_TAB_LAYOUT -> findNavController().navigate(R.id.action_mainFragment_to_tabLayoutFragment)
            ComponentTypeEnum.PIN_INPUT -> findNavController().navigate(R.id.action_mainFragment_to_pinInputFragment)
            ComponentTypeEnum.QUICK_ACTION_AVATAR -> findNavController().navigate(R.id.action_mainFragment_to_quickActionAndAvatarFragment)
            ComponentTypeEnum.SEARCH_INPUT -> findNavController().navigate(R.id.action_mainFragment_to_searchInputFragment)
            ComponentTypeEnum.TOOLBAR -> findNavController().navigate(R.id.action_mainFragment_to_toolbarFragment)
            ComponentTypeEnum.ONBOARDING_TOOTLTIPS -> findNavController().navigate(R.id.action_mainFragment_to_tooltipFragment)
            ComponentTypeEnum.CARD_LISTING -> findNavController().navigate(R.id.action_mainFragment_to_cardListingFragment)
            ComponentTypeEnum.STATUS_SCREEN -> findNavController().navigate(R.id.action_mainFragment_to_statusScreenFragment)
            ComponentTypeEnum.BOTTOM_SHEET -> findNavController().navigate(R.id.action_mainFragment_to_bottomSheetFragment)
            ComponentTypeEnum.EMPTY_SCREEN -> findNavController().navigate(R.id.action_mainFragment_to_emptyStateFragment)
            ComponentTypeEnum.PRODUCT_DESCRIPTION_CARD -> findNavController().navigate(R.id.action_mainFragment_to_productDescriptionCardFragment)
            ComponentTypeEnum.PROGRESS_INDICATOR -> findNavController().navigate(R.id.action_mainFragment_to_progressIndicatorFragment)
            ComponentTypeEnum.OFFERS_COMPONENTS -> findNavController().navigate(R.id.action_mainFragment_to_offersFragment)
            ComponentTypeEnum.PRODUCT_CARD_VIEW -> findNavController().navigate(R.id.action_mainFragment_to_cardViewFragment)
            ComponentTypeEnum.TABLES -> findNavController().navigate(R.id.action_mainFragment_to_tablesFragment)
            ComponentTypeEnum.SNACK_BAR -> findNavController().navigate(R.id.action_mainFragment_to_snackBarFragment)
        }
    }

    private fun getComponents() = arrayListOf(
        Component(ComponentTypeEnum.BUTTONS, ComponentTypeEnum.BUTTONS.componentName),
        Component(ComponentTypeEnum.BUTTONS_COMPOSE, ComponentTypeEnum.BUTTONS_COMPOSE.componentName),
        Component(ComponentTypeEnum.INPUTS, ComponentTypeEnum.INPUTS.componentName),
        Component(ComponentTypeEnum.SNACK_BAR, ComponentTypeEnum.SNACK_BAR.componentName),
        Component(ComponentTypeEnum.PHONE_NUMBER_INPUT, ComponentTypeEnum.PHONE_NUMBER_INPUT.componentName),
        Component(ComponentTypeEnum.CURRENCY_INPUT, ComponentTypeEnum.CURRENCY_INPUT.componentName),
        Component(ComponentTypeEnum.PROGRESS_CARD_WIEW, ComponentTypeEnum.PROGRESS_CARD_WIEW.componentName),
        Component(ComponentTypeEnum.CARD_INPUT, ComponentTypeEnum.CARD_INPUT.componentName),
        Component(ComponentTypeEnum.EXPANDABLE_VIEW, ComponentTypeEnum.EXPANDABLE_VIEW.componentName),
        Component(ComponentTypeEnum.PRIMARY_TAB_LAYOUT, ComponentTypeEnum.PRIMARY_TAB_LAYOUT.componentName),
        Component(ComponentTypeEnum.SEARCH_INPUT, ComponentTypeEnum.SEARCH_INPUT.componentName),
        Component(ComponentTypeEnum.PIN_INPUT, ComponentTypeEnum.PIN_INPUT.componentName),
        Component(ComponentTypeEnum.CONTROLS, ComponentTypeEnum.CONTROLS.componentName),
        Component(ComponentTypeEnum.DATE_PICKER, ComponentTypeEnum.DATE_PICKER.componentName),
        Component(ComponentTypeEnum.EXCHANGE, ComponentTypeEnum.EXCHANGE.componentName),
        Component(ComponentTypeEnum.QUICK_ACTION_AVATAR, ComponentTypeEnum.QUICK_ACTION_AVATAR.componentName),
        Component(ComponentTypeEnum.BADGES, ComponentTypeEnum.BADGES.componentName),
        Component(ComponentTypeEnum.BANNERS, ComponentTypeEnum.BANNERS.componentName),
        Component(ComponentTypeEnum.ALERTS, ComponentTypeEnum.ALERTS.componentName),
        Component(ComponentTypeEnum.DIVIDER, ComponentTypeEnum.DIVIDER.componentName),
        Component(ComponentTypeEnum.CHIPS, ComponentTypeEnum.CHIPS.componentName),
        Component(ComponentTypeEnum.DIALOGS, ComponentTypeEnum.DIALOGS.componentName),
        Component(ComponentTypeEnum.DROP_DOWN, ComponentTypeEnum.DROP_DOWN.componentName),
        Component(ComponentTypeEnum.LIST_ITEMS, ComponentTypeEnum.LIST_ITEMS.componentName),
        Component(ComponentTypeEnum.LIST_ITEMS_2, ComponentTypeEnum.LIST_ITEMS_2.componentName),
        Component(ComponentTypeEnum.TOOLBAR, ComponentTypeEnum.TOOLBAR.componentName),
        Component(ComponentTypeEnum.ONBOARDING_TOOTLTIPS, ComponentTypeEnum.ONBOARDING_TOOTLTIPS.componentName),
        Component(ComponentTypeEnum.CARD_LISTING, ComponentTypeEnum.CARD_LISTING.componentName),
        Component(ComponentTypeEnum.STATUS_SCREEN, ComponentTypeEnum.STATUS_SCREEN.componentName),
        Component(ComponentTypeEnum.BOTTOM_SHEET, ComponentTypeEnum.BOTTOM_SHEET.componentName),
        Component(ComponentTypeEnum.EMPTY_SCREEN, ComponentTypeEnum.EMPTY_SCREEN.componentName),
        Component(ComponentTypeEnum.PRODUCT_DESCRIPTION_CARD, ComponentTypeEnum.PRODUCT_DESCRIPTION_CARD.componentName),
        Component(ComponentTypeEnum.PROGRESS_INDICATOR, ComponentTypeEnum.PROGRESS_INDICATOR.componentName),
        Component(ComponentTypeEnum.OFFERS_COMPONENTS, ComponentTypeEnum.OFFERS_COMPONENTS.componentName),
        Component(ComponentTypeEnum.PRODUCT_CARD_VIEW, ComponentTypeEnum.PRODUCT_CARD_VIEW.componentName),
        Component(ComponentTypeEnum.TABLES, ComponentTypeEnum.TABLES.componentName),
    )
}