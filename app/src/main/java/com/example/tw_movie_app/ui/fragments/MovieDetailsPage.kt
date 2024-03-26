package com.example.tw_movie_app.ui.fragments

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.tw_movie_app.R
import com.example.tw_movie_app.appbase.BaseFragment
import com.example.tw_movie_app.baseconf.BACK_DROP_BASE_URL
import com.example.tw_movie_app.databinding.FragmentMovieDetailsBinding
import com.example.tw_movie_app.services.network.Status
import com.example.tw_movie_app.utils.dpToPx
import com.example.tw_movie_app.view_models.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsPage :
    BaseFragment<FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    override fun subscribe() {
        val movieId = arguments?.getInt(MOVIE_ID_BUNDLE_KEY)
        if (movieId != null)
            movieDetailsViewModel.getMovieDetail(movieId)
        else
            Toast.makeText(
                requireContext(),
                getString(R.string.oops_something_went_wrong), Toast.LENGTH_SHORT
            ).show()
    }

    override fun setupViews() {
        binding.loadingView.visibility = View.VISIBLE

        setToolbar()
        lifecycleScope.launch {
            movieDetailsViewModel.getMovieDetails.collectLatest {
                it?.let {
                    with(binding) {
                        loadingView.visibility = View.GONE
                        toolbar.setTitleText(it.originalTitle)
                        toolbar.setBackgroundImageLink(BACK_DROP_BASE_URL + it.backdropPath)
                    }
                }
            }
        }

        lifecycleScope.launch {
            movieDetailsViewModel.error.collectLatest {
                binding.loadingView.visibility = View.GONE
                Toast.makeText(requireContext(),it, Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun setToolbar() {
        with(binding.toolbar) {
            setImageHeight(requireContext().dpToPx(300))
            setBackButtonClickListener {
                findNavController().popBackStack()
            }
        }
    }
}