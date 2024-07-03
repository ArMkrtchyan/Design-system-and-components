package am.acba.component.extensions

import android.util.Log

inline fun <reified T> T.log(tag: String = "Common", prefix: String = ""): T {
    Log.i(tag, prefix + this.toString())
    return this
}