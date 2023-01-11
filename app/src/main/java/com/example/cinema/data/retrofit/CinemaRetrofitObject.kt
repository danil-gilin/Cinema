package com.example.cinema.data.retrofit

import com.example.cinema.entity.actorAndWorker.ActorAndWorker
import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.cinemaTop.CinemaTop
import com.example.cinema.entity.filmInfo.FilmInfo
import com.example.cinema.entity.galleryFilm.GalleryFilm
import com.example.cinema.entity.gener.GenreName
import com.example.cinema.entity.serialInfo.InfoSeasons
import com.example.cinema.entity.similarFilm.SimilarFilm
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val apiKey="518a1c18-031a-423c-92ea-dbdb897c776b"
const val baseUrl="https://kinopoiskapiunofficial.tech/api/"

object CinemaRetrofitObject {
   private val retrofit= Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val cinemaApi: CinemaApi = retrofit.create(CinemaApi::class.java)
    val cinemaFullInfoApi: CinemaFullInfoApi = retrofit.create(CinemaFullInfoApi::class.java)
}

interface CinemaApi {
@GET("v2.2/films?&order=RATING&type=ALL&ratingFrom=3&ratingTo=10&yearFrom=1000&yearTo=3000")
@Headers("X-API-KEY: $apiKey")
suspend fun getGenreWithCountryFilm(@Query("countries") countries:Int,@Query("genres") genres:Int,@Query("page") page:Int=1): AllCinema

@GET("v2.2/films?&order=RATING&type=ALL&ratingFrom=3&ratingTo=10&yearFrom=1000&yearTo=3000")
@Headers("X-API-KEY: $apiKey")
suspend fun getGenreFilm(@Query("genres") genres:Int,@Query("page") page:Int=1): AllCinema

@GET("v2.2/films?&order=RATING&type=ALL&ratingFrom=3&ratingTo=10&yearFrom=1000&yearTo=3000")
@Headers("X-API-KEY: $apiKey")
suspend fun getCountryFilm(@Query("countries") countries:Int,@Query("page") page:Int=1): AllCinema

@GET("v2.2/films/filters")
@Headers("X-API-KEY: $apiKey")
suspend fun getGenreAndCountry(): GenreName

@GET("v2.2/films/premieres")
@Headers("X-API-KEY: $apiKey")
suspend fun getPremieresFilm(@Query("year") year:Int,@Query("month") month:String): AllCinema

@GET("v2.2/films/top")
@Headers("X-API-KEY: $apiKey")
suspend fun getPopularFilm(@Query("type") year:String="TOP_AWAIT_FILMS",@Query("page") page:Int=1): CinemaTop

@GET("v2.2/films?order=RATING&type=TV_SERIES&ratingFrom=0&ratingTo=10&yearFrom=1000&yearTo=3000")
@Headers("X-API-KEY: $apiKey")
suspend fun getSerial(@Query("page") page:Int=1): AllCinema

}

interface CinemaFullInfoApi{
    @GET("v2.2/films/{filmId}")
    @Headers("X-API-KEY: $apiKey")
    suspend fun getFullInfo(@Path("filmId") filmId:Int): FilmInfo

    @GET("v2.2/films/{filmId}/seasons")
    @Headers("X-API-KEY: $apiKey")
    suspend fun getInfoForSerial(@Path("filmId") serialId:Int): InfoSeasons

    @GET("v1/staff")
    @Headers("X-API-KEY: $apiKey")
    suspend fun getListActor(@Query("filmId") filmId:Int):List<ActorAndWorker>

    @GET("v2.2/films/{filmId}/images")
    @Headers("X-API-KEY: $apiKey")
    suspend fun getListGallery(@Path("filmId") filmId:Int):GalleryFilm

    @GET("v2.2/films/{filmId}/similars")
    @Headers("X-API-KEY: $apiKey")
    suspend fun getListSimilarFilm(@Path("filmId") filmId:Int):SimilarFilm
}