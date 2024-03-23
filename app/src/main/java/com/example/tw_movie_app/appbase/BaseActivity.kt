package com.example.tw_movie_app.appbase

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.tw_movie_app.appbase.interfaces.BaseViewInterface

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    AppCompatActivity(), BaseViewInterface {

    private lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!::binding.isInitialized)
            binding = DataBindingUtil.setContentView(this, layoutRes)
        setupViews()
    }

}