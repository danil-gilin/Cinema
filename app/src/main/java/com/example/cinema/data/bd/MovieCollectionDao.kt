package com.example.cinema.data.bd

import android.util.Log
import androidx.room.*
import com.example.cinema.entity.dbCinema.CollectionFilms
import com.example.cinema.entity.dbCinema.FilmDBLocal
import com.example.cinema.entity.dbCinema.MovieCollectionJoin


@Dao
interface MovieCollectionDao {

    @Query("SELECT size FROM collections WHERE id = :collectionId")
    suspend fun getCollectionSize(collectionId: Int): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToCollection(join: MovieCollectionJoin)

    @Delete
    suspend fun removeFromCollection(join: MovieCollectionJoin)

    @Query("SELECT * FROM movie_collection_join WHERE movieId = :movieId AND collectionId = :collectionId")
    suspend fun getJoin(movieId: Int, collectionId: Int): MovieCollectionJoin?


    @Query("UPDATE collections SET size = :size WHERE id = :collectionId")
    suspend fun updateCollectionSize(collectionId: Int, size: Int)


    @Query("SELECT FilmDBLocal.* FROM FilmDBLocal INNER JOIN movie_collection_join ON id = movie_collection_join.movieId WHERE movie_collection_join.collectionId = :collectionId")
    suspend fun getMoviesForCollection(collectionId: Int): List<FilmDBLocal>

    @Query("SELECT * FROM FilmDBLocal")
    suspend  fun getAllMovies(): List<FilmDBLocal>

    @Query("SELECT collectionId FROM movie_collection_join WHERE movieId = :movieId")
    suspend fun getCollectionsForMovie(movieId: Int): List<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: FilmDBLocal)


    @Query("SELECT * FROM FilmDBLocal WHERE id = :id")
    suspend fun getMovieById(id: Int): FilmDBLocal?

    @Query("DELETE FROM FilmDBLocal")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM collections")
    suspend  fun getAllCollections(): List<CollectionFilms>

    @Insert
   suspend fun insertCollection(collection: CollectionFilms)

    @Query("DELETE FROM collections")
    suspend fun deleteAllCollections()

    @Query("SELECT id FROM collections WHERE title = :nameCollection")
    suspend fun findCollectionByTytle(nameCollection: String):Int

    //dell collection by id and all film in this collection
    @Transaction
    suspend fun deleteCollectionById(id: Int) {
        // Удаляем все связи фильмов с коллекцией
        val joins = getCollectionsJoinById(id)
        joins.forEach {
            removeFromCollection(it)
        }
        // Удаляем коллекцию
        deleteCollection(id)
    }

    @Query("SELECT * FROM movie_collection_join WHERE collectionId = :id")
    suspend fun getCollectionsJoinById(id: Int): List<MovieCollectionJoin>

    @Query("DELETE FROM collections WHERE id = :id")
    suspend fun deleteCollection(id: Int)

}