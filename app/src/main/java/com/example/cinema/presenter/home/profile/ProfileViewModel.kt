package com.example.cinema.presenter.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.*
import com.example.cinema.entity.dbCinema.CollectionFilms
import com.example.cinema.entity.dbCinema.HistoryCollectionDB
import com.example.cinema.entity.dbCinema.WatchFilm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val historyLocalFilmUseCase: HistoryLocalFilmUseCase,
    private val watchFilmUseCase: WatchFilmUseCase,
    private val likeFilmUseCase: LikeFilmUseCase,
    private val wantToWatchFilmUseCase: WantToWatchFilmUseCase,
    private val collectionUseCase: CollectionUseCase
) : ViewModel() {

    private val _state= MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state = _state.asStateFlow()

    fun getProfile(){
        viewModelScope.launch {
           val listHistory= historyLocalFilmUseCase.getHistoryLocal().plus(HistoryCollectionDB(-1,-1,"","","",1.0,false,-1))
            val listWatch=watchFilmUseCase.getAllWatchFilm().plus(WatchFilm(-1,"","","",1.0,false))

            val listCollection= arrayListOf<CollectionFilms>()
            val likeCollection=CollectionFilms(-1,"Любимые",likeFilmUseCase.getLikeFilmSize())
            val wantToWatchCollection=CollectionFilms(-2,"Хочу посмотреть",wantToWatchFilmUseCase.getWantToWatchFilmSize())
            listCollection.add(likeCollection)
            listCollection.add(wantToWatchCollection)
            listCollection.addAll(collectionUseCase.getAllCollection())



            _state.value=ProfileState.Success(listHistory,listWatch,listCollection)
        }
    }

    fun deleteWatch() {
        viewModelScope.launch {
            watchFilmUseCase.dellAllWatchFilm()
        }
    }

    fun deleteHistory() {
        viewModelScope.launch {
            historyLocalFilmUseCase.dellAllHistoryLocal()
        }
    }
}