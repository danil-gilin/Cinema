package com.example.cinema.presenter.home.homepage.filmInfo.filmInfoAll

import com.example.cinema.entity.actorAndWorker.ActorAndWorker
import com.example.cinema.entity.galleryFilm.GalleryItem
import com.example.cinema.entity.similarFilm.SimilarItem

sealed class FilmInfoState {
    object Loading : FilmInfoState()
    data class Success(
        val shotInfo1: String,
        val shotInfo2: String,
        val shotInfo3: String,
        val name: String,
        val imgPreview: String,
        val imgLogo: String?,
        val actorList: Pair<String, List<ActorAndWorker>>,
        val workerList: Pair<String, List<ActorAndWorker>>,
        val galleryList: Pair<String, List<GalleryItem>>,
        val similarList: Pair<String, List<SimilarItem>>,
        val genre: String,
        val infoSerial: String?,
        val filmDescription: String,
        val filmShortDescription: String,
        val isWatch: Boolean,
        val filmsWatch: List<Int>,
    ) : FilmInfoState()
    data class Error(val error: String) : FilmInfoState()
}