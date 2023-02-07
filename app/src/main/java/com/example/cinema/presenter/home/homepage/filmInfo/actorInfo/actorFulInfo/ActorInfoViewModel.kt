package com.example.cinema.presenter.home.homepage.filmInfo.actorInfo.actorFulInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.GetActorWorkerInfo
import com.example.cinema.domain.GetFilmFullInfo
import com.example.cinema.entity.fullInfoActor.Film
import com.example.cinema.entity.fullInfoActor.FilmWithPosterAndActor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActorInfoViewModel @Inject constructor(private val getActorWorkerInfo: GetActorWorkerInfo,private val getFilmFullInfo: GetFilmFullInfo) : ViewModel() {
    private val _stateActroInfo= MutableStateFlow<ActorInfoState>(ActorInfoState.Loading)
    val stateActorInfo=_stateActroInfo.asStateFlow()


    fun getActorInfo(idActor: Int) {
        viewModelScope.launch {
            try {
            _stateActroInfo.value= ActorInfoState.Loading
          val info=  getActorWorkerInfo.getActorWorkerInfo(idActor)
            var listUrlFilmPreview= arrayListOf<FilmWithPosterAndActor>()
            var reatingFilm: List<Film>? = null
            if (info.films?.size!! <10){
               reatingFilm= info.films?.sortedByDescending { it.rating }?.distinctBy { it.filmId}
            }else{
                reatingFilm= info.films?.sortedByDescending { it.rating }?.distinctBy { it.filmId}?.subList(0,11)
            }
            reatingFilm?.forEach {
                val infoFilm=getFilmFullInfo.getFilmInfo(it.filmId!!)
                listUrlFilmPreview.add(FilmWithPosterAndActor(it.description,it.filmId,it.general,it.nameEn,it.nameRu,it.professionKey,it.rating,infoFilm?.posterUrlPreview))
            }

            var infoHistoryFilms=""
            info.films?.distinctBy { it.filmId}?.size.let {
                Log.d("HistoryFilms", "${it}")
                if (it != null && it.toString() in TenNumber || it.toString().last() == '0') {
                    infoHistoryFilms = "${it} фильмов"
                } else if (it.toString().last() == '1') {
                    infoHistoryFilms = "${it} фильм"
                } else if (it.toString().last() in '2'..'4') {
                    infoHistoryFilms = "${it} фильма"
                } else if (it.toString().last() in '5'..'9') {
                    infoHistoryFilms= "${it} фильмов"
                }
            }
            _stateActroInfo.value=
                ActorInfoState.Success(info, listUrlFilmPreview, infoHistoryFilms)
            }catch (e:Exception){
                _stateActroInfo.value= ActorInfoState.Error("Ошибка загрузки")
            }
        }
    }
    companion object {
        private val TenNumber = listOf<String>("10", "11", "12", "13", "14", "15", "16", "17", "18", "19")
    }
}