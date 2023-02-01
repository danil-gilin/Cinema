package com.example.cinema.presenter.home.homepage.filmInfo.filmGallery

import com.example.cinema.entity.allGallery.GalleryAllFilm

sealed class StateFilmGallery {
    data class Success(val data: GalleryAllFilm) : StateFilmGallery()
    data class Error(val error: String) : StateFilmGallery()
    object Loading : StateFilmGallery()
}