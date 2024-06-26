package am.acba.components.base

import androidx.recyclerview.widget.DiffUtil

fun <T : Any> getDiffCallback(): DiffUtil.ItemCallback<T> {
    return object : DiffUtil.ItemCallback<T>() {

        inline fun <reified T> T.isTheSame(second: Any): Boolean {
            return second is T
        }

        inline fun <reified T> T.isContentsTheSame(second: Any): Boolean {
            return try {
                this == second as T
            } catch (e: java.lang.Exception) {
                false
            }
        }

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return if (oldItem is DifItem<*> && newItem is DifItem<*>) {
                try {
                    oldItem as DifItem<T>
                    newItem as DifItem<T>
                    oldItem.areItemsTheSame(newItem)
                } catch (e: Exception) {
                    false
                }
            } else {
                try {
                    oldItem as DifItem<T>
                    oldItem.isTheSame(newItem)
                } catch (e: Exception) {
                    false
                }
            }
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return if (oldItem is DifItem<*> && newItem is DifItem<*>) {
                try {
                    oldItem as DifItem<T>
                    newItem as DifItem<T>
                    oldItem.areContentsTheSame(newItem)
                } catch (e: Exception) {
                    false
                }
            } else {
                try {
                    oldItem as DifItem<T>
                    oldItem.isContentsTheSame(newItem)
                } catch (e: Exception) {
                    false
                }
            }
        }

    }
}
