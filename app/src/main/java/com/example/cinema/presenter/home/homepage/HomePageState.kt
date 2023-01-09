package com.example.cinema.presenter.home.homepage

import com.example.cinema.entity.cinema.Cinema

sealed class HomePageState{
    object Loading : HomePageState()
    object Success :HomePageState()
    data class Error(val error: String):HomePageState()
}
