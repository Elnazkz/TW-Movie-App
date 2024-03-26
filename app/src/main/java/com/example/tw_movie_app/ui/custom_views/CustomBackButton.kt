package com.example.tw_movie_app.ui.custom_views

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.tw_movie_app.R
import com.example.tw_movie_app.databinding.CustomBackButtonBinding
import com.example.tw_movie_app.databinding.CustomDoubleButtonBinding

class CustomBackButton(
    context: Context,
    private val attrs: AttributeSet
) : ConstraintLayout(
    context,
    attrs
) {

    private lateinit var binding: CustomBackButtonBinding
    private lateinit var icon: AppCompatImageView
    private lateinit var title: AppCompatTextView

    init {
        initView()
    }

    private fun initView() {
        binding = CustomBackButtonBinding.inflate(LayoutInflater.from(context), this, true)
        val customAttributesStyle =
            context.obtainStyledAttributes(attrs, R.styleable.CustomBackButton, 0, 0)

        try {
            val buttonTitleText = customAttributesStyle.getString(R.styleable.CustomBackButton_buttonText)
            val iconTint = customAttributesStyle.getColor(R.styleable.CustomBackButton_buttonIconTint,
                ResourcesCompat.getColor(resources, R.color.white, null)

            )
            val textColor = customAttributesStyle.getColor(R.styleable.CustomBackButton_textColor,
                ResourcesCompat.getColor(resources, R.color.white, null)
            )

            setTitle(buttonTitleText)
            setIconColor(iconTint)
            setTitleColor(textColor)

        } finally {
            customAttributesStyle.recycle()
        }

    }

    private fun setTitle(name: String?) {
        if (name != null)
            binding.buttonTitle.text = name
    }

    private fun setIconColor(colorRes: Int?) {
        if (colorRes != null)
            binding.buttonIcon.setColorFilter(colorRes)
    }


    private fun setTitleColor(colorRes: Int?) {
        if (colorRes != null)
            binding.buttonTitle.setTextColor(colorRes)
    }

    fun setClickListener(action: () -> Unit) {
        binding.root.setOnClickListener {
            action()
        }
    }
}