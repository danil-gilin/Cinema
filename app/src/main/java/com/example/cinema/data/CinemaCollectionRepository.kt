package com.example.cinema.data

import android.util.Log
import androidx.room.Insert
import androidx.room.Query
import com.example.cinema.data.bd.MovieCollectionDao
import com.example.cinema.entity.dbCinema.FilmDBLocal
import com.example.cinema.entity.dbCinema.MovieCollectionJoin
import com.example.cinema.entity.dbCinema.CollectionFilms
import com.example.cinema.entity.dbCinema.FilmDB
import javax.inject.Inject

class CinemaCollectionRepository @Inject constructor(
    private val movieCollectionDao: MovieCollectionDao
){


   suspend fun addFilmToCollection(film: FilmDBLocal, idCollection:Pair<Int,Boolean>) {
     if (movieCollectionDao.getMovieById(film.id) != null){
         if (idCollection.second){
             Log.d("DBCollection", "add film")
             val join= movieCollectionDao.getJoin(film.id, idCollection.first)
             if(join == null){
                 movieCollectionDao.addToCollection(MovieCollectionJoin(film.id, idCollection.first))
                 movieCollectionDao.updateCollectionSize(idCollection.first, movieCollectionDao.getCollectionSize(idCollection.first) + 1)
             }
         }
         else{
             Log.d("DBCollection", "delete film")
             val join= movieCollectionDao.getJoin(film.id, idCollection.first)
             if (join != null){
                 movieCollectionDao.removeFromCollection(MovieCollectionJoin(film.id, idCollection.first))
                 movieCollectionDao.updateCollectionSize(idCollection.first, movieCollectionDao.getCollectionSize(idCollection.first) - 1)
             }
         }
     }else{
         if (idCollection.second){
             Log.d("DBCollection", "add new film")
             movieCollectionDao.insertMovie(film)
            val join= movieCollectionDao.getJoin(film.id, idCollection.first)
             if(join == null){
                 movieCollectionDao.addToCollection(MovieCollectionJoin(film.id, idCollection.first))
                 movieCollectionDao.updateCollectionSize(idCollection.first, movieCollectionDao.getCollectionSize(idCollection.first) + 1)
             }
         }
     }
    }

    suspend fun getAllCollection(): List<CollectionFilms> {
        return movieCollectionDao.getAllCollections()
    }

   suspend fun addCollection(nameCollection: String):Int {
       if (nameCollection.isNotEmpty()){
           if(nameCollection !in  movieCollectionDao.getAllCollections().map { it.title }) {
               movieCollectionDao.insertCollection(CollectionFilms(title = nameCollection))
           }
           return movieCollectionDao.findCollectionByTytle(nameCollection)
       }else{
          return 0
       }
    }

   suspend fun getSelectCollection(id:Int): List<Int> {
        return movieCollectionDao.getCollectionsForMovie(id)
    }


    suspend fun deleteCollection(id: Int) {
        movieCollectionDao.deleteCollectionById(id)
    }
}