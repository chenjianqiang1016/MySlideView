package com.demo.myslideview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.Scroller

class MySlideRootView : HorizontalScrollView {

    private var mContext: Context? = null

    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    private var wrapper: LinearLayout? = null

    private var leftView: LinearLayout? = null
    private var mainView: LinearLayout? = null

    private var leftViewWidth: Int = 0

    private var scroller: Scroller? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mContext = context

        if (mContext != null) {

            screenWidth = UiUtils.getScreenWidth(mContext!!)
            screenHeight = UiUtils.getScreenHeight(mContext!!)

            scroller = Scroller(mContext)

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


        //放在这里获取、赋值。如果在构造方法中，会有空指针异常
        wrapper = this.getChildAt(0) as LinearLayout?
        leftView = wrapper?.getChildAt(0) as LinearLayout?
        mainView = wrapper?.getChildAt(1) as LinearLayout?

        leftViewWidth = UiUtils.dp2px(mContext!!, 80f)

        var mainLayoutParams: LinearLayout.LayoutParams =
            mainView!!.layoutParams as LinearLayout.LayoutParams

        mainLayoutParams.width = screenWidth

        mainView!!.layoutParams = mainLayoutParams

        mainView!!.layoutParams.width = screenWidth

//        setMeasuredDimension(leftViewWidth + screenWidth, screenHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        scrollTo(leftViewWidth, 0)

    }


    private var downX: Float = 0f
    private var downY: Float = 0f

    private var isOpen: Boolean = false

    override fun onTouchEvent(event: MotionEvent?): Boolean {

//        return super.onTouchEvent(event)

        event ?: return super.onTouchEvent(event)

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {

                downX = event.x
                downY = event.y

            }

//            MotionEvent.ACTION_MOVE -> {
//
//                var dx = downX - event.x
//                var dy = downY - event.y
//
//                //如果Y轴偏移量大于X轴偏移量 不再滑动
//                if (Math.abs(dy) > Math.abs(dx)) {
//                    return false
//                }
//
//            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

                Log.e("onTouchEvent ", "$scrollX")

                if (scrollX > leftViewWidth / 2) {
                    closeLeft()
                } else {
                    openLeft()
                }

            }
        }

        return super.onTouchEvent(event)
    }

    private fun closeLeft() {

        if (scroller == null) {
            scrollTo(leftViewWidth, 0)
        } else {
            scroller?.startScroll(scrollX, 0, Math.abs(leftViewWidth - scrollX), 0, 400);
            invalidate()
        }

        isOpen = false
    }

    private fun openLeft() {

        if (scroller == null) {
            scrollTo(0, 0)
        } else {
            scroller?.startScroll(scrollX, 0, -leftViewWidth - scrollX, 0, 400);
            invalidate()
        }

        isOpen = true
    }

    fun closeOrOpenLeft() {

        if (isOpen) {
            closeLeft()
        } else {
            openLeft()
        }

    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller != null && scroller!!.computeScrollOffset()) {
            scrollTo(scroller!!.currX, 0)
            invalidate()
        }
    }

}