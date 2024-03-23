package com.example.tw_movie_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tw_movie_app.R
import com.example.tw_movie_app.baseconf.POSTER_IMAGE_BASE_URL
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.databinding.ItemMovieBinding

class MovieAdapter(
    private val movieList: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.FavArticleViewHolder>() {

    inner class FavArticleViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            setGenres(movie.genres.take(2))
            binding.movieTitle.text = movie.originalTitle
            binding.movieReleaseDate.text = movie.releaseDate
            binding.userScore.text = movie.votePercentage.toString() + "%"

            Glide
                .with(binding.root)
                .load(POSTER_IMAGE_BASE_URL + movie.posterPath)
                .error(R.drawable.placeholder)
                .into(binding.moviePic)
        }

        private fun setGenres(list: List<String>) {
            binding.genresChipView.removeAllViews()
            val inflater = LayoutInflater.from(binding.root.context)
            for (tag in list) {
                val view =
                    inflater.inflate(
                        R.layout.item_genre,
                        binding.genresChipView,
                        false
                    )

                val title = view.findViewById<AppCompatTextView>(R.id.genre_title)
                title.text = tag

                if (tag.isNotEmpty()) {
                    binding.genresChipView.addView(view)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return FavArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavArticleViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }


}