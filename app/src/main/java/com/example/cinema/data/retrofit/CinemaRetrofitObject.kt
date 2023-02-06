package com.example.cinema.data.retrofit

import com.example.cinema.entity.actorAndWorker.ActorAndWorker
import com.example.cinema.entity.cinema.AllCinema
import com.example.cinema.entity.cinemaTop.CinemaTop
import com.example.cinema.entity.filmInfo.FilmInfo
import com.example.cinema.entity.fullInfoActor.FullInfoActor
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

//518a1c18-031a-423c-92ea-dbdb897c776b
//a16112fd-a6d3-480c-a1bc-7ece0ea36df5
//f1877a5c-f583-454d-96e7-5d4f5a242426
//6a2d7029-a0b8-4c64-8469-25bf56208176
//543764a7-ddaa-498c-9bbb-a6d2393a6c88
//02313c45-a6f6-47aa-9eb8-ea784b9b67f0
const val apiKey="543764a7-ddaa-498c-9bbb-a6d2393a6c88"
const val apiKeyForActor="543764a7-ddaa-498c-9bbb-a6d2393a6c88"
const val baseUrl="https://kinopoiskapiunofficial.tech/api/"

object CinemaRetrofitObject {
   private val retrofit= Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val cinemaApi: CinemaApi = retrofit.create(CinemaApi::class.java)
    val cinemaFullInfoApi: CinemaFullInfoApi = retrofit.create(CinemaFullInfoApi::class.java)
    val actorAndWorkerFullInfoApi: ActorAndWorkerFullInfoApi = retrofit.create(ActorAndWorkerFullInfoApi::class.java)
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

    @GET("v2.2/films/{filmId}/images")
    @Headers("X-API-KEY: $apiKey")
    suspend fun getListGalleryType(@Path("filmId") filmId:Int,@Query("type") type:String,@Query("page") page:Int=1):GalleryFilm
}

interface ActorAndWorkerFullInfoApi{
    @GET("v1/staff/{id}")
    @Headers("X-API-KEY: $apiKeyForActor")
    suspend fun getActorWorkerInfo(@Path("id") idActor: Int):FullInfoActor

}