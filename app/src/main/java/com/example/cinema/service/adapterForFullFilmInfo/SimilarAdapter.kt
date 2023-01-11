package com.example.cinema.service.adapterForFullFilmInfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.CinemaItemBinding
import com.example.cinema.databinding.CinemaItemFirstBinding
import com.example.cinema.entity.similarFilm.SimilarItem

class SimilarAdapter(val genre:String) :ListAdapter<SimilarItem,RecyclerView.ViewHolder>(SimilarDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            0->{
                return SimilarFirstViewHolder(
                    CinemaItemFirstBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            1->{
                return SimilarViewHolder(
                    CinemaItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else->{
                return SimilarViewHolder(
                    CinemaItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item=getItem(position)
       when(holder){
           is SimilarViewHolder->{
               with(holder.binding){
                   Glide.with(cinemaImg).load(item.posterUrlPreview).centerCrop().into(cinemaImg)
                   cinemaRating.visibility= View.GONE
                   if(item.nameRu!=null){
                       cinemaNameTxt.text=item.nameRu
                   }else if(item.nameEn!=null) {
                       cinemaNameTxt.text = item.nameEn
                   }else{
                       cinemaNameTxt.text=item.nameOriginal
                   }
                   cinemaGenre.text=genre
               }
           }
           is SimilarFirstViewHolder->{
               with(holder.binding){
                   Glide.with(cinemaFirstImg).load(item.posterUrlPreview).centerCrop().into(cinemaFirstImg)
                   cinemaRatingFirst.visibility= View.GONE
                   if(item.nameRu!=null){
                       cinemaFirstNameTxt.text=item.nameRu
                   }else if(item.nameEn!=null) {
                       cinemaFirstNameTxt.text = item.nameEn
                   }else{
                       cinemaFirstNameTxt.text=item.nameOriginal
                   }
                     cinemaFirstGenre.text=genre
               }
           }
       }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position==0){ 0 }else{ 1 }
    }
}

class SimilarViewHolder(val binding:CinemaItemBinding):RecyclerView.ViewHolder(binding.root)
class SimilarFirstViewHolder(val binding:CinemaItemFirstBinding):RecyclerView.ViewHolder(binding.root)

class SimilarDiffCallback: DiffUtil.ItemCallback<SimilarItem>(){
    override fun areItemsTheSame(oldItem: SimilarItem, newItem: SimilarItem): Boolean {
        return oldItem.filmId==newItem.filmId
    }

    override fun areContentsTheSame(oldItem: SimilarItem, newItem: SimilarItem): Boolean {
        return oldItem==newItem
    }

}

