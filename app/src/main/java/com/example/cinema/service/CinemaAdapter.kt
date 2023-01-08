package com.example.cinema.service

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cinema.databinding.AllCinemaItemBinding
import com.example.cinema.databinding.CinemaItemBinding
import com.example.cinema.entity.cinema.Cinema

class CinemaAdapter : ListAdapter<Cinema, ViewHolder>(CinemaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType== 0) {
            return CinemaViewHolder(
                CinemaItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return AllCinemaViewHolder(
                AllCinemaItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is CinemaViewHolder) {
            val item = getItem(position)
            with(holder.binding) {
                Glide.with(cinemaImg).load(item.posterUrlPreview).centerCrop().into(cinemaImg)
                if (item.nameRu == null) {
                    cinemaNameTxt.text = item.nameOriginal
                } else {
                    cinemaNameTxt.text = item.nameRu
                }
                if (item.genres.isNotEmpty()) {
                    cinemaGenre.text = item.genres[0].genre
                }
                if (item.ratingKinopoisk != null) {
                    cinemaRating.text = item.ratingKinopoisk.toString()
                } else if (item.ratingImdb != null) {
                    cinemaRating.text = item.ratingImdb.toString()
                } else {
                    cinemaRating.visibility = View.GONE
                }
            }
        }else{
            with(holder as AllCinemaViewHolder) {
                holder.binding.root.setOnClickListener {
                    Log.d("AllCinema","все работает")
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && itemCount >=20) {
            1
        }else{
            0
        }
    }
}

class CinemaViewHolder(val binding: CinemaItemBinding):RecyclerView.ViewHolder(binding.root)

class AllCinemaViewHolder(val binding: AllCinemaItemBinding):RecyclerView.ViewHolder(binding.root)

class CinemaDiffCallback : DiffUtil.ItemCallback<Cinema>() {
    override fun areItemsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem.kinopoiskId == newItem.kinopoiskId
    }

    override fun areContentsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem == newItem
    }
}