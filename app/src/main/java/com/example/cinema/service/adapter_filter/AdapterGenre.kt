package com.example.cinema.service.adapter_filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.databinding.ItemSearchTextBinding
import com.example.cinema.entity.filterEntity.CountryFilter
import com.example.cinema.entity.filterEntity.GenreFilter

class AdapterGenre (private val click: (Int,String) -> Unit) : ListAdapter<GenreFilter, GenreViewHolder>(DiffutilGenre()) {
    var selectGenre =""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            ItemSearchTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val item=getItem(position)
        with(holder.binding){
            if(item.genre!=selectGenre){
                linearFilterItem.setBackgroundResource(R.color.white)
            }else{
                linearFilterItem.setBackgroundResource(R.color.select_item_back_ground)
            }
            nameFilter.text=item.genre.replaceFirstChar(Char::titlecase)
            root.setOnClickListener {
                click(item.id,item.genre.replaceFirstChar(Char::titlecase))
            }
        }
    }

}

class GenreViewHolder(val binding: ItemSearchTextBinding) : RecyclerView.ViewHolder(binding.root)

class DiffutilGenre : DiffUtil.ItemCallback<GenreFilter>() {
    override fun areItemsTheSame(oldItem: GenreFilter, newItem: GenreFilter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GenreFilter, newItem: GenreFilter): Boolean {
        return oldItem == newItem
    }
}