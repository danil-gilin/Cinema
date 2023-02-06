package com.example.cinema.service.adapterFilmActor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.CinemaItem2Binding
import com.example.cinema.databinding.CinemaItemBinding
import com.example.cinema.entity.fullInfoActor.FilmWithPosterAndActor

class AdapterFilmActor(private val allFilm:Boolean=false,private val onClickFilm:(Int)->Unit):ListAdapter<FilmWithPosterAndActor,RecyclerView.ViewHolder>(FilmActorDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(allFilm)
            return FilmActorAllHolder(CinemaItem2Binding.inflate(LayoutInflater.from(parent.context),parent,false))
        else
            return FilmActorHolder(CinemaItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is FilmActorHolder->{
                val item=getItem(position)
                with(holder.binding){
                    cinemaNameTxt.text=item.nameRu
                    cinemaGenre.text=item.nameEn
                    Glide.with(cinemaImg).load(item.posterUrlPreview).centerCrop().into(cinemaImg)
                    root.setOnClickListener {
                        onClickFilm?.invoke(item.filmId)
                    }
                    if (item.rating!=null){
                        cinemaRating.text=item.rating
                    }
                }
            }
            is FilmActorAllHolder->{
                val item=getItem(position)
                with(holder.binding){
                    cinemaNameTxt2.text=item.nameRu
                    cinemaGenre2.text=item.nameEn
                    Glide.with(cinemaImg2).load(item.posterUrlPreview).centerCrop().into(cinemaImg2)
                    root.setOnClickListener {
                        onClickFilm?.invoke(item.filmId)
                    }
                    if (item.rating!=null){
                        cinemaRating2.text=item.rating
                    }
                }
            }
        }

    }

}

class FilmActorHolder(val binding:CinemaItemBinding): RecyclerView.ViewHolder(binding.root)
class FilmActorAllHolder(val binding:CinemaItem2Binding): RecyclerView.ViewHolder(binding.root)

class FilmActorDiffUtil: DiffUtil.ItemCallback<FilmWithPosterAndActor>(){
    override fun areItemsTheSame(oldItem: FilmWithPosterAndActor, newItem: FilmWithPosterAndActor): Boolean {
        return oldItem.filmId==newItem.filmId
    }

    override fun areContentsTheSame(oldItem: FilmWithPosterAndActor, newItem: FilmWithPosterAndActor): Boolean {
        return oldItem==newItem
    }
}