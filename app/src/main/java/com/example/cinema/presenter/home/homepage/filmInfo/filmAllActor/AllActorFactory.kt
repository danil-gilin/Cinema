package com.example.cinema.presenter.home.homepage.filmInfo.filmAllActor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class AllActorFactory @Inject constructor(private val allActorViewModel: AllActorViewModel) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return allActorViewModel as T
    }
}