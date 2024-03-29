package com.demo.myslideview

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.demo.myslideview.MySlideRootView

class MyTextView : TextView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setParentScrollview(parentScrollview: MySlideRootView) {
        parentScrollview.requestDisallowInterceptTouchEvent(true)
    }


}