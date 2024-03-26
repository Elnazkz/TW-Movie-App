package com.example.tw_movie_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tw_movie_app.R
import com.example.tw_movie_app.baseconf.POSTER_IMAGE_BASE_URL
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.databinding.ItemFavouriteBinding
import com.example.tw_movie_app.databinding.ItemMovieBinding

class FavAdapter(
    private val movieList: List<Movie>,
    private val onItemClickListener: (Int) -> Unit,
    private val onFavButtonClick: (Movie) -> Unit,
) : RecyclerView.Adapter<FavAdapter.FavArticleViewHolder>() {

    inner class FavArticleViewHolder(
        private val binding: ItemFavouriteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                if (movie.favourite)
                    faveButton.setImageResource(R.drawable.ic_favourite_filled)
                else
                    faveButton.setImageResource(R.drawable.ic_favourite_outline)

                faveButton.setOnClickListener {
                    onFavButtonClick(movie)
                }

                ratingValue.text = movie.myRating.toString()

                Glide
                    .with(root)
                    .load(POSTER_IMAGE_BASE_URL + movie.posterPath)
                    .error(R.drawable.placeholder)
                    .into(moviePic)

                mainItemLayout.setOnClickListener {
                    onItemClickListener(movie.id)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavouriteBinding.inflate(inflater, parent, false)
        return FavArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavArticleViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }


}