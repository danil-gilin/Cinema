package com.example.cinema.presenter.home.homepage.bottomSheetFilm.newCollection

sealed class StateNewCollection{
    object Loading: StateNewCollection()
    object SuccessSaveCollection: StateNewCollection()
    data class Error(val error:String): StateNewCollection()
}
