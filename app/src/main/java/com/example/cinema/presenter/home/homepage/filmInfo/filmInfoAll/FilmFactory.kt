package com.example.cinema.presenter.home.homepage.filmInfo.filmInfoAll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class FilmFactory @Inject constructor(private val filmInfoViewModel: FilmInfoViewModel):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return filmInfoViewModel as T
    }
}