package com.example.cinema.service.adapterForFullFilmInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.GalerryItemBinding
import com.example.cinema.databinding.GalerryItemFirstBinding
import com.example.cinema.entity.galleryFilm.GalleryItem

class GalleryAdapter : ListAdapter<GalleryItem,RecyclerView.ViewHolder>(GalleryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            1->{
                return GalleryViewFirstHolder(GalerryItemFirstBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else->{
                return GalleryViewHolder(GalerryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item=getItem(position)
        when(holder){
            is GalleryViewHolder->{
                with(holder.binding){
                    Glide.with(holder.binding.imgGallery).load(item.imageUrl).centerInside().into(holder.binding.imgGallery)
                }
            }
            is GalleryViewFirstHolder->{
                with(holder.binding){
                    Glide.with(holder.binding.imgGalleryFirst).load(item.imageUrl).centerInside().into(holder.binding.imgGalleryFirst)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) 1 else 2
    }
}


class GalleryViewHolder(val binding:GalerryItemBinding) : RecyclerView.ViewHolder(binding.root)
class GalleryViewFirstHolder(val binding: GalerryItemFirstBinding) : RecyclerView.ViewHolder(binding.root)

class GalleryDiffUtil : DiffUtil.ItemCallback<GalleryItem>() {
    override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem.imageUrl==newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem==newItem
    }
}