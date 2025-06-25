package am.acba.component.tabLayout

import am.acba.component.R
import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.textView.PrimaryTextView
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.google.android.material.tabs.TabLayout

@SuppressLint("InflateParams")
class PrimaryTabLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : TabLayout(context, attrs) {

    private lateinit var tabNames: MutableList<String>

    init {
        this.tabRippleColor = null
        this.background = null
        onTabSelectListener()

    }

    private fun onTabSelectListener() {
        this.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                updateTabStyle(tab, true)
                mAction?.invoke(selectedTabPosition)
            }

            override fun onTabUnselected(tab: Tab?) {
                updateTabStyle(tab, false)
            }

            override fun onTabReselected(tab: Tab?) {

            }

        })
    }

    @SuppressLint("MissingInflatedId")
    private fun addTabsByList(tabList: MutableList<String>) {
        tabList.forEach {
            val tab: Tab = this.newTab()
            val customTabItem: View = LayoutInflater.from(context).inflate(R.layout.tab_item, null)
            val title = customTabItem.findViewById<PrimaryTextView>(R.id.item_title)
            title.text = it
            tab.customView = customTabItem
            this.addTab(tab)
            updateTabStyle(this.getTabAt(this.selectedTabPosition), true)
        }

    }

    private fun updateTabStyle(tab: Tab?, isSelected: Boolean) {
        tab?.customView?.let {
            val textView = it.findViewById<TextView>(R.id.item_title)
            if (isSelected) {
                textView.setTextAppearance(R.style.Body2_Bold)
                textView.setTextColor(context?.getColorStateListFromAttr(R.attr.contentBrandTonal1))
            } else {
                textView.setTextAppearance(R.style.Subtitle2_Regular)
                textView.setTextColor(context?.getColorStateListFromAttr(R.attr.contentPrimaryTonal1))
            }
        }
    }

    fun setTabItemsList(tabList: MutableList<String>) {
        tabNames = arrayListOf<String>().apply {
            addAll(tabList)
        }
        addTabsByList(tabNames)
    }

    fun setSelectTab(position: Int) {
        // Select the tab at the given position
        this.getTabAt(position)?.select()
    }


    fun setTabItemBadgeText(tabIndex: Int, badgeText: String) {
        val tab = this.getTabAt(tabIndex) ?: return
        val customView = tab.customView ?: return
        val badgeTextView = customView.findViewById<PrimaryTextView>(R.id.item_badge_text)
        badgeTextView.isVisible = badgeText.isNotEmpty()
        badgeTextView.text = badgeText
    }

    fun onTabSelectListener(action: ((Int) -> Unit)? = null) {
        mAction = action
    }

    private var mAction: ((Int) -> Unit?)? = null
}