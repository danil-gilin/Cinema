package com.example.cinema.presenter.home.homepage.bottomSheetFilm.newCollection

sealed class StateNewCollection{
    object Loading: StateNewCollection()
    data class SuccessSaveCollection(val id: Int) : StateNewCollection()
    data class Error(val error:String): StateNewCollection()
}
