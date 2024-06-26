package com.example.tw_movie_app.baseconf

import android.content.Context
import androidx.room.Room
import com.example.tw_movie_app.services.room.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DB_NAME = "fav_movies_db"
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDB(@ApplicationContext ctx: Context): AppDataBase {
        val builder = Room.databaseBuilder(ctx, AppDataBase::class.java, DB_NAME )
            .allowMainThreadQueries()
        return builder.build()
    }


}