package com.demo.myslideview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.demo.myslideview.R

/**
 * 标签文字View，用于展示文字标签，且标签只展示一个字。
 * 如：标签文字为：经济，则标签View中的文字为"经"
 *
 * 用法：
 * 布局中使用：
 * <com.demo.customtextdemo.LabelTextView
 *      android:id="@+id/myLabelView"
 *      android:layout_width="wrap_content"
 *      android:layout_height="wrap_content"
 *      app:labelBgColor="@color/labelBg"
 *      app:labelSize="30"
 *      android:layout_marginTop="30dp"
 * />
 * 界面中：myLabelView?.setTextAndColor("经济")
 *
 * 注：labelSize，是真正影响控件大小的值
 *
 */
class LabelTextView : View {

    private var mContext: Context? = null

    private var mPaint: Paint? = null
    private var mTextPaint: TextPaint? = null

    private var labelText: String = ""
    private var labelBgColor: Int = 0
    private var labelTextColor: Int = 0
    private var labelSize: Int = 0//单位：dp
    //控件的大小，宽=高
    private var viewSize: Float = 0f
    //要展示的文字
    private var showText: String = ""

    private var textOffset: Float = 0f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mContext = context

        val ta = context?.obtainStyledAttributes(attrs, R.styleable.LabelTextView)
        labelText = ta?.getString(R.styleable.LabelTextView_labelText) ?: ""
        labelBgColor = ta?.getColor(R.styleable.LabelTextView_labelBgColor, 0) ?: 0
        labelTextColor = ta?.getColor(R.styleable.LabelTextView_labelTextColor, 0) ?: 0
        labelSize = ta?.getInteger(R.styleable.LabelTextView_labelSize, 0) ?: 0

        ta?.recycle()

        init()
    }

    private fun init() {

        if (mContext == null || labelText.isEmpty() || labelBgColor == 0 || labelTextColor == 0 || labelSize == 0) {
            //关键数据不足，无法绘制
            viewSize = 0f
        } else {
            viewSize = UiUtils.dp2px(mContext!!, labelSize.toFloat()).toFloat()
        }

        if (viewSize != 0f) {

            //背景画笔
            if (mPaint == null) {
                mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            }
            mPaint?.style = Paint.Style.FILL
            mPaint?.color = labelBgColor
            mPaint?.strokeWidth = 20.toFloat()

            //字体画笔
            if (mTextPaint == null) {
                mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
            }

            mTextPaint?.textSize = viewSize / 2f
            mTextPaint?.color = labelTextColor
            mTextPaint?.textAlign = Paint.Align.CENTER

            //文字的上坡度和下坡度。用于计算偏移量
            var ascent = mTextPaint?.ascent() ?: 0f
            var descent = mTextPaint?.descent() ?: 0f

            //偏移量，用于辅助文字在竖直方向居中
            textOffset = (ascent + descent) / 2

            if (labelText.isEmpty().not()) {
                showText = labelText.substring(0, 1)
            }

        }

    }

    //设置文字和颜色
    fun setTextAndColor(text: String = "", size: Int = 0, bgColor: Int = 0, tvColor: Int = 0) {

        if (size != 0) {
            labelSize = size
        }

        if (bgColor != 0) {
            labelBgColor = bgColor
        }
        if (tvColor != 0) {
            labelTextColor = tvColor
        }

        if (text.isEmpty().not()) {
            labelText = text
        }

        //重新设置数据
        init()

        //重绘
        invalidate()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(viewSize.toInt(), viewSize.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (viewSize != 0f && showText.isEmpty().not() && mPaint != null && mTextPaint != null) {

            canvas?.drawCircle(viewSize / 2, viewSize / 2, viewSize / 2, mPaint!!)
            canvas?.drawText(showText, viewSize / 2, viewSize / 2 - textOffset, mTextPaint!!)

        }

    }


}