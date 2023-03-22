package com.example.cinema.presenter.home.homepage.bottomSheetFilm

import com.example.cinema.entity.dbCinema.CollectionFilms

sealed class StateAddCollection{
    object Loading: StateAddCollection()
    object SuccessSaveCollection: StateAddCollection()
    data class SuccessGetCollection(val collections: List<CollectionFilms>): StateAddCollection()
    data class Error(val error:String): StateAddCollection()
}
