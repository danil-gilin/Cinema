package com.example.cinema.presenter.home.searchPage.genre

sealed class GenreState {
    object Loading : GenreState()
    object Success : GenreState()
    data class Error(val error: String) : GenreState()
}