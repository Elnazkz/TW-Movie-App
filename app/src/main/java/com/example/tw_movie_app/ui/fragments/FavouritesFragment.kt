package com.example.tw_movie_app.ui.fragments

import androidx.navigation.fragment.findNavController
import com.example.tw_movie_app.R
import com.example.tw_movie_app.appbase.BaseFragment
import com.example.tw_movie_app.databinding.FragmentFavouritesBinding
import com.example.tw_movie_app.ui.activities.MainActivity

class FavouritesFragment: BaseFragment<FragmentFavouritesBinding>(R.layout.fragment_favourites) {
    override fun subscribe() {

    }

    override fun setupViews() {
        binding.searchMore.setOnClickListener {
            (requireActivity() as MainActivity).navigate(
                R.id.action_favouritesFragment_to_popularMovieFragment,
                null
            )
        }

        binding.backButton.setClickListener {
            findNavController().popBackStack()
        }
    }
}