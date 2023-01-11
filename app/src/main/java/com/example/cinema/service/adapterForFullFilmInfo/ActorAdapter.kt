package com.example.cinema.service.adapterForFullFilmInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.ActorAndWorkerItemBinding
import com.example.cinema.databinding.ActorAndWorkerItemFirstBinding
import com.example.cinema.entity.actorAndWorker.ActorAndWorker

class ActorAdapter(val spanCount: Int) :ListAdapter<ActorAndWorker,RecyclerView.ViewHolder>(ActorDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            1->{
                return ActorFirstViewHolder(ActorAndWorkerItemFirstBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else->{
                return ActorViewHolder(ActorAndWorkerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item=getItem(position)
        when(holder){
            is ActorViewHolder->{
               with(holder.binding){
                   if (item.nameRu!=""){
                       nameActorTxt.text=item.nameRu
                   }else{
                       nameActorTxt.text=item.nameEn
                   }
                   nameActorInFilmTxt.text=item.description
                   Glide.with(immActor).load(item.posterUrl).centerCrop().into(immActor)
               }
            }
            is ActorFirstViewHolder->{
                with(holder.binding){
                    if (item.nameRu!=""){
                        nameActorTxtFirst.text=item.nameRu
                    }else{
                        nameActorTxtFirst.text=item.nameEn
                    }
                    nameActorInFilmTxtFirst.text=item.description
                    Glide.with(immActorFirst).load(item.posterUrl).centerCrop().into(immActorFirst)
                }
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if(position in 0 until spanCount) 1 else 2
    }

}

class ActorViewHolder(val binding:ActorAndWorkerItemBinding):RecyclerView.ViewHolder(binding.root)
class ActorFirstViewHolder(val binding: ActorAndWorkerItemFirstBinding):RecyclerView.ViewHolder(binding.root)

class ActorDiffUtil: DiffUtil.ItemCallback<ActorAndWorker>(){
    override fun areItemsTheSame(oldItem: ActorAndWorker, newItem: ActorAndWorker): Boolean {
        return oldItem.staffId==newItem.staffId
    }

    override fun areContentsTheSame(oldItem: ActorAndWorker, newItem: ActorAndWorker): Boolean {
        return oldItem==newItem
    }

}