package com.example.tw_movie_app.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.tw_movie_app.R
import com.example.tw_movie_app.databinding.ProgressLineBinding

class CustomProgressLine(
    context: Context,
    attributeSet: AttributeSet
): LinearLayout(
    context,
    attributeSet
) {
    private lateinit var binding: ProgressLineBinding

    init {
        initView()
    }

    private fun initView() {
        binding =
            ProgressLineBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setLineWidth(percentage: Int) {
        with(binding) {
            val layoutParams = horizontalLine.layoutParams
            layoutParams.width = (percentage / 100f * resources.getDimensionPixelSize(R.dimen.line_max_width)).toInt()
            horizontalLine.layoutParams = layoutParams
        }
    }
}