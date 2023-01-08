package com.example.cinema.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.CinemaItemBinding
import com.example.cinema.entity.cinema.Cinema

class CinemaAdapter : ListAdapter<Cinema, CinemaViewHolder>(CinemaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        return CinemaViewHolder(CinemaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            Glide.with(cinemaImg).load(item.posterUrlPreview).centerCrop().into(cinemaImg)
            if(item.nameRu==null){
                cinemaNameTxt.text=item.nameOriginal
            }else{
                cinemaNameTxt.text = item.nameRu
            }
            if(item.genres.isNotEmpty()){
                cinemaGenre.text = item.genres[0].genre
            }
        }
    }
}

class CinemaViewHolder(val binding: CinemaItemBinding):RecyclerView.ViewHolder(binding.root)

class CinemaDiffCallback : DiffUtil.ItemCallback<Cinema>() {
    override fun areItemsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem.kinopoiskId == newItem.kinopoiskId
    }

    override fun areContentsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem == newItem
    }
}