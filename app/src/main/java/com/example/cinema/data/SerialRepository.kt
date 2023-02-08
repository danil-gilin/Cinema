package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import javax.inject.Inject

class SerialRepository @Inject constructor(){
    val retrofitObject=CinemaRetrofitObject

    suspend fun getSerials(idSerial: Int)=retrofitObject.serialSeasonApi.getInfoForSerial(idSerial)
}