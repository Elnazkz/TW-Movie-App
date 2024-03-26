package com.example.tw_movie_app.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

const val TABLE_NAME = "favourites"
@Parcelize
@Entity(tableName = TABLE_NAME)
data class Movie(
    @PrimaryKey val id: Int,
    val genres: List<String>,
    val backdropPath: String,
    val posterPath: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val votePercentage: Int,
    var favourite: Boolean = false,
    var myRating: Int = 0
): Parcelable
