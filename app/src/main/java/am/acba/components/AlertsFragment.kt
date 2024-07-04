package am.acba.components

import am.acba.component.alert.Alert
import am.acba.component.alert.PrimaryAlert
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentAlertsBinding
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class AlertsFragment : BaseViewBindingFragment<FragmentAlertsBinding>() {
    override val inflate: Inflater<FragmentAlertsBinding>
        get() = FragmentAlertsBinding::inflate
    override val toolbar: Toolbar
        get() = mBinding.toolbar

    override fun FragmentAlertsBinding.initView() {
        showAlert()
        showAlertPagination()
    }

    private fun showAlert() {
        mBinding.alert.apply {
            setType(PrimaryAlert.AlertTypes.NEUTRAL)
            setNeutralIcon(am.acba.component.R.drawable.ic_info)
            setTitle("Title")
            setBody("Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam nunc adipiscing. Quis nec diam fames feugiat ac non.")
            setLink("Link")
            setOnCloseClickListener {
                Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT)
                    .show()
            }
            setOnLinkClickListener {
                Toast.makeText(context, "Link clicked", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showAlertPagination() {
        val alerts = listOf(
            Alert(
                PrimaryAlert.AlertTypes.DANGER,
                title = "Title",
                body = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam nunc adipiscing. Quis nec diam fames feugiat ac non.",
                link = "aaa",
                onLinkClick = {
                    Toast.makeText(context, "Link clicked", Toast.LENGTH_SHORT)
                        .show()
                }),
            Alert(
                PrimaryAlert.AlertTypes.INFO,
                title = "Title",
                body = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam nunc adipiscing. Quis nec diam fames feugiat ac non."
            ),
            Alert(
                PrimaryAlert.AlertTypes.NEUTRAL,
                neutralIcon = am.acba.component.R.drawable.ic_info,
                title = "Title",
                body = "Lorem ipsum dolor sit amet consectetur. Integer odio consectetur interdum at nullam nunc adipiscing. Quis nec diam fames feugiat ac non."
            ),
        )
        mBinding.alertPagination.submitList(alerts)
    }
}