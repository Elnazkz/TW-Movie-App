package com.example.tw_movie_app.ui.custom_views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.tw_movie_app.R
import com.example.tw_movie_app.databinding.CustomAppbarImageBinding

class CustomAppbarWithImage @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout (
    context,
    attributeSet
) {

    private lateinit var binding: CustomAppbarImageBinding

    init {
        createAppBar()
    }

    private fun createAppBar() {
        binding =
            CustomAppbarImageBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setBackgroundImageLink(url: String) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .into(binding.toolbarImage)
    }

    fun setTitleText(text: String) {
        binding.toolbarTitle.text = text
    }

    fun setBackButtonClickListener(action: () -> Unit) {
        binding.backButton.setOnClickListener {
            action()
        }
    }

    fun setImageHeight(height: Int) {
        val layoutParams = ViewGroup.LayoutParams(
            LayoutParams.MATCH_PARENT,
            height
        )
        binding.toolbarImage.layoutParams = layoutParams
    }

}