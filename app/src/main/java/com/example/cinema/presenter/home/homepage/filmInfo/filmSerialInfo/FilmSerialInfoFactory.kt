package com.example.cinema.presenter.home.homepage.filmInfo.filmSerialInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class FilmSerialInfoFactory @Inject constructor(private val filmSerialInfoViewModel: FilmSerialInfoViewModel):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return filmSerialInfoViewModel as T
    }
}