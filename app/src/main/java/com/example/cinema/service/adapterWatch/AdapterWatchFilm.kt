package com.example.cinema.service.adapterWatch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cinema.databinding.BaggageItemBinding
import com.example.cinema.databinding.CinemaItemBinding
import com.example.cinema.entity.dbCinema.WatchFilm

class AdapterWatchFilm(val onClick : (Int)-> Unit,val delete:()->Unit) : ListAdapter<WatchFilm,ViewHolder>(WatchDiffutil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType==1){
            return BaggageViewHolder(BaggageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }else{
            return WatchViewHolder(CinemaItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder){
            is WatchViewHolder->{
                val item=getItem(position)
               with(holder.binding){
                   cinemaNameTxt.text=item.nameFilm
                   Glide.with(holder.itemView.context).load(item.img).centerCrop().into(cinemaImg)
                    cinemaGenre.text=item.genre
                   if(item.rating!=null){
                       cinemaRating.text=item.rating.toString()
                   }else{
                       cinemaRating.visibility= View.GONE
                   }
                     root.setOnClickListener {
                          onClick(item.id)
                     }
               }
            }
            is BaggageViewHolder->{
               holder.binding.root.setOnClickListener {
                   delete()
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

class WatchViewHolder(val binding: CinemaItemBinding):RecyclerView.ViewHolder(binding.root)
class BaggageViewHolder(val binding :BaggageItemBinding):RecyclerView.ViewHolder(binding.root)

class WatchDiffutil : DiffUtil.ItemCallback<WatchFilm>(){
    override fun areItemsTheSame(oldItem: WatchFilm, newItem: WatchFilm): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: WatchFilm, newItem: WatchFilm): Boolean {
        return oldItem==newItem
    }

}