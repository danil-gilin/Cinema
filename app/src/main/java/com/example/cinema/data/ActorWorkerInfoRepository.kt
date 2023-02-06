package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.fullInfoActor.FullInfoActor
import javax.inject.Inject

class ActorWorkerInfoRepository @Inject constructor(){
   suspend fun getActorWorkerInfo(idActor: Int): FullInfoActor {
     return CinemaRetrofitObject.actorAndWorkerFullInfoApi.getActorWorkerInfo(idActor)
    }
}