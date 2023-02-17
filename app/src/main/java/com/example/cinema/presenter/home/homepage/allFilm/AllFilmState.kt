package com.example.cinema.presenter.home.homepage.allFilm

sealed class AllFilmState{
    object Loading:AllFilmState()
    data class Success(val watchFilms:List<Int>):AllFilmState()
    data class Error(val error:String):AllFilmState()
}
