package com.example.tw_movie_app.services.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tw_movie_app.data.models.Movie
import com.example.tw_movie_app.utils.Converters

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract val movieDao: MovieDao
}