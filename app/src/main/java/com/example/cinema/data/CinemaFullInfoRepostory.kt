package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.actorAndWorker.ActorAndWorker
import com.example.cinema.entity.allGallery.GalleryAllFilm
import com.example.cinema.entity.filmInfo.FilmInfo
import com.example.cinema.entity.galleryFilm.GalleryFilm
import com.example.cinema.entity.galleryFilm.GalleryItem
import com.example.cinema.entity.serialInfo.InfoSeasons
import com.example.cinema.entity.similarFilm.SimilarFilm
import retrofit2.HttpException
import javax.inject.Inject

class CinemaFullInfoRepostory @Inject constructor() {
   suspend fun getFilmFullInfo(id:Int):FilmInfo?{
       try {
           return CinemaRetrofitObject.cinemaFullInfoApi.getFullInfo(id)
       }catch (e:Exception){
           return null
       }
    }

    suspend fun getInfoForSerial(id:Int): InfoSeasons? {
        try {
            return CinemaRetrofitObject.cinemaFullInfoApi.getInfoForSerial(id)
        }catch (e:Exception){
            return null
        }
    }

    suspend fun getActorAndWroker(id: Int): List<ActorAndWorker>? {
        try {
            return  CinemaRetrofitObject.cinemaFullInfoApi.getListActor(id)
        }catch (e:Exception){
            return null
        }
    }
    suspend fun getGallery(id: Int): GalleryFilm? {
        try {
            return CinemaRetrofitObject.cinemaFullInfoApi.getListGallery(id)
        }catch (e:Exception){
            return null
        }
    }
    suspend fun getSimilarFilm(id: Int): SimilarFilm? {
        try {
            return  CinemaRetrofitObject.cinemaFullInfoApi.getListSimilarFilm(id)
        }catch (e:Exception){
            return null
        }
    }


    suspend  fun getFilmGallery(idFilm: Int): GalleryAllFilm {
        var listGallery=GalleryAllFilm(0,0,0,0,0,0,0,0,0)
        listGallery.still= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[0]).total
        listGallery.shooting= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[1]).total
        listGallery.poster= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[2]).total
        listGallery.fanArt= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[3]).total
        listGallery.promo= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[4]).total
        listGallery.concept= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[5]).total
        listGallery.wallpaper= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[6]).total
        listGallery.cover= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[7]).total
        listGallery.screenshot= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[8]).total
        return listGallery
    }

    suspend fun getFilmGalleryType(idFilm: Int,type:String,page:Int): List<GalleryItem>? {
        try {
            return CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,type,page).items
        }catch (e:Exception){
            return null
        }
    }


    companion object{
        val nameTypeGallery= listOf("STILL","SHOOTING","POSTER","FAN_ART","PROMO","CONCEPT","WALLPAPER","COVER","SCREENSHOT")
    }
}