package com.example.cinema.service.cinemaPaggingAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.cinema.databinding.CinemaItemFirst2Binding
import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.entity.typeListFilm.TypeListFilm

class CinemaPaggingAdapter(): PagingDataAdapter<Cinema, RecyclerView.ViewHolder>(CinemaPaggingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            1 -> {
                return CinemaPaggingFirstViewHolder(
                    CinemaItemFirst2Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            0 -> {
                return CinemaPaggingViewHolder(
                    CinemaItem2Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return CinemaPaggingViewHolder(
                    CinemaItem2Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CinemaPaggingViewHolder) {
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
            }
        }else if(holder is CinemaPaggingFirstViewHolder){
            val item = getItem(position)
            with(holder.binding) {
                Glide.with(cinemaFirstImg2).load(item!!.posterUrlPreview).centerCrop().into(cinemaFirstImg2)
                if (item.nameRu == null) {
                    cinemaFirstNameTxt2.text = item.nameOriginal
                } else {
                    cinemaFirstNameTxt2.text = item?.nameRu
                }
                if (item?.genres!!.isNotEmpty()) {
                    cinemaFirstGenre2.text = item!!.genres[0].genre
                }
                if (item?.ratingKinopoisk != null) {
                    cinemaRating2First.text = item.ratingKinopoisk.toString()
                } else {
                    cinemaRating2First.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return  if(position in 0..1) {
            1
        } else {
            0
        }
    }
}

class CinemaPaggingViewHolder(val binding: CinemaItem2Binding): RecyclerView.ViewHolder(binding.root)
class CinemaPaggingFirstViewHolder(val binding: CinemaItemFirst2Binding): RecyclerView.ViewHolder(binding.root)

class CinemaPaggingDiffCallback : DiffUtil.ItemCallback<Cinema>() {
    override fun areItemsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem.kinopoiskId == newItem.kinopoiskId
    }

    override fun areContentsTheSame(oldItem: Cinema, newItem: Cinema): Boolean {
        return oldItem == newItem
    }
}