package com.mckimquyen.watermark.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView

@SuppressLint("ClickableViewAccessibility")
class ControllableScrollView : NestedScrollView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var canScroll = true

    override fun scrollTo(x: Int, y: Int) {
        if (canScroll) {
            super.scrollTo(x, y)
        }
    }

    override fun scrollBy(x: Int, y: Int) {
        if (canScroll) {
            super.scrollBy(x, y)
        }
    }
}
