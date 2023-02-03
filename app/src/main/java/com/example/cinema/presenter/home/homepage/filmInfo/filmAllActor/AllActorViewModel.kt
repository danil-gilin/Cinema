package com.example.cinema.presenter.home.homepage.filmInfo.filmAllActor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetFilmFullInfo
import com.example.cinema.entity.actorAndWorker.ActorAndWorker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllActorViewModel @Inject constructor(private val getFilmFullInfo: GetFilmFullInfo) : ViewModel() {
    private val listActorChannel= Channel<List<ActorAndWorker>>{ }
    val listActor=listActorChannel.receiveAsFlow()


    fun getActors(idFilm: Int,type:String){
        viewModelScope.launch {
            var listActorAndWorker= getFilmFullInfo.getActorAndWorker(idFilm)
        if(type=="ACTOR"){
            val listActor= listActorAndWorker?.filter { it.professionKey == "ACTOR" && (it.nameRu != "" || it.nameEn != "") }?:emptyList()
            listActorChannel.send(  listActor)
        }else{
            val listWorker= listActorAndWorker?.filter { it.professionKey != "ACTOR" && (it.nameRu != "" || it.nameEn != "") }?:emptyList()
            Log.d("listWorker",listWorker.toString())
            listActorChannel.send(listWorker)
        }
        }
    }

}