package com.example.cinema.presenter.home.homepage.bottomSheetFilm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.CollectionFilmUseCase
import com.example.cinema.domain.CollectionUseCase
import com.example.cinema.entity.dbCinema.CollectionFilms
import com.example.cinema.entity.dbCinema.FilmDBLocal
import com.example.cinema.presenter.home.homepage.bottomSheetFilm.newCollection.StateAddCollection
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddCollectionViewModel @Inject constructor(private val collectionUseCase: CollectionUseCase,private val collectionFilmUseCase: CollectionFilmUseCase) : ViewModel() {
  private  val _state = MutableStateFlow<StateAddCollection>(StateAddCollection.Loading)
    val state = _state.asStateFlow()




    private val _collection= Channel<List<CollectionFilms>>{  }
    val collection=_collection.receiveAsFlow()

    private val _collectionSelect= Channel<List<Int>>{  }
    val collectionSelect=_collectionSelect.receiveAsFlow()



    fun getCollection() {
        viewModelScope.launch {
            _collection.send(collectionUseCase.getAllCollection())
        }
    }

    fun getSelectCollection(id:Int){
        viewModelScope.launch {
            _collectionSelect.send(collectionFilmUseCase.getSelectCollection(id))
        }
    }

    fun addFilmToCollection(film: FilmDBLocal, idCollections: List<Int>) {
        viewModelScope.launch {
            var idCollectionBoolean= arrayListOf<Pair<Int,Boolean>>()
            var allCollection=collectionUseCase.getAllCollection()
            allCollection.forEach {
                if (it.id !in idCollections){
                    idCollectionBoolean.add(Pair(it.id,false))
                }else
                    idCollectionBoolean.add(Pair(it.id,true))
            }


            idCollectionBoolean.forEach {
               collectionFilmUseCase.addFilmToCollection(film, it)
           }
            _state.value = StateAddCollection.Success
        }
    }
}