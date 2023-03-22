package com.example.cinema.presenter.home.homepage.bottomSheetFilm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.CollectionFilmUseCase
import com.example.cinema.domain.CollectionUseCase
import com.example.cinema.entity.dbCinema.CollectionFilms
import com.example.cinema.entity.dbCinema.FilmDBLocal
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddCollectionViewModel @Inject constructor(private val collectionUseCase: CollectionUseCase,private val collectionFilmUseCase: CollectionFilmUseCase) : ViewModel() {
  private  val _state = MutableStateFlow<StateAddCollection>(StateAddCollection.Loading)
    val state = _state.asStateFlow()

    private val _collectionSelect= Channel<List<Int>>{  }
    val collectionSelect=_collectionSelect.receiveAsFlow()



    fun getCollection() {
        try {
        viewModelScope.launch {
            _state.value = StateAddCollection.Loading
            _state.value = StateAddCollection.SuccessGetCollection(collectionUseCase.getAllCollection())
        }
        }catch (e:Exception){
            _state.value=StateAddCollection.Error(e.message.toString())
        }
    }

    fun getSelectCollection(id:Int){
        try {
        viewModelScope.launch {
            _collectionSelect.send(collectionFilmUseCase.getSelectCollection(id))
        }
        }catch (e:Exception){
            _state.value=StateAddCollection.Error(e.message.toString())
        }
    }

    fun addFilmToCollection(film: FilmDBLocal, idCollections: List<Int>) {
        try {


            viewModelScope.launch {
                Log.d("SuccessGetCollection", "addFilmToCollection dismiss 1")
                _state.value = StateAddCollection.Loading
                var idCollectionBoolean = arrayListOf<Pair<Int, Boolean>>()
                Log.d("SuccessGetCollection", "addFilmToCollection dismiss 2")
                var allCollection = collectionUseCase.getAllCollection()
                Log.d("SuccessGetCollection", "addFilmToCollection dismiss 3")
                allCollection.forEach {
                    if (it.id !in idCollections) {
                        idCollectionBoolean.add(Pair(it.id, false))
                    } else
                        idCollectionBoolean.add(Pair(it.id, true))
                }


                idCollectionBoolean.forEach {
                    collectionFilmUseCase.addFilmToCollection(film, it)
                }
                Log.d("SuccessGetCollection", "addFilmToCollection dismiss")
                _state.value = StateAddCollection.SuccessSaveCollection
            }
        } catch (e: Exception) {
          _state.value=StateAddCollection.Error(e.message.toString())
        }
    }
}