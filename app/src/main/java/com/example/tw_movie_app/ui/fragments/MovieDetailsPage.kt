package com.example.tw_movie_app.ui.fragments

import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.tw_movie_app.R
import com.example.tw_movie_app.appbase.BaseFragment
import com.example.tw_movie_app.baseconf.BACK_DROP_BASE_URL
import com.example.tw_movie_app.baseconf.POSTER_IMAGE_BASE_URL
import com.example.tw_movie_app.data.models.Movie
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
                        movieTitle.text = it.originalTitle
                        movieReleaseDate.text = it.releaseDate
                        setPercentage(it.votePercentage)
                        progressHorizontal.setLineWidth(it.votePercentage)
                        toolbar.setBackgroundImageLink(BACK_DROP_BASE_URL + it.backdropPath)
                        setMoviePic(it.posterPath)
                        overview.text = it.overview
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
            setImageHeight(requireContext().dpToPx(310))
            setBackButtonClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setMoviePic(path: String) {
        Glide
            .with(requireContext())
            .load(POSTER_IMAGE_BASE_URL + path)
            .error(R.drawable.placeholder)
            .into(binding.moviePic)

    }

    private fun setPercentage(rating: Int) {
        val percentageText = SpannableString( getString(R.string.vote_percentage_placeholder, rating.toString()))
        percentageText.setSpan(RelativeSizeSpan(0.7f), percentageText.length - 1, percentageText.length, 0)
        percentageText.setSpan(SuperscriptSpan(), percentageText.length - 1, percentageText.length, 0)
        binding.userScore.text = percentageText
    }
}