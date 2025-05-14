package am.acba.components.mainScreen

import am.acba.components.R
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

fun Fragment.navigateWithNavOptions(@IdRes resId: Int, bundle: Bundle?, popDestinationId: Int? = null, inclusive: Boolean = false) {
    val navOptions = NavOptions.Builder()
    navOptions.setEnterAnim(R.anim.enter_from_right)
    navOptions.setExitAnim(R.anim.exit_to_left)
    navOptions.setPopEnterAnim(R.anim.enter_from_left)
    navOptions.setPopExitAnim(R.anim.exit_to_right)
    if (popDestinationId != null && popDestinationId != 0) {
        navOptions.setPopUpTo(popDestinationId, inclusive)
    }
    if (isVisible) {
        findNavController().navigate(resId, bundle, navOptions.build())
    }
}