package com.example.cinema.presenter.home.homepage.filmInfo

import com.example.cinema.entity.filmInfo.FilmInfo

sealed class FilmInfoState {
    object Loading : FilmInfoState()
    data class Success(val film: FilmInfo) : FilmInfoState()
    data class Error(val error: String) : FilmInfoState()
}