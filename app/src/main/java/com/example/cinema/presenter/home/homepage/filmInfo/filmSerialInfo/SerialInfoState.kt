package com.example.cinema.presenter.home.homepage.filmInfo.filmSerialInfo

import com.example.cinema.entity.serialInfo.InfoSeasons

sealed class SerialInfoState{
    object Loading:SerialInfoState()
    data class Error(val error:String):SerialInfoState()
    data class Success(val serialInfo:InfoSeasons):SerialInfoState()
}
