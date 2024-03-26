package com.example.tw_movie_app.ui.fragments

import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tw_movie_app.R
import com.example.tw_movie_app.adapters.FavAdapter
import com.example.tw_movie_app.appbase.BaseFragment
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.databinding.FragmentFavouritesBinding
import com.example.tw_movie_app.ui.activities.MainActivity
import com.example.tw_movie_app.view_models.FavouritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment: BaseFragment<FragmentFavouritesBinding>(R.layout.fragment_favourites) {

    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private lateinit var favAdapter: FavAdapter
    override fun subscribe() {
        favouritesViewModel.getAllFavsCall()
    }

    override fun setupViews() {
        binding.loadingView.visibility = View.VISIBLE
        setObservers()

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

    private fun setObservers() {
        lifecycleScope.launch {
            favouritesViewModel.getAllFavourites.collectLatest {
                if (it.isEmpty())
                    binding.noMovieFound.visibility = View.VISIBLE
                else {
                    binding.noMovieFound.visibility = View.GONE
                }
                setFavAdapter(it)
                binding.loadingView.visibility = View.GONE
            }
        }

        lifecycleScope.launch {
            favouritesViewModel.insertionResult.collectLatest {
                if (it > 0) {
                    binding.loadingView.visibility = View.VISIBLE
                    favouritesViewModel.getAllFavsCall()
                }
            }
        }

        lifecycleScope.launch {
            favouritesViewModel.deleteResult.collectLatest {
                if (it != 0) {
                    //if deleted
                    binding.loadingView.visibility = View.VISIBLE
                    favouritesViewModel.getAllFavsCall()
                }

            }
        }

        lifecycleScope.launch {
            favouritesViewModel.error.collectLatest {
                binding.loadingView.visibility = View.GONE
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun setFavAdapter(list: List<Movie>) {
        favAdapter = FavAdapter(list, {
            val bundle = bundleOf(MOVIE_ID_BUNDLE_KEY to it)
            (requireActivity() as MainActivity).navigate(
                R.id.action_favouritesFragment_to_movieDetailsPage,
                bundle
            )
        }, {
            favouritesViewModel.updateMovieFaveDB(it)
        })

        binding.favsRv.adapter = favAdapter
    }
}