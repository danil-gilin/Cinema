package com.example.cinema.presenter.home.homepage.filmInfo.actorInfo.actorFulInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ActorInfoFactory @Inject constructor(private val actorInfoViewModel: ActorInfoViewModel) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return actorInfoViewModel as T
    }
}