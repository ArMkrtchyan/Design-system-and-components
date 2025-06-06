package am.acba.component.extensions

import am.acba.component.phoneNumberInput.CountryModel
import android.content.Context
import android.content.ContextWrapper
import android.content.res.ColorStateList
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ColorInt
fun Context.getColorFromAttr(
    @AttrRes attrColor: Int, typedValue: TypedValue = TypedValue(), resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun Context.getColorStateListFromAttr(
    @AttrRes attrColor: Int, typedValue: TypedValue = TypedValue(), resolveRefs: Boolean = true
): ColorStateList {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return ColorStateList.valueOf(typedValue.data)
}

fun Context.inflater(): LayoutInflater = LayoutInflater.from(this)

fun Context.getActionBarHeight(): Int {
    var actionBarHeight = 0
    val tv = TypedValue()
    if (this.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    }
    return actionBarHeight
}

fun Context.saveCountryLastAction(country: CountryModel) {
    val dBActionsList: MutableList<CountryModel> = this.getCountryLastActions()
    dBActionsList.remove(dBActionsList.find { it.name == country.name })
    if (dBActionsList.size > 4) {
        dBActionsList.removeAt(dBActionsList.size - 1)
    }
    dBActionsList.add(0, country)
    val sharedPreferences = this.getSharedPreferences("phoneNumber_countries", Context.MODE_PRIVATE)
    val editor = sharedPreferences?.edit()
    val gson = Gson()
    val json = gson.toJson(dBActionsList)
    editor?.putString("LastActionCountryList", json)
    editor?.apply()
}

fun Context.getCountryLastActions(): MutableList<CountryModel> {
    val dBActionsList: MutableList<CountryModel>
    val sharedPreferences = this.getSharedPreferences("phoneNumber_countries", Context.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPreferences?.getString("LastActionCountryList", "")
    val type = object : TypeToken<ArrayList<CountryModel>>() {}.type
    dBActionsList = gson.fromJson(json, type) ?: ArrayList()
    return dBActionsList
}


fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Context.getDisplayWidth(): Int {
    val displayMetrics = DisplayMetrics()
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun Context.getDisplayHeight(): Int {
    val displayMetrics = DisplayMetrics()
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

private var lastVibrationTime: Long = 0
private const val vibrationInterval = 100
private var vibrator: Vibrator? = null

fun Context.vibrate(duration: Long) {
    if (vibrator == null) vibrator = getVibrator()

    val currentTime = System.currentTimeMillis()
    if (currentTime - lastVibrationTime >= vibrationInterval
        && vibrator?.hasVibrator() == true
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator?.vibrate(effect)
        } else {
            vibrator?.vibrate(duration)
        }

        lastVibrationTime = currentTime
    }
}

private fun Context.getVibrator(): Vibrator {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).getDefaultVibrator()
    } else {
        getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
}

fun Context.getActivity(): AppCompatActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is AppCompatActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}