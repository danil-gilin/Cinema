package com.example.cinema.presenter.home.profilePage.allFilmProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.*
import com.example.cinema.entity.dbCinema.FilmDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllFilmProfileViewModel @Inject constructor(
    val wantToWatchFilmUseCase: WantToWatchFilmUseCase,
    val likeFilmUseCase: LikeFilmUseCase,
    val watchFilmUseCase: WatchFilmUseCase,
    val collectionFilmUseCase: CollectionFilmUseCase,
    val historyLocalFilmUseCase: HistoryLocalFilmUseCase,
    val collectionUseCase: CollectionUseCase
) : ViewModel() {
    val _state = MutableStateFlow<AllFilmProfileState>(AllFilmProfileState.Loading)
    val state = _state.asStateFlow()


    fun getCollection(collectionID: Int) {
        viewModelScope.launch {
            try {
                var name = ""
                val listFilm = when (collectionID) {
                    0 -> {
                        throw Exception("Error")
                    }
                    -1 -> {
                        name = idNameList[0].second
                        likeFilmUseCase.getAllLikeFilm()
                    }
                    -2 -> {
                        name = idNameList[1].second
                        wantToWatchFilmUseCase.getAllWantToWatchFilm()
                    }
                    -3 -> {
                        name = idNameList[2].second
                        watchFilmUseCase.getAllWatchFilm()
                    }
                    -4 -> {
                        emptyList()
                    }
                    else -> {
                        name = collectionUseCase.getCollectionNameById(collectionID)
                        collectionFilmUseCase.getCollectionAllFilm(collectionID)
                    }
                }

                if (listFilm.isEmpty()) {
                    name = idNameList[3].second
                    val watchsFilmId = watchFilmUseCase.getWatchFilmId()
                    _state.value = AllFilmProfileState.SuccessHistory(
                        historyLocalFilmUseCase.getHistoryLocal(),
                        name,
                        watchsFilmId
                    )
                } else {
                    val watchsFilmId = watchFilmUseCase.getWatchFilmId()
                    _state.value = AllFilmProfileState.Success(listFilm, name, watchsFilmId)
                }
            } catch (e: Throwable) {
                _state.value =
                    AllFilmProfileState.Error("Во время обработки запроса \nпроизошла ошибка")
            }
        }
    }

    companion object {
        val idNameList = arrayListOf<Pair<Int, String>>(
            -1 to "Любимые",
            -2 to "Хочу посмотреть",
            -3 to "Просмотрено",
            -4 to "История просмотра"
        )
    }
}