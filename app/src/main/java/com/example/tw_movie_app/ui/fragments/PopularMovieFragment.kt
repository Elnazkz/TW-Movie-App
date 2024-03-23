package com.example.tw_movie_app.ui.fragments

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tw_movie_app.R
import com.example.tw_movie_app.adapters.MovieAdapter
import com.example.tw_movie_app.appbase.BaseFragment
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.data.responses.MovieResponse
import com.example.tw_movie_app.databinding.FragmentMoviesListBinding
import com.example.tw_movie_app.services.network.Status
import com.example.tw_movie_app.view_models.PopularMovieViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
        setSearch()
        setSearchClearButton()
    }

    private fun observe() {
        lifecycleScope.launch {
            popularMovieViewModel.getMovies.collectLatest {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.loadingView.visibility = View.GONE

                        if (it.data?.isEmpty() == true) {
                            binding.noDataFound.visibility = View.VISIBLE
                            setMovieAdapter(listOf())
                        } else {
                            binding.noDataFound.visibility = View.GONE
                            it.data?.let { it1 -> setMovieAdapter(it1) }

                        }
                    }
                    Status.ERROR -> {
                        binding.loadingView.visibility = View.GONE
                        Toast.makeText(requireContext(),"Oops! Something went wrong!", Toast.LENGTH_SHORT).show()
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

    private fun setSearch() {
        binding.searchView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val input = p0.toString()
                if (input.isNotEmpty()) {
                    setViewToSearch()
                    popularMovieViewModel.doSearch(input)
                } else {
                    setViewToNormal()
                }
            }

        })
    }

    private fun setSearchClearButton() {
        binding.clearText.setOnClickListener {
            binding.searchView.setText("")
        }
    }

    private fun setViewToNormal() {
        popularMovieViewModel.resetToOriginalList()
        binding.pageTitle.setText(R.string.popular_right_now)
        binding.clearText.visibility = View.INVISIBLE

    }

    private fun setViewToSearch() {
        binding.pageTitle.setText(R.string.your_results)
        binding.clearText.visibility = View.VISIBLE
    }

}