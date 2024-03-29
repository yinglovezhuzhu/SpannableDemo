package com.owen.spannabledemo

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.*
import android.text.style.*
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val nums = "0123456789";

    var textView: TextView? = null

    val relativeSizeColorSpan = RelativeSizeColorSpan(1.5f, Color.RED)


    val relativeSizeSpan = RelativeSizeSpan(1.2f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.textView = tvContent

        textView?.setText(SpannableStringBuilder(nums), TextView.BufferType.SPANNABLE)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnReset -> {
//                tvContent.setText(nums)
                relativeSizeColorSpan.color = Color.RED
                textView?.setText(SpannableStringBuilder(nums), TextView.BufferType.SPANNABLE)
            }
            R.id.btnSetSpan -> {
                SpannableStringBuilder(nums).also {
                    // 这里的end是4，但是Span效果是在0~3，所以Span的结尾是不包括end所在的字符
                    it.setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    textView?.setText(it)
                }
            }
            R.id.btnSetMultiSpan -> {
                SpannableStringBuilder(nums).also {
                    // 这里的end是4，但是Span效果是在0~3，所以Span的结尾是不包括end所在的字符
                    it.setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    it.setSpan(StyleSpan(Typeface.BOLD), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    textView?.setText(it)
                }

            }
            R.id.btnInsert0 -> {
                SpannableStringBuilder(nums).also {
                    // 这里的end是4，但是Span效果是在0~3，所以Span的结尾是不包括end所在的字符
                    it.setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    it.insert(0, "ABC")
                    textView?.setText(it)
                }

            }
            R.id.btnInsert4 -> {
                SpannableStringBuilder(nums).also {
                    // 这里的end是4，但是Span效果是在0~3，所以Span的结尾是不包括end所在的字符
                    it.setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    it.insert(4, "ABC")
                    textView?.setText(it)
                }
            }
            R.id.btnSetUnderLineSpan -> {
                SpannableStringBuilder(nums).also {
                    // 这里的end是7，但是Span效果是在3~6，所以Span的结尾是不包括end所在的字符
                    it.setSpan(UnderlineSpan(), 3, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    textView?.setText(it)
                }
            }
            R.id.btnSetRelativeSizeSpan -> {
                // 基于原来的字体大小扩大1.5倍
                SpannableStringBuilder(nums).also {
                    // 这里的end是7，但是Span效果是在3~6，所以Span的结尾是不包括end所在的字符
                    it.setSpan(RelativeSizeSpan(1.5f), 3, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    textView?.setText(it)
                }
            }
            R.id.btnSetBgColorSpan -> {
                // 设置文字的背景色
                SpannableStringBuilder(nums).also {
                    // 这里的end是7，但是Span效果是在3~6，所以Span的结尾是不包括end所在的字符
                    it.setSpan(BackgroundColorSpan(Color.GREEN), 3, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    textView?.setText(it)
                }
            }
            R.id.btnSetAlignSpan -> {
                // 设置段落文字对齐方式
                SpannableStringBuilder(nums + "\n" + "34567" + "\n" + "123").also {
                    // Span应用于整个段落
                    it.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, it.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    textView?.setText(it)
                }
            }
            R.id.btnSetCustomSpan -> {
                // 使用自定义 Span
                SpannableStringBuilder(nums).also {
                    // 这里的end是7，但是Span效果是在3~6，所以Span的结尾是不包括end所在的字符
                    it.setSpan(ColorAndRelativeSizeSpan(1.5f, Color.RED), 3, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//                    it.setSpan(RelativeSizeSpan(1.5f), 3, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
//                    it.setSpan(ForegroundColorSpan(Color.RED), 3, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    textView?.setText(it)
                }
            }
            R.id.btnChangeSpanWithoutText -> {
                // 定义Span
                SpannableStringBuilder(nums).also {
                    // TextView调用setText()方法是，使用带有TextView.BufferType参数的，并传入TextView.BufferType.SPANNABLE
                    textView?.setText(it, TextView.BufferType.SPANNABLE)
                }

                // 获取TextView的Spannable并修改
                val s = tvContent.text as Spannable
                s.setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                s.setSpan(StyleSpan(Typeface.BOLD), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                s.setSpan(RelativeSizeSpan(1.5f), 3, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }
            R.id.btnChangeSpanProperties1 -> {
                relativeSizeColorSpan.color = Color.RED
                relativeSizeColorSpan.relativeSize = 1.5f
                textView?.setText(SpannableStringBuilder(nums).also {
                    it.setSpan(relativeSizeColorSpan, 3, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                }, TextView.BufferType.SPANNABLE)
                textView?.requestLayout()
            }
            R.id.btnChangeSpanProperties2 -> {
                relativeSizeColorSpan.color = Color.GREEN
                relativeSizeColorSpan.relativeSize = 1.8f
                textView?.requestLayout()
            }
            R.id.btnChangeSpanProperties3 -> {
                relativeSizeColorSpan.color = Color.YELLOW
                relativeSizeColorSpan.relativeSize = 2.0f
                textView?.requestLayout()
            }
            R.id.btnClickableSpan -> {
                startActivity(Intent(this@MainActivity, ClickableSpanActivity::class.java))
            }
        }
    }


    /**
     * 自定义 Span：文本扩大并且设置前景色
     * 实现方案：Android已有文本扩大的 Span，所以只需要扩展文本扩大的 Span （RelativeSizeSpan）即可
     */
    class RelativeSizeColorSpan(var relativeSize: Float, @ColorInt var color: Int): RelativeSizeSpan(relativeSize) {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds?.color = color
            ds?.textSize = ds?.textSize * relativeSize
        }

        override fun updateMeasureState(ds: TextPaint) {
            super.updateMeasureState(ds)
            ds?.textSize = ds?.textSize * relativeSize
        }
    }

    class ColorAndRelativeSizeSpan(private val relativeSize: Float, @ColorInt val color: Int): ForegroundColorSpan(color) {
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)

            ds?.textSize = ds?.textSize * relativeSize
        }
    }
}
