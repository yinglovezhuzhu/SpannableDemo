package com.owen.spannabledemo

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val nums = "0123456789";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnReset -> {
                tvContent.setText(nums)
            }
            R.id.btnSetSpan -> {
                SpannableStringBuilder(nums).also {
                    // 这里的end是4，但是Span效果是在0~3，所以Span的结尾是不包括end所在的字符
                    it.setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    tvContent.setText(it)
                }
            }
            R.id.btnSetMultiSpan -> {
                SpannableStringBuilder(nums).also {
                    // 这里的end是4，但是Span效果是在0~3，所以Span的结尾是不包括end所在的字符
                    it.setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    it.setSpan(StyleSpan(Typeface.BOLD), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    tvContent.setText(it)
                }
            }
            R.id.btnInsert0 -> {
                SpannableStringBuilder(nums).also {
                    // 这里的end是4，但是Span效果是在0~3，所以Span的结尾是不包括end所在的字符
                    it.setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    it.insert(0, "ABC")
                    tvContent.setText(it)
                }
            }
            R.id.btnInsert4 -> {
                SpannableStringBuilder(nums).also {
                    // 这里的end是4，但是Span效果是在0~3，所以Span的结尾是不包括end所在的字符
                    it.setSpan(ForegroundColorSpan(Color.RED), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    it.insert(4, "ABC")
                    tvContent.setText(it)
                }
            }
        }
    }
}
