package am.acba.components

import am.acba.component.extensions.getColorStateListFromAttr
import am.acba.component.table.TableItem
import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentTablesBinding
import androidx.appcompat.content.res.AppCompatResources.getDrawable

class TablesFragment : BaseViewBindingFragment<FragmentTablesBinding>() {
    override val inflate: Inflater<FragmentTablesBinding>
        get() = FragmentTablesBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentTablesBinding.initView() {
        table.apply {
            setTitle("Acba")
            setIconTint(requireContext().getColorStateListFromAttr(am.acba.component.R.attr.contentPrimaryTonal1))
            setIcon(getDrawable(requireContext(), am.acba.component.R.drawable.ic_close_round))
            submitList(
                listOf(
                    TableItem("Անուն Ազգանուն1", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն2", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն3", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն4", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն5", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն6", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն7", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն8", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն9", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն0", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն10", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն11", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն11", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն11", "Աննա Հովսեփյան"),
                    TableItem("Անուն Ազգանուն-", "Աննա Հովսեփյան")
                )
            )
        }
    }
}