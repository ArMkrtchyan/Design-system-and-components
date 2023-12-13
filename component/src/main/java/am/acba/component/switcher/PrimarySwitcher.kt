package am.acba.component.switcher

import am.acba.component.R
import android.content.Context
import android.util.AttributeSet
import com.google.android.material.switchmaterial.SwitchMaterial

class PrimarySwitcher : SwitchMaterial {
    constructor(context: Context) : super(context, null, R.attr.primarySwitcherStyle)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs, R.attr.primarySwitcherStyle) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }


}