package com.example.cinema.presenter.home.profile.allFilmProfile

import com.example.cinema.entity.dbCinema.FilmDB
import com.example.cinema.entity.dbCinema.HistoryCollectionDB
import com.example.cinema.entity.dbCinema.WatchFilm

sealed class AllFilmProfileState{
    data class Success(val list:List<FilmDB>,val name:String,val watchFilmId: List<Int>):AllFilmProfileState()
    data class SuccessHistory(val list:List<HistoryCollectionDB>,val name:String,val watchFilmId: List<Int>):AllFilmProfileState()
    data class Error(val error:String):AllFilmProfileState()
    object Loading:AllFilmProfileState()
    object Empty:AllFilmProfileState()
}
