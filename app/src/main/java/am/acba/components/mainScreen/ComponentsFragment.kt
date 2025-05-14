package am.acba.components.mainScreen

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentComponentsBinding
import androidx.core.os.bundleOf
import androidx.core.view.isVisible

class ComponentsFragment(private val isCompose: Boolean = false) : BaseViewBindingFragment<FragmentComponentsBinding>() {
    private val adapter = ComponentsAdapter(::onItemClick)
    override val inflate: Inflater<FragmentComponentsBinding>
        get() = FragmentComponentsBinding::inflate
    override val setInsets: Boolean
        get() = false

    override fun FragmentComponentsBinding.initView() {
        componentsRecycler.adapter = adapter
        val components = ComponentTypeEnum.getComponentsList(isCompose)
        adapter.submitList(components)
        componentsCount.isVisible = isCompose
        componentsCount.text = "Components (${components.size})\nReady (${components.count { it.isReady }})"
    }

    private fun onItemClick(component: Component) {
        getParent()?.navigateWithNavOptions(component.navigationId, bundleOf("Title" to component.title))
    }

    private fun getParent(): MainFragment? {
        if (parentFragment != null && parentFragment is MainFragment) {
            return parentFragment as MainFragment
        }
        return null
    }
}