package com.example.cinema.data.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinema.entity.dbCinema.*

@Database(entities = [WatchFilm::class,LikeFilm::class,WantToWatchFilm::class,CollectionFilms::class,FilmDBLocal::class,MovieCollectionJoin::class,HistoryCollectionDB::class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun cinemaDao():CinemaDao
    abstract fun movieCollectionDao():MovieCollectionDao
}