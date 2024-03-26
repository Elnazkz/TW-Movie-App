package com.example.tw_movie_app.ui.activities
import androidx.navigation.findNavController

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
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

    fun navigate(resId: Int, bundle: Bundle?) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter_from_right)
            .setExitAnim(R.anim.exit_to_left)
            .setPopEnterAnim(R.anim.enter_from_left)
            .setPopExitAnim(R.anim.exit_to_right)
            .build()
        navController.navigate(resId, bundle, navOptions)
    }

}