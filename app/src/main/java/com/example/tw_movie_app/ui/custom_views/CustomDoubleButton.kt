package com.example.tw_movie_app.ui.custom_views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.example.tw_movie_app.R
import com.example.tw_movie_app.databinding.CustomDoubleButtonBinding

class CustomDoubleButton(
    context: Context,
    private val attrs: AttributeSet
) : ConstraintLayout(
    context,
    attrs
) {

    private lateinit var binding: CustomDoubleButtonBinding

    init {
        initView()
    }

    private fun initView() {
        binding = CustomDoubleButtonBinding.inflate(LayoutInflater.from(context), this, true)

        val customAttributesStyle =
            context.obtainStyledAttributes(attrs, R.styleable.CustomDoubleButton, 0, 0)

        try {
            val topButtonText =
                customAttributesStyle.getString(R.styleable.CustomDoubleButton_topButtonText)
            val bottomButtonText =
                customAttributesStyle.getString(R.styleable.CustomDoubleButton_bottomButtonText)

            val topButtonTextColor =
                customAttributesStyle.getColor(
                    R.styleable.CustomDoubleButton_topButtonTextColor,
                    ResourcesCompat.getColor(resources, R.color.white, null)
                )
            val bottomButtonTextColor =
                customAttributesStyle.getColor(
                    R.styleable.CustomDoubleButton_bottomButtonTextColor,
                    ResourcesCompat.getColor(resources, R.color.button_brown, null)
                )

            val topButtonBgRes =
                customAttributesStyle.getDrawable(
                    R.styleable.CustomDoubleButton_topButtonBackground
                )
            val bottomButtonBgRes =
                customAttributesStyle.getDrawable(
                    R.styleable.CustomDoubleButton_bottomButtonBackground
                )

            setTopButtonTitle(topButtonText)
            setTopButtonTitleColor(topButtonTextColor)
            setBottomButtonTitle(bottomButtonText)
            setBottomButtonTitleColor(bottomButtonTextColor)
            setTopButtonBackground(topButtonBgRes)
            setBottomButtonBackground(bottomButtonBgRes)

        } finally {
            customAttributesStyle.recycle()
        }
    }

    fun setTopButtonTitle(title: String?) {
        if (title != null)
            binding.topButton.text = title
    }

    fun setTopButtonTitleColor(colorRes: Int) {
        binding.topButton.setTextColor(colorRes)
    }


    fun setBottomButtonTitle(title: String?) {
        if (title != null)
            binding.bottomButton.text = title
    }

    fun setBottomButtonTitleColor(colorRes: Int) {
        binding.bottomButton.setTextColor(colorRes)
    }


    fun setTopButtonBackground(bgRes: Drawable?) {
        if (bgRes != null)
            binding.topButton.setBackgroundDrawable(bgRes)
    }

    fun setBottomButtonBackground(bgRes: Drawable?) {
        if (bgRes != null)
            binding.bottomButton.setBackgroundDrawable(bgRes)
    }

    fun setClickListener(action: () -> Unit) {

    }
}