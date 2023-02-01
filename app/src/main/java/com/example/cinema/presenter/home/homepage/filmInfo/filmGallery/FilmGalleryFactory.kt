package com.example.cinema.presenter.home.homepage.filmInfo.filmGallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class FilmGalleryFactory @Inject constructor(private val filmGalleryViewModel: FilmGalleryViewModel):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return filmGalleryViewModel as T
    }
}