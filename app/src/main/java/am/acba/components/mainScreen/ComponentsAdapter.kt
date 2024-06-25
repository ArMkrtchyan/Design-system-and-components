package am.acba.components.mainScreen

import am.acba.components.base.BaseListAdapter
import am.acba.components.base.Inflater
import am.acba.components.databinding.ItemComponentsBinding

class ComponentsAdapter(
    private val onItemClick: (Component) -> Unit
) : BaseListAdapter<Component, ItemComponentsBinding>() {
    override val inflate: Inflater<ItemComponentsBinding>
        get() = ItemComponentsBinding::inflate

    override fun ItemComponentsBinding.onBind(position: Int, item: Component) {
        buttons.text = item.title
        root.setOnClickListener { onItemClick.invoke(item) }
    }
}