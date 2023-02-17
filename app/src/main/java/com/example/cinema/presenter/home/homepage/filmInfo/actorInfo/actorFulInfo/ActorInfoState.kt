package com.example.cinema.presenter.home.homepage.filmInfo.actorInfo.actorFulInfo

import com.example.cinema.entity.fullInfoActor.FilmWithPosterAndActor
import com.example.cinema.entity.fullInfoActor.FullInfoActor

sealed class ActorInfoState{
    object Loading: ActorInfoState()
    data class Success(
        val actorInfo: FullInfoActor,
        val Films: ArrayList<FilmWithPosterAndActor>,
        val infoHistoryFilms: String,
        val watchesFilms: List<Int>
    ): ActorInfoState()
    data class Error(val error:String): ActorInfoState()
}