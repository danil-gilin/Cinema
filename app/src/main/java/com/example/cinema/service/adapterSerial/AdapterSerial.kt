package com.example.cinema.service.adapterSerial

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.databinding.SerialSeasonsItemBinding
import com.example.cinema.databinding.SerialSeasonsItemFirstBinding
import com.example.cinema.entity.serialInfo.Episode
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class AdapterSerial:ListAdapter<Episode,RecyclerView.ViewHolder>(SeasonDiffUtil()) {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
         when(viewType){
         0->{
             val binding=SerialSeasonsItemFirstBinding.inflate(LayoutInflater.from(parent.context),parent,false)
             return SeasonFirstViewHolder(binding)
         }
         else->{
             val binding=SerialSeasonsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
             return SeasonViewHolder(binding)
         }
         }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder){
            is SeasonFirstViewHolder->{
                holder.binding.apply {
                    txtInfoSeriaFirst.text = item.nameRu
                }
            }
            is SeasonViewHolder->{
                holder.binding.apply {
                    var txtInfo="${item.episodeNumber} cерия"
                    if(item.nameRu.isNullOrEmpty()) {
                        txtInfo =txtInfo+", " +item.nameEn
                    }else {
                        txtInfo = txtInfo+", " +item.nameRu
                    }
                    txtInfoSeria.text=txtInfo

                    if(item.releaseDate.isNullOrEmpty()) {
                        txtDate.text = ""
                    }else {
                        val inputDate = item.releaseDate
                        val parsedDate = dateFormat.parse(inputDate)
                        val outputDate = outputDateFormat.format(parsedDate)
                        txtDate.text = outputDate
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position==0){
            return 0
        }else{
            return 1
        }
    }
}

class SeasonViewHolder(val binding: SerialSeasonsItemBinding): RecyclerView.ViewHolder(binding.root)
class SeasonFirstViewHolder(val binding: SerialSeasonsItemFirstBinding): RecyclerView.ViewHolder(binding.root)

class SeasonDiffUtil: DiffUtil.ItemCallback<Episode>(){
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.episodeNumber == newItem.episodeNumber
    }

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem == newItem
    }
}