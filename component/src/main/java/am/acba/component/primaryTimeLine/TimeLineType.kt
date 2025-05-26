package am.acba.component.primaryTimeLine

enum class TimeLineType(val type: Int) {
    TIMELINE(0),
    PROGRESS(1);

    companion object {
        fun findByType(type: Int): TimeLineType {
            return entries.find { it.type == type } ?: TIMELINE
        }
    }
}