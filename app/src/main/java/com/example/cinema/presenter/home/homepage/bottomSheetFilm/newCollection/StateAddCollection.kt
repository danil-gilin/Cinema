package com.example.cinema.presenter.home.homepage.bottomSheetFilm.newCollection

sealed class StateAddCollection{
    object Loading:StateAddCollection()
    object Success:StateAddCollection()
    data class Error(val error:Throwable):StateAddCollection()
}
