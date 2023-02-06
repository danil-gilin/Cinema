package com.example.cinema.service.adapterForFullFilmInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.ActorAndWorkerItemBinding
import com.example.cinema.entity.actorAndWorker.ActorAndWorker

class ActorAdapter(val actorFunction:((Int) -> Unit)?) :
    ListAdapter<ActorAndWorker, ActorViewHolder>(ActorDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(
            ActorAndWorkerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            if (item.nameRu != "") {
                nameActorTxt.text = item.nameRu
            } else {
                nameActorTxt.text = item.nameEn
            }
            if (item.description == null) {
                nameActorInFilmTxt.text = item.professionText?.dropLast(1)
            } else nameActorInFilmTxt.text =  item.description.replaceFirstChar { it.uppercase() }
            Glide.with(immActor).load(item.posterUrl).centerCrop().into(immActor)
            root.setOnClickListener {
                actorFunction?.invoke(item.staffId)
            }
        }
    }

}

class ActorViewHolder(val binding: ActorAndWorkerItemBinding) :
    RecyclerView.ViewHolder(binding.root)


class ActorDiffUtil : DiffUtil.ItemCallback<ActorAndWorker>() {
    override fun areItemsTheSame(oldItem: ActorAndWorker, newItem: ActorAndWorker): Boolean {
        return oldItem.staffId == newItem.staffId
    }

    override fun areContentsTheSame(oldItem: ActorAndWorker, newItem: ActorAndWorker): Boolean {
        return oldItem == newItem
    }

}