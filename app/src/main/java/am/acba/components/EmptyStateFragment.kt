package am.acba.components

import am.acba.component.toolbar.PrimaryToolbar
import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentEmptyStateBinding
import android.widget.Toast

class EmptyStateFragment : BaseViewBindingFragment<FragmentEmptyStateBinding>() {
    override val inflate: Inflater<FragmentEmptyStateBinding>
        get() = FragmentEmptyStateBinding::inflate
    override val toolbar: PrimaryToolbar
        get() = mBinding.toolbar

    override fun FragmentEmptyStateBinding.initView() {
        emptyView.setOnActionButtonClickListener {
            Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show()
        }
    }
}