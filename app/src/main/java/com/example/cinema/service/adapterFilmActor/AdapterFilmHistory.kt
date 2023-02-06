package com.example.cinema.service.adapterFilmActor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.FilmHistoryItemBinding
import com.example.cinema.entity.fullInfoActor.FilmWithPosterAndActor

class AdapterFilmHistory(val onClickFilm:(Int)->Unit):ListAdapter<FilmWithPosterAndActor,FilmHistorViewHolder>(FilmHistoryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHistorViewHolder {
        return FilmHistorViewHolder(FilmHistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FilmHistorViewHolder, position: Int) {
        val film=getItem(position)
        with(holder.binding){
            if(film.nameRu==null){
                cinemaNameTxtFilmHistory.text=film.nameEn
            }else{
                cinemaNameTxtFilmHistory.text=film.nameRu
            }
            if(film.rating==null){
                cinemaRatingFilmHistory.visibility= View.GONE
            }else{
                cinemaRatingFilmHistory.text=film.rating.toString()
            }
            Glide.with(cinemaImgFilmHistory).load(film.posterUrlPreview).into(cinemaImgFilmHistory)

            var info=""
            if(film.year!=null){
                if (film.genre!=null){
                    info+="${film.year}, "
                }else{
                    info+="${film.year}"
                }
            }
            if (film.genre!=null){
                info+=film.genre
            }
            cinemaGenreFilmHistory.text=info

            root.setOnClickListener {
                onClickFilm(film.filmId)
            }
        }
    }
}

class FilmHistorViewHolder(val binding:FilmHistoryItemBinding): RecyclerView.ViewHolder(binding.root) {
}

class FilmHistoryDiffUtil: DiffUtil.ItemCallback<FilmWithPosterAndActor>() {
    override fun areItemsTheSame(oldItem: FilmWithPosterAndActor, newItem: FilmWithPosterAndActor): Boolean {
        return oldItem.filmId==newItem.filmId
    }

    override fun areContentsTheSame(oldItem: FilmWithPosterAndActor, newItem: FilmWithPosterAndActor): Boolean {
        return oldItem==newItem
    }
}