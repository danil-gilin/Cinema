package com.example.cinema.service.adapterActorWorkerAll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.ActorAndWorkerItemBinding
import com.example.cinema.databinding.ActorWorkerAllItemBinding
import com.example.cinema.entity.actorAndWorker.ActorAndWorker

class AdapterActorWorkerAll:
    ListAdapter<ActorAndWorker, AdapterActorWorkerAllViewHolder>(AdapterActorWorkerAllDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterActorWorkerAllViewHolder {
        return AdapterActorWorkerAllViewHolder(
            ActorWorkerAllItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdapterActorWorkerAllViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            if (item.nameRu != "") {
                nameActorTxtAll.text = item.nameRu
            } else {
                nameActorTxtAll.text = item.nameEn
            }
            if (item.description == null) {
                nameActorInFilmTxtAll.text = item.professionText?.dropLast(1)
            } else  nameActorInFilmTxtAll.text =item.description.replaceFirstChar { it.uppercase() }
            Glide.with(immActorAll).load(item.posterUrl).centerCrop().into(immActorAll)
        }
    }

}

class AdapterActorWorkerAllViewHolder(val binding: ActorWorkerAllItemBinding) :
    RecyclerView.ViewHolder(binding.root)


class AdapterActorWorkerAllDiffUtil : DiffUtil.ItemCallback<ActorAndWorker>() {
    override fun areItemsTheSame(oldItem: ActorAndWorker, newItem: ActorAndWorker): Boolean {
        return oldItem.staffId == newItem.staffId
    }

    override fun areContentsTheSame(oldItem: ActorAndWorker, newItem: ActorAndWorker): Boolean {
        return oldItem == newItem
    }

}