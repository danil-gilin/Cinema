package com.example.cinema.data.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinema.entity.dbCinema.LikeFilm
import com.example.cinema.entity.dbCinema.WantToWatchFilm
import com.example.cinema.entity.dbCinema.WatchFilm

@Database(entities = [WatchFilm::class,LikeFilm::class,WantToWatchFilm::class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun cinemaDao():CinemaDao
}