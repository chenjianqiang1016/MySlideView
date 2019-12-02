package com.demo.myslideview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import com.demo.myslideview.MySlideRootView

class MyLinearLayout:LinearLayout {

    private var parentScrollview: MySlideRootView? = null

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setParentScrollview(parentScrollview: MySlideRootView) {
        this.parentScrollview = parentScrollview
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        parentScrollview?.requestDisallowInterceptTouchEvent(true)
        return super.onInterceptTouchEvent(ev)
    }


}