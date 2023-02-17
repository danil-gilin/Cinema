package com.example.cinema.presenter.home.homepage.filmInfo.actorInfo.actorFilmHistory

sealed class ActorFilmHistoryState{
    object Loading: ActorFilmHistoryState()
    data class Success(
        val filterFilms: List<Pair<String, Int>>,
        val nameActorWorker: String,
        val famel: Boolean,
        val watchesFilms: List<Int>
    ): ActorFilmHistoryState()
    data class Error(val error:String): ActorFilmHistoryState()
}
