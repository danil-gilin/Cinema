package com.example.cinema.service

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.AllCinemaItemBinding
import com.example.cinema.databinding.CinemaItemBinding
import com.example.cinema.entity.cinemaTop.Film

class CinemaTopAdapter: ListAdapter<Film, RecyclerView.ViewHolder>(CinemaTopDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType== 0) {
            return CinemaTopViewHolder(
                CinemaItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return AllCinemaTopViewHolder(
                AllCinemaItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CinemaTopViewHolder) {
            val item = getItem(position)
            with(holder.binding) {
                Glide.with(cinemaImg).load(item.posterUrlPreview).centerCrop().into(cinemaImg)
                if (item.nameRu == null) {
                    cinemaNameTxt.text = item.nameEn
                } else {
                    cinemaNameTxt.text = item.nameRu
                }
                if (item.genres.isNotEmpty()) {
                    cinemaGenre.text = item.genres[0].genre
                }
                if (item.rating != null) {
                    cinemaRating.text = item.rating.toString()
                } else {
                    cinemaRating.visibility = View.GONE
                }
            }
        }else{
            with(holder as AllCinemaTopViewHolder) {
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

class CinemaTopViewHolder(val binding: CinemaItemBinding): RecyclerView.ViewHolder(binding.root)

class AllCinemaTopViewHolder(val binding: AllCinemaItemBinding): RecyclerView.ViewHolder(binding.root)

class CinemaTopDiffCallback : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem
    }
}