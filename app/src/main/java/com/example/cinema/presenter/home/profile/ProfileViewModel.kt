package com.example.cinema.presenter.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.*
import com.example.cinema.entity.dbCinema.CollectionFilms
import com.example.cinema.entity.dbCinema.HistoryCollectionDB
import com.example.cinema.entity.dbCinema.WatchFilm
import com.example.cinema.presenter.home.profile.allFilmProfile.AllFilmProfileFragment
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
            val likeCollection=CollectionFilms(idNameList[0].first,
                idNameList[0].second,likeFilmUseCase.getLikeFilmSize())
            val wantToWatchCollection=CollectionFilms(idNameList[1].first,
                idNameList[1].second,wantToWatchFilmUseCase.getWantToWatchFilmSize())
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

    fun deleteCollection(id: Int) {
        viewModelScope.launch {
            collectionUseCase.deleteCollection(id)
        }
    }

    companion object {
        val idNameList = arrayListOf(-1 to "Любимые", -2 to "Хочу посмотреть", -3 to "Просмотрено", -4 to "История просмотра")
    }
}