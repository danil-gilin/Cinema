package com.example.cinema.service.adapterAllFilmProfile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.CinemaItem2Binding
import com.example.cinema.entity.dbCinema.*

class AdapterAllFilmProfile(val clickFilm:(Int)->Unit):ListAdapter<FilmDB,AllFilmProfileViewHolder>(AllFilmProfileDiffUtil()) {
    var listIdWarchFilm= emptyList<Int>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllFilmProfileViewHolder {
        val binding = CinemaItem2Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AllFilmProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllFilmProfileViewHolder, position: Int) {
        val film = getItem(position)
        holder.binding.root.setOnClickListener {
            clickFilm(film.id)
        }
        if(film.rating == null){
            holder.binding.cinemaRating2.visibility = View.GONE
        }else{
            holder.binding.cinemaRating2.text = film.rating.toString()
        }

        Glide.with(holder.binding.cinemaImg2).load(film.img).centerCrop().into(holder.binding.cinemaImg2)

        holder.binding.cinemaRating2.text = film.rating.toString()
        holder.binding.cinemaNameTxt2.text = film.nameFilm
        holder.binding.cinemaGenre2.text = film.genre

        if(film !is WatchFilm) {
            if (film.id in listIdWarchFilm) {
                holder.binding.gradientImg2.visibility = View.VISIBLE
                holder.binding.cinemaSeen2.visibility = View.VISIBLE
            } else {
                holder.binding.gradientImg2.visibility = View.GONE
                holder.binding.cinemaSeen2.visibility = View.GONE
            }
        }
    }
}

class AllFilmProfileViewHolder(val binding:CinemaItem2Binding): RecyclerView.ViewHolder(binding.root)

class AllFilmProfileDiffUtil: DiffUtil.ItemCallback<FilmDB>(){
    override fun areItemsTheSame(oldItem: FilmDB, newItem: FilmDB): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FilmDB, newItem: FilmDB): Boolean {
        return oldItem.equals(newItem)
    }
}