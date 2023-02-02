package com.example.cinema.presenter.home.homepage.home

import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.cinemaTop.CinemaTop
import com.example.cinema.entity.typeListFilm.TypeListFilm

sealed class HomePageState {
    object Loading : HomePageState()

    data class Error(val error: String) : HomePageState()
    data class Success(
        val genreCinema1: Pair<TypeListFilm, AllCinema>?,
        val genreCinema2: Pair<TypeListFilm, AllCinema>?,
        val premiers: Pair<TypeListFilm, AllCinema>?,
        val serial: Pair<TypeListFilm, AllCinema>?,
        val popular: Pair<TypeListFilm, CinemaTop>?,
        val popular1: Pair<TypeListFilm, CinemaTop>?
    ) : HomePageState()

}
