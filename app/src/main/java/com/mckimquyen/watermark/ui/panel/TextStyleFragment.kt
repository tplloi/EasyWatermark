package com.mckimquyen.watermark.ui.panel

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.mckimquyen.watermark.databinding.FTextStyleBinding
import com.mckimquyen.watermark.ui.adapter.DividerAdapter
import com.mckimquyen.watermark.ui.adapter.TextPaintStyleAdapter
import com.mckimquyen.watermark.ui.adapter.TextTypefaceAdapter
import com.mckimquyen.watermark.ui.base.BaseBindFragment
import com.mckimquyen.watermark.ui.widget.utils.BounceEdgeEffectFactory
import com.mckimquyen.watermark.utils.ktx.commitWithAnimation

class TextStyleFragment : BaseBindFragment<FTextStyleBinding>() {
    override fun bindView(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
    ): FTextStyleBinding {
        return FTextStyleBinding.inflate(layoutInflater)
    }

    private val paintStyleAdapter by lazy {
        TextPaintStyleAdapter(
            TextPaintStyleAdapter.obtainDefaultPaintStyleList(
                requireContext()
            ),
            shareViewModel.waterMark.value?.textStyle
        ) { _, paintStyle ->
            shareViewModel.updateTextStyle(paintStyle)
            typefaceAdapter.updateTextStyle(paintStyle)
        }
    }
    private val typefaceAdapter by lazy {
        TextTypefaceAdapter(
            TextTypefaceAdapter.obtainDefaultTypefaceList(
                requireContext()
            ),
            shareViewModel.waterMark.value?.textTypeface
        ) { _, typeface ->
            shareViewModel.updateTextTypeface(typeface)
        }
    }

    private val concatAdapter by lazy {
        ConcatAdapter(
            paintStyleAdapter,
            DividerAdapter(),
            typefaceAdapter,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rvColor?.apply {
            layoutManager = LinearLayoutManager(
                /* context = */ requireContext(),
                /* orientation = */LinearLayoutManager.HORIZONTAL,
                /* reverseLayout = */false
            )
            adapter = concatAdapter
            edgeEffectFactory = BounceEdgeEffectFactory(context, this)
        }
    }

    companion object {
        const val TAG = "TextStyleFragment"

        fun replaceShow(fa: FragmentActivity, containerId: Int) {
            val f = fa.supportFragmentManager.findFragmentByTag(TAG)
            if (f?.isVisible == true) {
                return
            }
            fa.commitWithAnimation {
                replace(
                    /* containerViewId = */ containerId,
                    /* fragment = */ TextStyleFragment(),
                    /* tag = */ TAG
                )
            }
        }
    }
}
