package com.example.cinema.service.adapterHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.BaggageItemBinding
import com.example.cinema.databinding.CinemaItem2Binding
import com.example.cinema.databinding.CinemaItemBinding
import com.example.cinema.entity.dbCinema.HistoryCollectionDB
import com.example.cinema.service.adapterWatch.BaggageViewHolder

class AdapterAllHistory (val onClick : (Int,Boolean)-> Unit) : ListAdapter<HistoryCollectionDB, HistoryAllViewHolder>(HistoryAllDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAllViewHolder {
            return HistoryAllViewHolder(
                CinemaItem2Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: HistoryAllViewHolder, position: Int) {
            val item = getItem(position)
            with(holder.binding) {
                cinemaNameTxt2.text = item.title
                cinemaGenre2.text = item.description
                Glide.with(cinemaImg2).load(item.image).centerCrop().into(cinemaImg2)
                if (item.filmFlag) {
                    if (item.rating == 0.0) {
                        cinemaRating2.visibility = View.GONE
                    } else {
                        cinemaRating2.text = item.rating.toString()
                    }
                } else {
                    cinemaRating2.visibility = View.GONE
                }
                root.setOnClickListener {
                    onClick(item.idItem, item.filmFlag)
                }
            }

    }
}

class HistoryAllViewHolder(val binding: CinemaItem2Binding): RecyclerView.ViewHolder(binding.root)

class HistoryAllDiffUtil: DiffUtil.ItemCallback<HistoryCollectionDB>() {
    override fun areItemsTheSame(oldItem: HistoryCollectionDB, newItem: HistoryCollectionDB): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: HistoryCollectionDB, newItem: HistoryCollectionDB): Boolean {
        return oldItem==newItem
    }
}