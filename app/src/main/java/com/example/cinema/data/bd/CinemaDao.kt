package com.example.cinema.data.bd

import androidx.room.*
import com.example.cinema.entity.dbCinema.HistoryCollectionDB
import com.example.cinema.entity.dbCinema.LikeFilm
import com.example.cinema.entity.dbCinema.WantToWatchFilm
import com.example.cinema.entity.dbCinema.WatchFilm

@Dao
interface CinemaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWatchFilm(watchFilm: WatchFilm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLikeFilm(watchFilm: LikeFilm)

    @Query("SELECT * FROM WatchFilm WHERE id=:id")
    suspend fun getWatchFilm(id: Int): WatchFilm?

    @Query("SELECT id FROM WatchFilm")
    suspend fun getWatchFilmId(): List<Int>

    @Query("DELETE FROM WatchFilm WHERE id=:id")
    suspend fun deleteWatchFilm(id: Int)


    @Query("SELECT * FROM LikeFilm WHERE id=:id")
    suspend fun getLikeFilm(id: Int): LikeFilm?

    @Query("DELETE FROM LikeFilm WHERE id=:id")
    suspend fun deleteLikeFilm(id: Int)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWantToWatchFilm(watchFilm: WantToWatchFilm)

    @Query("SELECT * FROM WantToWatchFilm WHERE id=:id")
    suspend fun getWantToWatchFilm(id: Int): WantToWatchFilm?

    @Query("DELETE FROM WantToWatchFilm WHERE id=:id")
    suspend fun deleteWantToWatchFilm(id: Int)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(history: HistoryCollectionDB)

    @Query("SELECT * FROM History")
    suspend fun getHistory(): List<HistoryCollectionDB>

    @Query("SELECT COUNT(*) FROM History")
    suspend fun getTableHistorySize(): Int

    @Query("DELETE FROM History WHERE date_time = (SELECT MIN(date_time) FROM History)")
    suspend fun deleteOldestHistory()

    @Transaction
   suspend fun updateOrInsert(item: HistoryCollectionDB) {
        val existingItem = getItemHistoryById(item.idItem)
        if (existingItem != null) {
            deleteHistoryItem(existingItem)
        }
        addHistory(item)
    }

  @Query("SELECT * FROM History WHERE idItem = :id")
  suspend fun getItemHistoryById(id: Int): HistoryCollectionDB?

  @Delete
  suspend fun deleteHistoryItem(item: HistoryCollectionDB)


}