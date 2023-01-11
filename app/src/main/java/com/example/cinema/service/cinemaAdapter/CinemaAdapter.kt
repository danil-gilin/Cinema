package com.example.cinema.service.cinemaAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cinema.databinding.AllCinemaItemBinding
import com.example.cinema.databinding.CinemaItemBinding
import com.example.cinema.databinding.CinemaItemFirstBinding
import com.example.cinema.entity.cinema.Cinema


class CinemaAdapter(val onClickAllFilm: () -> Unit,val clickFilm:(Int)->Unit) : ListAdapter<Cinema, ViewHolder>(
    CinemaDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == 0) {
            return CinemaViewHolder(
                CinemaItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else if(viewType==1) {
            return AllCinemaViewHolder(
                AllCinemaItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else{
            return CinemaFirstViewHolder(
                CinemaItemFirstBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is CinemaViewHolder) {
            if (position == 0) {
                val params = holder.binding.root.layoutParams as RecyclerView.LayoutParams
                Log.d("pagedCinema", "position = $position")
                params.setMargins(
                    54,
                    holder.binding.root.marginTop,
                    holder.binding.root.marginRight,
                    holder.binding.root.marginBottom
                )
                holder.binding.root.layoutParams = params
            }
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
                root.setOnClickListener {
                    clickFilm(item.kinopoiskId)
                }
            }
        } else  if (holder is AllCinemaViewHolder){
            with(holder) {
                holder.binding.root.setOnClickListener {
                    onClickAllFilm()
                }
            }
        }else{
            val item = getItem(position)
            with(holder as CinemaFirstViewHolder) {
                Glide.with(binding.cinemaFirstImg).load(item.posterUrlPreview).centerCrop().into(binding.cinemaFirstImg)
                if (item.nameRu == null) {
                    binding.cinemaFirstNameTxt.text = item.nameOriginal
                } else {
                    binding.cinemaFirstNameTxt.text = item.nameRu
                }
                if (item.genres.isNotEmpty()) {
                    binding.cinemaFirstGenre.text = item.genres[0].genre
                }
                if (item.ratingKinopoisk != null) {
                    binding.cinemaRatingFirst.text = item.ratingKinopoisk.toString()
                } else{
                    binding.cinemaRatingFirst.visibility = View.GONE
                }
                binding.root.setOnClickListener {
                    clickFilm(item.kinopoiskId)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && itemCount >= 20) {
            1
        }else if(position ==0) {
            2
        } else {
            0
        }
    }
}

class CinemaViewHolder(val binding: CinemaItemBinding) : RecyclerView.ViewHolder(binding.root)
class CinemaFirstViewHolder(val binding: CinemaItemFirstBinding) :
    RecyclerView.ViewHolder(binding.root)

class AllCinemaViewHolder(val binding: AllCinemaItemBinding) : RecyclerView.ViewHolder(binding.root)

class CinemaDiffCallback : DiffUtil.ItemCallback<Cinema>() {
    override fun areItemsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem.kinopoiskId == newItem.kinopoiskId
    }

    override fun areContentsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem == newItem
    }
}