package com.example.cinema.domain

import com.example.cinema.data.ActorWorkerInfoRepository
import javax.inject.Inject

class GetActorWorkerInfo @Inject constructor(private val actorWorkerInfoRepository: ActorWorkerInfoRepository){
    suspend fun getActorWorkerInfo(idActor:Int)=actorWorkerInfoRepository.getActorWorkerInfo(idActor)
}