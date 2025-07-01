package am.acba.utils.extensions

fun String?.toIntOrZero() = this?.toIntOrNull() ?: 0

fun String?.toDoubleOrZero() = this?.toDoubleOrNull() ?: 0.0