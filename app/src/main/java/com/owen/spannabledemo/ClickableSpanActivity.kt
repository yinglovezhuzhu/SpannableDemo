package com.owen.spannabledemo

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt

/**
 *
 * <br/>Author：yunying.zhang
 * <br/>Email: yunyingzhang@rastar.com
 * <br/>Date: 2022/12/6
 */
class ClickableSpanActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_clickable_span)





        val tvNormal = findViewById<TextView>(R.id.tv_normal_clickable_span)
        tvNormal.movementMethod = LinkMovementMethod.getInstance()
        tvNormal.setText(SpannableString("我是普通的ClickableSpan").apply {
            setSpan(CSClickableSpan(Color.BLUE, View.OnClickListener {
                Toast.makeText(this@ClickableSpanActivity, tvNormal.text, Toast.LENGTH_SHORT).show()
            }), 5, 18, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        })

        val tvNormalNoSelection = findViewById<TextView>(R.id.tv_normal_clickable_span_no_selection)
        tvNormalNoSelection.highlightColor = Color.TRANSPARENT
        tvNormalNoSelection.movementMethod = LinkMovementMethod.getInstance()
        tvNormalNoSelection.setText(SpannableString("我是普通的ClickableSpan(无选中背景)").apply {
            setSpan(CSClickableSpan(Color.BLUE, View.OnClickListener {
                Toast.makeText(this@ClickableSpanActivity, tvNormalNoSelection.text, Toast.LENGTH_SHORT).show()
            }), 5, 18, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        })

        val tvStyle = findViewById<TextView>(R.id.tv_clickstyle_clickable_span)
        tvStyle.movementMethod = ClickableSpanMovementMethod(Color.argb(0x20, 0x33, 0x33, 0x33), Color.BLUE, Color.RED)
        tvStyle.setText(SpannableString("我是带点击效果的ClickableSpan").apply {
            setSpan(CSClickableSpan(Color.BLUE, View.OnClickListener {
                Toast.makeText(this@ClickableSpanActivity, tvStyle.text, Toast.LENGTH_SHORT).show()
            }), 8, 21, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        })
    }


    /**
     * 自定义 ClickableSpan
     * @param textColor 可点击标记文字颜色
     * @param clickListener 点击时间监听
     */
    class CSClickableSpan (@param:ColorInt private val textColor: Int,
                           private val clickListener: View.OnClickListener?) : ClickableSpan() {
        override fun onClick(widget: View) {
            clickListener?.onClick(widget)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)

            ds.color = textColor // 字体颜色（前景色）
            ds.bgColor = Color.TRANSPARENT  // 背景颜色
            ds.linkColor = textColor // 链接颜色
            ds.isUnderlineText = false // 是否显示下划线
            // 这里还可以配置其他绘制样式，比如下划线的粗细（如果启用下划线）、字体等等
        }
    }


    /**
     * 可点击标记 MovementMethod
     * @param clickedBgColor 按下背景颜色
     * @param normalTextColor 普通模式下文字颜色
     * @param clickedTextColor 按下文字颜色
     */
    class ClickableSpanMovementMethod(@ColorInt val clickedBgColor: Int, @ColorInt val normalTextColor : Int,
                                      @ColorInt val clickedTextColor: Int) : LinkMovementMethod() {
        override fun onTouchEvent(widget: TextView?, buffer: Spannable?, event: MotionEvent?): Boolean {
            if(null == event || null == widget || null == buffer) {
                return false
            }
            val action = event.action
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
                var x = event.x.toInt()
                var y = event.y.toInt()
                x -= widget.totalPaddingLeft
                y -= widget.totalPaddingTop
                x += widget.scrollX
                y += widget.scrollY
                val layout = widget.layout
                val line = layout.getLineForVertical(y)
                val off = layout.getOffsetForHorizontal(line, x.toFloat())
                val links = buffer!!.getSpans(off, off, ClickableSpan::class.java)
                if (links.isNotEmpty()) {
                    val link = links[0]
                    if (action == MotionEvent.ACTION_UP) {
                        // ACTION_UP 给当前标记添加一个透明色的背景Span
                        buffer.setSpan(
                            BackgroundColorSpan(Color.TRANSPARENT), buffer.getSpanStart(link),
                            buffer.getSpanEnd(link), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        // ACTION_UP 恢复普通字体颜色
                        buffer.setSpan(ForegroundColorSpan(normalTextColor), buffer.getSpanStart(link),
                            buffer.getSpanEnd(link), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                        // ACTION_UP 移除选中
                        // 移除选中（如果将TextView高亮色设置为透明，可忽略此行代码）
                        Selection.removeSelection(buffer)
                        link.onClick(widget)
                    } else if (action == MotionEvent.ACTION_DOWN) {
                        // ACTION_DOWN 给当前标记添加一个点击色的背景Span
                        buffer.setSpan(BackgroundColorSpan(clickedBgColor), buffer.getSpanStart(link),
                            buffer.getSpanEnd(link), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        // ACTION_DOWN 给当前标记添加一个点击色的前景Span
                        buffer.setSpan(ForegroundColorSpan(clickedTextColor), buffer.getSpanStart(link),
                            buffer.getSpanEnd(link), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                        // 移除选中（如果将TextView高亮色设置为透明，可忽略此行代码）
                        Selection.removeSelection(buffer)

                        // ACTION_DOWN 设置高亮色为点击色，并选中标记
//                        widget.highlightColor = clickedBgColor
//                        Selection.setSelection(
//                            buffer,
//                            buffer.getSpanStart(link),
//                            buffer.getSpanEnd(link)
//                        )
                    }
                    return true
                } else {
                    Selection.removeSelection(buffer)
                }
            }
//            return false
            return super.onTouchEvent(widget, buffer, event)
        }
    }

}