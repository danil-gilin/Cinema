package com.example.cinema.domain

import com.example.cinema.data.CinemaFullInfoRepostory
import javax.inject.Inject

class GetFilmGalleryUseCase @Inject constructor(private val repository: CinemaFullInfoRepostory){
   suspend fun getFilmGallery(idFilm:Int)=repository.getFilmGallery(idFilm)
}