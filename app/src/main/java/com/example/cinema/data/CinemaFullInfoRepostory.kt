package com.example.cinema.data

import com.example.cinema.data.retrofit.CinemaRetrofitObject
import com.example.cinema.entity.allGallery.GalleryAllFilm
import com.example.cinema.entity.filmInfo.FilmInfo
import com.example.cinema.entity.serialInfo.InfoSeasons
import javax.inject.Inject

class CinemaFullInfoRepostory @Inject constructor() {
   suspend fun getFilmFullInfo(id:Int):FilmInfo{
     return CinemaRetrofitObject.cinemaFullInfoApi.getFullInfo(id)
    }

    suspend fun getInfoForSerial(id:Int): InfoSeasons {
        return CinemaRetrofitObject.cinemaFullInfoApi.getInfoForSerial(id)
    }

    suspend fun getActorAndWroker(id: Int)=CinemaRetrofitObject.cinemaFullInfoApi.getListActor(id)
    suspend fun getGallery(id: Int)=CinemaRetrofitObject.cinemaFullInfoApi.getListGallery(id)
    suspend fun getSimilarFilm(id: Int)=CinemaRetrofitObject.cinemaFullInfoApi.getListSimilarFilm(id)


    suspend  fun getFilmGallery(idFilm: Int): GalleryAllFilm {
        var listGallery=GalleryAllFilm(null,null,null,null,null,null,null,null,null)
        listGallery.still= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[0]).items
        listGallery.shooting= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[1]).items
        listGallery.poster= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[2]).items
        listGallery.fanArt= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[3]).items
        listGallery.promo= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[4]).items
        listGallery.concept= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[5]).items
        listGallery.wallpaper= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[6]).items
        listGallery.cover= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[7]).items
        listGallery.screenshot= CinemaRetrofitObject.cinemaFullInfoApi.getListGalleryType(idFilm,nameTypeGallery[8]).items
        return listGallery
    }


    companion object{
        val nameTypeGallery= listOf("STILL","SHOOTING","POSTER","FAN_ART","PROMO","CONCEPT","WALLPAPER","COVER","SCREENSHOT")
    }
}