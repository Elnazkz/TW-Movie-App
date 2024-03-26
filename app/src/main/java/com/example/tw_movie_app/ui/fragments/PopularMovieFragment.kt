package com.example.tw_movie_app.ui.fragments

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tw_movie_app.R
import com.example.tw_movie_app.adapters.MovieAdapter
import com.example.tw_movie_app.appbase.BaseFragment
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.databinding.FragmentMoviesListBinding
import com.example.tw_movie_app.services.network.Status
import com.example.tw_movie_app.ui.activities.MainActivity
import com.example.tw_movie_app.view_models.PopularMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val MOVIE_ID_BUNDLE_KEY = "movie_id"
@AndroidEntryPoint
class PopularMovieFragment : BaseFragment<FragmentMoviesListBinding>(R.layout.fragment_movies_list) {

    private val popularMovieViewModel: PopularMovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun subscribe() {
        popularMovieViewModel.getMovies()
    }
    override fun setupViews() {
        binding.loadingView.visibility = View.VISIBLE

        observe()
        setSearch()
        setSearchClearButton()
    }

    private fun observe() {
        lifecycleScope.launch {
            popularMovieViewModel.getMovies.collectLatest {
                if (it.isEmpty()) {
                    binding.loadingView.visibility = View.GONE

                    binding.noDataFound.visibility = View.VISIBLE
                } else {
                    binding.loadingView.visibility = View.GONE

                    binding.noDataFound.visibility = View.GONE
                    setMovieAdapter(it)
                }

            }
        }

        lifecycleScope.launch {
            popularMovieViewModel.error.collectLatest {
                binding.loadingView.visibility = View.GONE
                Toast.makeText(requireContext(),it, Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun setMovieAdapter(list: List<Movie>) {
        movieAdapter = MovieAdapter(list) {
            val bundle = bundleOf(MOVIE_ID_BUNDLE_KEY to it)
            (requireActivity() as MainActivity).navigate(
                R.id.action_popularMovieFragment_to_movieDetailsPage,
                bundle
            )

        }
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