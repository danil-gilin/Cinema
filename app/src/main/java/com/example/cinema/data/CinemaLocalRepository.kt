package com.example.cinema.data

import com.example.cinema.data.bd.CinemaDao
import com.example.cinema.entity.dbCinema.HistoryCollectionDB
import com.example.cinema.entity.dbCinema.LikeFilm
import com.example.cinema.entity.dbCinema.WantToWatchFilm
import com.example.cinema.entity.dbCinema.WatchFilm
import javax.inject.Inject

class CinemaLocalRepository @Inject constructor(
    private val cinemaDao: CinemaDao
) {
    suspend fun addWatchFilm(watchFilm: WatchFilm) {
        cinemaDao.addWatchFilm(watchFilm)
    }

    suspend fun getWatchFilm(id: Int): WatchFilm? {
        return cinemaDao.getWatchFilm(id)
    }

    suspend fun getWatchFilmId(): List<Int> {
        return cinemaDao.getWatchFilmId()
    }

    suspend fun deleteWatchFilm(id: Int){
        cinemaDao.deleteWatchFilm(id)
    }

    suspend fun getLikeFilm(kinopoiskId: Int): LikeFilm? {
        return cinemaDao.getLikeFilm(kinopoiskId)
    }

    suspend fun addLikeFilm(watchFilm: LikeFilm) {
        cinemaDao.addLikeFilm(watchFilm)
    }

    suspend fun deleteLikeFilm(id: Int){
        cinemaDao.deleteLikeFilm(id)
    }


    suspend fun getWantToWatchFilmFilm(kinopoiskId: Int): WantToWatchFilm? {
        return cinemaDao.getWantToWatchFilm(kinopoiskId)
    }

    suspend fun addWantToWatchFilmFilm(watchFilm: WantToWatchFilm) {
        cinemaDao.addWantToWatchFilm(watchFilm)
    }

    suspend fun deleteWantToWatchFilmFilm(id: Int){
        cinemaDao.deleteWantToWatchFilm(id)
    }

   suspend fun addHistory(history: HistoryCollectionDB) {
       if (cinemaDao.getTableHistorySize() >= 15){
              cinemaDao.deleteOldestHistory()
       }
       cinemaDao.updateOrInsert(history)
    }

   suspend fun getHistory(): List<HistoryCollectionDB> {
        return cinemaDao.getHistory()
    }

  suspend  fun getAllWatchFilm(): List<WatchFilm> {
            return cinemaDao.getAllWatchFilm()
    }

  suspend  fun dellAllWatchFilm(){
        cinemaDao.deleteAllWatchFilm()
    }

  suspend  fun dellAllHistory() {
        cinemaDao.deleteAllHistory()
    }

   suspend fun getLikeFilmSize(): Int {
        return cinemaDao.getLikeFilmSize()
    }

   suspend fun getWantToWatchFilmSize(): Int {
            return cinemaDao.getWantToWatchFilmSize()
    }
}