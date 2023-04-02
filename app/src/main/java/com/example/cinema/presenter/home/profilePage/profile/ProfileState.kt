package com.example.cinema.presenter.home.profilePage.profile

import com.example.cinema.entity.dbCinema.CollectionFilms
import com.example.cinema.entity.dbCinema.HistoryCollectionDB
import com.example.cinema.entity.dbCinema.WatchFilm

sealed class ProfileState{
    object Loading: ProfileState()
    data class Success(
        val listHisotry: List<HistoryCollectionDB>,
        val listWatch: List<WatchFilm>,
        val listCollection: ArrayList<CollectionFilms>
    ): ProfileState()
    data class Error(val error:String): ProfileState()
}
