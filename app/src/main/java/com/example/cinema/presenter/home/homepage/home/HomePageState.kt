package com.example.cinema.presenter.home.homepage.home

sealed class HomePageState{
    object Loading : HomePageState()
    object Success : HomePageState()
    data class Error(val error: String): HomePageState()
}
