package com.example.cinema.presenter.home.homepage

import com.example.cinema.entity.cinema.Cinema

sealed class HomePageState{
    object Loading : HomePageState()
    data class Success(val cinemaList: List<Cinema>,val genreName:String):HomePageState()
    data class Error(val error: String):HomePageState()
}
