package com.example.tw_movie_app.ui.activities
import androidx.navigation.findNavController

import android.os.Bundle
import androidx.navigation.NavController
import com.example.tw_movie_app.R
import com.example.tw_movie_app.appbase.BaseActivity
import com.example.tw_movie_app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TWMovieApp)
    }

    override fun setupViews() {
        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun subscribe() {
    }
}