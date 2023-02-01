package com.example.cinema.service.adapterAllGallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.cinema.databinding.AllCinemaItemBinding
import com.example.cinema.databinding.AllGalleryItemBigBinding
import com.example.cinema.databinding.AllGalleryItemBinding
import com.example.cinema.entity.galleryFilm.GalleryItem

class AdapterAllGallery(private val clickImg:(String,ImageView)->Unit):ListAdapter<GalleryItem,ViewHolder>(GalleryAllDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType){
            1->{
                return GalleryAllViewHolder(AllGalleryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
            else->{
                return GalleryAll2ViewHolder(AllGalleryItemBigBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=getItem(position)
        when(holder){
            is GalleryAllViewHolder->{
                with(holder.binding){
                    galleryAllImg.transitionName="img${position}"
                    Glide.with(galleryAllImg).load(item.imageUrl).centerCrop().into(galleryAllImg)
                    galleryAllImg.setOnClickListener {
                        clickImg(item.imageUrl,galleryAllImg)
                    }
                }
            }
            is GalleryAll2ViewHolder->{
                with(holder.binding){
                    galleryAllImgBig.transitionName="img${position}"
                    Glide.with(galleryAllImgBig).load(item.imageUrl).centerCrop().into(galleryAllImgBig)
                    galleryAllImgBig.setOnClickListener {
                        clickImg(item.imageUrl,galleryAllImgBig)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if((position+1)%3==0) 2 else 1
    }
}

class GalleryAllViewHolder(val binding:AllGalleryItemBinding) :RecyclerView.ViewHolder(binding.root)
class GalleryAll2ViewHolder(val binding: AllGalleryItemBigBinding) :RecyclerView.ViewHolder(binding.root)

class GalleryAllDiffUtil: DiffUtil.ItemCallback<GalleryItem>(){
    override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem.imageUrl==newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem==newItem
    }

}