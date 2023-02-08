package com.example.cinema.presenter.home.homepage.filmInfo.filmSerialInfo

import com.example.cinema.entity.serialInfo.InfoSeasons

sealed class SerialInfoState{
    object Loading:SerialInfoState()
    object Error:SerialInfoState()
    data class Success(val serialInfo:InfoSeasons):SerialInfoState()
}
