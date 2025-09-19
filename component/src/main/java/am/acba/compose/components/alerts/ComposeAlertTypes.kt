package am.acba.compose.components.alerts

import kotlin.collections.get

enum class ComposeAlertTypes(val type: Int) {
    INFO(0),
    DANGER(1),
    WARNING(2),
    SUCCESS(3),
    NEUTRAL(4);

    companion object{
        private val map = ComposeAlertTypes.entries.associateBy(ComposeAlertTypes::type)
        fun from(type: Int?) = map[type]
    }
}