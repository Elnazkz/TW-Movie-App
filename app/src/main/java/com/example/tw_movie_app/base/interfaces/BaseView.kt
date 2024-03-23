package com.example.tw_movie_app.base.interfaces

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tw_movie_app.R

interface BaseView {
    val rootView: ViewGroup

    fun showLoadingView(shouldShow: Boolean) {
        var loadingView: ConstraintLayout? = rootView.findViewById(R.id.loadingView)
        if (loadingView == null) {
            val inflater = LayoutInflater.from(rootView.context)
            loadingView = inflater.inflate(R.layout.layout_loading, rootView, false) as ConstraintLayout
            rootView.addView(loadingView)
        }

        loadingView.visibility = if (shouldShow) View.VISIBLE else View.GONE

    }

    fun setupViews()
    fun subscribe()
}