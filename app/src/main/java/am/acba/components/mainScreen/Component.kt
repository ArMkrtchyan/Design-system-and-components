package am.acba.components.mainScreen

import am.acba.components.ComponentTypeEnum
import am.acba.components.base.DifItem

data class Component(
    val type: ComponentTypeEnum,
    val title: String,
    val description: String = "",
) : DifItem<Component> {
    override fun areItemsTheSame(second: Component): Boolean {
        return type == second.type
    }

    override fun areContentsTheSame(second: Component): Boolean {
        return type == second.type
    }
}
