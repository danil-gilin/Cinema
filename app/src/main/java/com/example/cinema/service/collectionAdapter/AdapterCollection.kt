package com.example.cinema.service.collectionAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.databinding.ItemColllectionBinding
import com.example.cinema.entity.dbCinema.CollectionFilms

class AdapterCollection:ListAdapter<CollectionFilms,CollectionViewHolder>(CollectionDiffutil()) {
    var selectCollection= arrayListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(ItemColllectionBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item= getItem(position)
        holder.binding.collectionName.text=item.title
        holder.binding.collectionSize.text=item.size.toString()
        if (item.id in selectCollection){
            holder.binding.collectionImg.setImageResource(R.drawable.icon_select_collection)
        }else{
            holder.binding.collectionImg.setImageResource(R.drawable.icon_un_select_collection)
        }
        holder.binding.root.setOnClickListener {
            if (item.id in selectCollection){
                selectCollection.remove(item.id)
                holder.binding.collectionImg.setImageResource(R.drawable.icon_un_select_collection)
            }else{
                selectCollection.add(item.id)
                holder.binding.collectionImg.setImageResource(R.drawable.icon_select_collection)
            }
        }
    }

}

class CollectionViewHolder(val binding:ItemColllectionBinding): RecyclerView.ViewHolder(binding.root)

class CollectionDiffutil: DiffUtil.ItemCallback<CollectionFilms>() {
    override fun areItemsTheSame(oldItem: CollectionFilms, newItem: CollectionFilms): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CollectionFilms, newItem: CollectionFilms): Boolean {
        return oldItem == newItem
    }
}