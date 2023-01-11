package com.example.cinema.data.bd

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ProviderCinemaDao {
    @Provides
    fun providerCinemaDao(appDatabase: AppDataBase): CinemaDao {
        return appDatabase.cinemaDao()
    }
    @Provides
    @Singleton
    fun providerAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java ,
            "db"
        ).build()
    }
}