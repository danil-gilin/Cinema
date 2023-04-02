package com.example.cinema.customView.newCollection

sealed class StateNewCollection{
    object Loading: StateNewCollection()
    data class SuccessSaveCollection(val id: Int) : StateNewCollection()
    data class Error(val error:String): StateNewCollection()
}
