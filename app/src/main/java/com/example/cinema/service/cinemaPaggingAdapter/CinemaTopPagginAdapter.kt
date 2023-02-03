package com.example.cinema.service.cinemaPaggingAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cinema.databinding.CinemaItem2Binding

import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.entity.cinemaTop.Film

class CinemaTopPagginAdapter(val clickFilm: (Int) -> Unit) :
    PagingDataAdapter<Film, CinemaPaggingTopViewHolder>(CinemaPaggingTopDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaPaggingTopViewHolder {

        return CinemaPaggingTopViewHolder(
            CinemaItem2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CinemaPaggingTopViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            Glide.with(cinemaImg2).load(item!!.posterUrlPreview).centerCrop().into(cinemaImg2)
            if (item.nameRu == null) {
                cinemaNameTxt2.text = item.nameEn
            } else {
                cinemaNameTxt2.text = item?.nameRu
            }
            if (item?.genres!!.isNotEmpty()) {
                cinemaGenre2.text = item!!.genres[0].genre
            }
            if (item?.rating != null) {
                cinemaRating2.text = item.rating
            } else {
                cinemaRating2.visibility = View.GONE
            }
            root.setOnClickListener {
                clickFilm(item.filmId)
            }
        }
    }
}

class CinemaPaggingTopViewHolder(val binding: CinemaItem2Binding) :
    RecyclerView.ViewHolder(binding.root)

class CinemaPaggingTopDiffCallback : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem
    }
}