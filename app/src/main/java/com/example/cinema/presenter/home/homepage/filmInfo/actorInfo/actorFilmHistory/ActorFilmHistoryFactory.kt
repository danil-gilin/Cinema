package com.example.cinema.presenter.home.homepage.filmInfo.actorInfo.actorFilmHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ActorFilmHistoryFactory @Inject constructor(private val actorFilmHistoryViewModel: ActorFilmHistoryViewModel) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return actorFilmHistoryViewModel as T
    }
}