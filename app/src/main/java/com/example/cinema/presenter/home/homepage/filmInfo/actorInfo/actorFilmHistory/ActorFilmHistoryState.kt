package com.example.cinema.presenter.home.homepage.filmInfo.actorInfo.actorFilmHistory

import com.example.cinema.entity.fullInfoActor.Film

sealed class ActorFilmHistoryState{
    object Loading: ActorFilmHistoryState()
    data class Success(val filterFilms: List<Pair<String,Int>>): ActorFilmHistoryState()
    data class Error(val error:String): ActorFilmHistoryState()
}
