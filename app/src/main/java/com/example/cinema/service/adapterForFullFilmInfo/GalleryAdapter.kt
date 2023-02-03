package com.example.cinema.service.adapterForFullFilmInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.GalerryItemBinding

import com.example.cinema.entity.galleryFilm.GalleryItem


class GalleryAdapter(val function: ((String, ImageView) -> Unit)?) :
    ListAdapter<GalleryItem, GalleryViewHolder>(GalleryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            GalerryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            Glide.with(imgGallery).load(item.imageUrl).centerCrop().into(imgGallery)
            imgGallery.transitionName = "img${position}"
            if (function != null) {
                root.setOnClickListener {
                    function?.invoke(item.imageUrl, imgGallery)
                }
            }
        }
    }


}


class GalleryViewHolder(val binding: GalerryItemBinding) : RecyclerView.ViewHolder(binding.root)


class GalleryDiffUtil : DiffUtil.ItemCallback<GalleryItem>() {
    override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem == newItem
    }
}