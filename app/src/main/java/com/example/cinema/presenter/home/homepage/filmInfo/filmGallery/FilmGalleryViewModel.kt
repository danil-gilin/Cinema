package com.example.cinema.presenter.home.homepage.filmInfo.filmGallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinema.domain.GetFilmGalleryUseCase
import com.example.cinema.entity.galleryFilm.GalleryItem
import com.example.cinema.service.adapterAllGallery.AlllGalleryPaggingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmGalleryViewModel @Inject constructor(private val getfilmFallery: GetFilmGalleryUseCase) : ViewModel() {
   private val _state= MutableStateFlow<StateFilmGallery>(StateFilmGallery.Loading)
    var listGalleryType: Flow<PagingData<GalleryItem>>?=null
    val state=_state.asStateFlow()

    fun getFilterGalery(idFilm: Int) {
        try {
            viewModelScope.launch {
                _state.value = StateFilmGallery.Loading
                _state.value = StateFilmGallery.Success(getfilmFallery.getFilmGallery(idFilm))
            }
        }catch (e:Throwable){
            _state.value=StateFilmGallery.Error("Во время обработки запроса \nпроизошла ошибка")
        }
    }

    fun getGallery(idFilm: Int,type: String){
        try {
            listGalleryType = Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { AlllGalleryPaggingSource(idFilm, type) }
            ).flow.cachedIn(viewModelScope)
        Log.d("PaggeGallery","$listGalleryType")
        }catch (e:Throwable){
            _state.value=StateFilmGallery.Error("Во время обработки запроса \nпроизошла ошибка")
        }
    }
}