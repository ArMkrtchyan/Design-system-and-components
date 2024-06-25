package am.acba.components.base

interface DifItem<T> {

    fun areItemsTheSame(second: T): Boolean
    fun areContentsTheSame(second: T): Boolean

}
