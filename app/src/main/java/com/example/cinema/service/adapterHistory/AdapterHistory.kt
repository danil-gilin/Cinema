package com.example.cinema.service.adapterHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.BaggageItemBinding
import com.example.cinema.databinding.CinemaItemBinding
import com.example.cinema.entity.dbCinema.HistoryCollectionDB
import com.example.cinema.service.adapterWatch.BaggageViewHolder

class AdapterHistory (val onClick : (Int,Boolean)-> Unit,val delete:()->Unit) :ListAdapter<HistoryCollectionDB, RecyclerView.ViewHolder>(HistoryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1) {
            return BaggageViewHolder(BaggageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }else{
            return HistoryViewHolder(
                CinemaItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is BaggageViewHolder){
            holder.binding.root.setOnClickListener {
                delete()
            }
        }
        if(holder is HistoryViewHolder) {
            val item = getItem(position)
            with(holder.binding) {
                cinemaNameTxt.text = item.title
                cinemaGenre.text = item.description
                Glide.with(cinemaImg).load(item.image).centerCrop().into(cinemaImg)
                if (item.filmFlag) {
                    if (item.rating == 0.0) {
                        cinemaRating.visibility = View.GONE
                    } else {
                        cinemaRating.text = item.rating.toString()
                    }
                } else {
                    cinemaRating.visibility = View.GONE
                }
                root.setOnClickListener {
                    onClick(item.idItem, item.filmFlag)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            1
        }else{
            0
        }
    }
}

class HistoryViewHolder(val binding:CinemaItemBinding): RecyclerView.ViewHolder(binding.root)
class BaggageViewHolder(val binding : BaggageItemBinding):RecyclerView.ViewHolder(binding.root)

class HistoryDiffUtil: DiffUtil.ItemCallback<HistoryCollectionDB>() {
    override fun areItemsTheSame(oldItem: HistoryCollectionDB, newItem: HistoryCollectionDB): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: HistoryCollectionDB, newItem: HistoryCollectionDB): Boolean {
        return oldItem==newItem
    }
}