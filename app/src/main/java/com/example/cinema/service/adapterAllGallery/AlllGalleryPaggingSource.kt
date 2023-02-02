package com.example.cinema.service.adapterAllGallery

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinema.data.CinemaFullInfoRepostory
import com.example.cinema.entity.cinemaTop.Film
import com.example.cinema.entity.galleryFilm.GalleryItem
import kotlinx.coroutines.flow.Flow

class AlllGalleryPaggingSource(val idFilm:Int,val type:String) : PagingSource<Int, GalleryItem>() {
    private val repository:CinemaFullInfoRepostory=CinemaFullInfoRepostory()

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? =1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        val page=params.key?:1
        var apiFunction= repository.getFilmGalleryType(idFilm, type, page)

        return kotlin.runCatching {
            apiFunction
        }.fold(
            onSuccess = {
                Log.d("PagginGallery", "load: ${it}")
                LoadResult.Page(
                    data = it!!,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }



    private companion object{
        private val FIRST_PAGE =1
    }
}