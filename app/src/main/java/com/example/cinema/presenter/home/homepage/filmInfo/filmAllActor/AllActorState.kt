package com.example.cinema.presenter.home.homepage.filmInfo.filmAllActor

sealed class AllActorState{
    object Loading:AllActorState()
    data class Error(val error:String):AllActorState()
    object Success:AllActorState()
}
