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
            ComponentTypeEnum.INPUTS -> findNavController().navigate(R.id.action_mainFragment_to_inputsFragment)
            ComponentTypeEnum.ALERTS -> findNavController().navigate(R.id.action_mainFragment_to_alertsFragment)
            ComponentTypeEnum.BADGES -> findNavController().navigate(R.id.action_mainFragment_to_badgesFragment)
            ComponentTypeEnum.BANNERS -> findNavController().navigate(R.id.action_mainFragment_to_bannersFragment)
            ComponentTypeEnum.CHIPS -> findNavController().navigate(R.id.action_mainFragment_to_chipsFragment)
            ComponentTypeEnum.CONTROLS -> findNavController().navigate(R.id.action_mainFragment_to_controlsFragment)
            ComponentTypeEnum.DIALOGS -> findNavController().navigate(R.id.action_mainFragment_to_dialogsFragment)
            ComponentTypeEnum.DIVIDER -> findNavController().navigate(R.id.action_mainFragment_to_dividerFragment)
            ComponentTypeEnum.DROP_DOWN -> findNavController().navigate(R.id.action_mainFragment_to_dropDownFragment)
            ComponentTypeEnum.EXCHANGE -> findNavController().navigate(R.id.action_mainFragment_to_exchangeFragment)
            ComponentTypeEnum.LIST_ITEMS -> findNavController().navigate(R.id.action_mainFragment_to_listItemFragment)
            ComponentTypeEnum.PHONE_NUMBER_INPUT -> findNavController().navigate(R.id.action_mainFragment_to_phoneNumberInputFragment)
            ComponentTypeEnum.PIN_INPUT -> findNavController().navigate(R.id.action_mainFragment_to_pinInputFragment)
            ComponentTypeEnum.QUICK_ACTION_AVATAR -> findNavController().navigate(R.id.action_mainFragment_to_quickActionAndAvatarFragment)
            ComponentTypeEnum.SEARCH_INPUT -> findNavController().navigate(R.id.action_mainFragment_to_searchInputFragment)
            ComponentTypeEnum.TOOLBAR -> findNavController().navigate(R.id.action_mainFragment_to_toolbarFragment)
        }
    }

    private fun getComponents() = arrayListOf(
        Component(ComponentTypeEnum.BUTTONS, ComponentTypeEnum.BUTTONS.componentName),
        Component(ComponentTypeEnum.INPUTS, ComponentTypeEnum.INPUTS.componentName),
        Component(ComponentTypeEnum.PHONE_NUMBER_INPUT, ComponentTypeEnum.PHONE_NUMBER_INPUT.componentName),
        Component(ComponentTypeEnum.SEARCH_INPUT, ComponentTypeEnum.SEARCH_INPUT.componentName),
        Component(ComponentTypeEnum.PIN_INPUT, ComponentTypeEnum.PIN_INPUT.componentName),
        Component(ComponentTypeEnum.CONTROLS, ComponentTypeEnum.CONTROLS.componentName),
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
        Component(ComponentTypeEnum.TOOLBAR, ComponentTypeEnum.TOOLBAR.componentName),
    )
}