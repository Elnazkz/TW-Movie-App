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

class CustomBackButton(
    context: Context,
    private val attrs: AttributeSet
) : ConstraintLayout(
    context,
    attrs
) {

    private lateinit var view: View
    private lateinit var icon: AppCompatImageView
    private lateinit var title: AppCompatTextView

    init {
        initView()
    }

    private fun initView() {
        view = View.inflate(context, R.layout.custom_back_button, this)
        val customAttributesStyle =
            context.obtainStyledAttributes(attrs, R.styleable.CustomBackButton, 0, 0)

        icon = view.findViewById(R.id.button_icon)
        title = view.findViewById(R.id.button_title)

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
            title.text = name
    }

    private fun setIconColor(colorRes: Int?) {
        if (colorRes != null)
            icon.setColorFilter(colorRes)
    }


    private fun setTitleColor(colorRes: Int?) {
        if (colorRes != null)
            title.setTextColor(colorRes)
    }

    fun setClickListener(action: () -> Unit) {
        view.rootView.setOnClickListener {
            action()
        }
    }
}