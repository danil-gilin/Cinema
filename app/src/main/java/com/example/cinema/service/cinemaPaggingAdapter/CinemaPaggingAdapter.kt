package com.example.cinema.service.cinemaPaggingAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.AllCinemaItemBinding
import com.example.cinema.databinding.CinemaItem2Binding
import com.example.cinema.databinding.CinemaItemBinding
import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.entity.fullInfoActor.typeListFilm.TypeListFilm

class CinemaPaggingAdapter(val clickFilm:(Int)->Unit): PagingDataAdapter<Cinema, CinemaPaggingViewHolder>(CinemaPaggingDiffCallback()) {

    private  var watchFilms= emptyList<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaPaggingViewHolder {
                return CinemaPaggingViewHolder(
                    CinemaItem2Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
    }

    override fun onBindViewHolder(holder: CinemaPaggingViewHolder, position: Int) {
            val item = getItem(position)
            with(holder.binding) {
                Glide.with(cinemaImg2).load(item!!.posterUrlPreview).centerCrop().into(cinemaImg2)
                if (item.nameRu == null) {
                    cinemaNameTxt2.text = item.nameOriginal
                } else {
                    cinemaNameTxt2.text = item?.nameRu
                }
                if (item?.genres!!.isNotEmpty()) {
                    cinemaGenre2.text = item!!.genres[0].genre
                }
                if (item?.ratingKinopoisk != null) {
                    cinemaRating2.text = item.ratingKinopoisk.toString()
                } else if (item?.ratingImdb != null) {
                    cinemaRating2.text = item.ratingImdb.toString()
                } else {
                    cinemaRating2.visibility = View.GONE
                }
                root.setOnClickListener {
                    clickFilm(item.kinopoiskId)
                }
                if (item.kinopoiskId in watchFilms){
                    Log.d("item.kinopoiskId", "onBindViewHolder: ${item.kinopoiskId} ${watchFilms}")
                    gradientImg2.visibility=View.VISIBLE
                    cinemaSeen2.visibility=View.VISIBLE
                }else{
                    gradientImg2.visibility=View.GONE
                    cinemaSeen2.visibility=View.GONE
                }
            }
    }

    fun updateWatchFilms(watchFilmsId:List<Int>){
        watchFilms=watchFilmsId
    }
}

class CinemaPaggingViewHolder(val binding: CinemaItem2Binding): RecyclerView.ViewHolder(binding.root)

class CinemaPaggingDiffCallback : DiffUtil.ItemCallback<Cinema>() {
    override fun areItemsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem.kinopoiskId == newItem.kinopoiskId
    }

    override fun areContentsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem == newItem
    }
}