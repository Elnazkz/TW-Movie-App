package com.example.tw_movie_app.ui.fragments

import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tw_movie_app.R
import com.example.tw_movie_app.adapters.MovieAdapter
import com.example.tw_movie_app.appbase.BaseFragment
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.data.responses.MoviesResponse
import com.example.tw_movie_app.databinding.FragmentMoviesListBinding
import com.example.tw_movie_app.services.network.Status
import com.example.tw_movie_app.view_models.PopularMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularMovieFragment : BaseFragment<FragmentMoviesListBinding>(R.layout.fragment_movies_list) {

    private val popularMovieViewModel: PopularMovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun subscribe() {
        popularMovieViewModel.getMovies()
    }
    override fun setupViews() {
        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            popularMovieViewModel.getMovies.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.loadingView.visibility = View.GONE

                        if (it.data?.results?.isEmpty() == true) {
                            binding.noDataFound.visibility = View.VISIBLE
                        } else {
                            binding.noDataFound.visibility = View.GONE
                            it.data?.results?.let { it1 -> setMovieAdapter(it1) }

                        }
                    }
                    Status.ERROR -> {
                        binding.loadingView.visibility = View.GONE
                    }
                    Status.LOADING -> binding.loadingView.visibility = View.VISIBLE
                    Status.NONE -> {}
                }

            }
        }
    }

    private fun setMovieAdapter(list: List<Movie>) {
        movieAdapter = MovieAdapter(list)
        binding.moviesRv.adapter = movieAdapter

    }

}