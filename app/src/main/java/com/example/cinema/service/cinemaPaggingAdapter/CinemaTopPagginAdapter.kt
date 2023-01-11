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
import com.example.cinema.databinding.CinemaItemFirst2Binding

import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.entity.cinemaTop.Film

class CinemaTopPagginAdapter(val clickFilm:(Int)->Unit) : PagingDataAdapter<Film, RecyclerView.ViewHolder>(CinemaPaggingTopDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            1 -> {
                return CinemaPaggingTopFirstViewHolder(
                    CinemaItemFirst2Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            0 -> {
                return CinemaPaggingTopViewHolder(
                    CinemaItem2Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return CinemaPaggingTopViewHolder(
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
        if( holder is CinemaPaggingTopViewHolder) {
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
        }else if(holder is CinemaPaggingTopFirstViewHolder) {
            val item = getItem(position)
            with(holder.binding) {
                Glide.with(cinemaFirstImg2).load(item!!.posterUrlPreview).centerCrop().into(cinemaFirstImg2)
                if (item.nameRu == null) {
                    cinemaFirstNameTxt2.text = item.nameEn
                } else {
                    cinemaFirstNameTxt2.text = item?.nameRu
                }
                if (item?.genres!!.isNotEmpty()) {
                    cinemaFirstGenre2.text = item!!.genres[0].genre
                }
                if (item?.rating != null) {
                    cinemaRating2First.text = item.rating
                } else {
                    cinemaRating2First.visibility = View.GONE
                }
                root.setOnClickListener {
                    clickFilm(item.filmId)
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

class CinemaPaggingTopViewHolder(val binding: CinemaItem2Binding): RecyclerView.ViewHolder(binding.root)
class CinemaPaggingTopFirstViewHolder(val binding: CinemaItemFirst2Binding): RecyclerView.ViewHolder(binding.root)

class CinemaPaggingTopDiffCallback : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem
    }
}