package am.acba.components

import am.acba.components.base.BaseViewBindingFragment
import am.acba.components.base.Inflater
import am.acba.components.databinding.FragmentProgressIndicatorBinding

class ProgressIndicatorFragment : BaseViewBindingFragment<FragmentProgressIndicatorBinding>() {
    override val inflate: Inflater<FragmentProgressIndicatorBinding>
        get() = FragmentProgressIndicatorBinding::inflate


    override fun FragmentProgressIndicatorBinding.initView() {
        mBinding.shiping.setMediaAnimation("check_test.json")
        mBinding.segmentMediaView.setImageView(am.acba.component.R.drawable.ic_dot)
        mBinding.segmentMediaView.setMediaAnimation("check_test.json", am.acba.component.R.attr.contentBrand)
        mBinding.segment.setSegmentImage(am.acba.component.R.drawable.ic_dot)
        mBinding.segment.setMediaAnimation("check_test.json", am.acba.component.R.attr.contentBrand)

        mBinding.btn.setOnClickListener {
            mBinding.progressBar.setProgress(3)
            mBinding.segment.startProgress()
            mBinding.shiping.setProgress(4)
        }
        mBinding.resize1.setOnClickListener {
            mBinding.segmentMediaView.showLottieView()
        }
        mBinding.resize2.setOnClickListener {
            mBinding.segmentMediaView.resizeImageView()
        }
    }
}