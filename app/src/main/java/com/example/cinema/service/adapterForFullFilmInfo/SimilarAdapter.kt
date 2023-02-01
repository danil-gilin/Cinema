package com.example.cinema.service.adapterForFullFilmInfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.CinemaItem2Binding
import com.example.cinema.databinding.CinemaItemBinding
import com.example.cinema.databinding.CinemaItemFirst2Binding
import com.example.cinema.databinding.CinemaItemFirstBinding
import com.example.cinema.entity.similarFilm.SimilarItem
import com.example.cinema.service.cinemaPaggingAdapter.CinemaPaggingTopFirstViewHolder
import com.example.cinema.service.cinemaPaggingAdapter.CinemaPaggingTopViewHolder

class SimilarAdapter(val genre: String, val clickFilm:(Int)->Unit,private val allFilmFlag: Boolean =false) :ListAdapter<SimilarItem,RecyclerView.ViewHolder>(SimilarDiffCallback()) {
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
            2->{
                return CinemaPaggingTopFirstViewHolder(
                    CinemaItemFirst2Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            3->{
                return CinemaPaggingTopViewHolder(
                    CinemaItem2Binding.inflate(
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
                   root.setOnClickListener { clickFilm(item.filmId) }
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
                   root.setOnClickListener { clickFilm(item.filmId) }
               }
           }
              is CinemaPaggingTopViewHolder->{
                  val item = getItem(position)
                  with(holder.binding) {
                      Glide.with(cinemaImg2).load(item!!.posterUrlPreview).centerCrop().into(cinemaImg2)
                      if (item.nameRu == null) {
                          cinemaNameTxt2.text = item.nameEn
                      } else {
                          cinemaNameTxt2.text = item?.nameRu
                      }
                      cinemaGenre2.text = genre
                      cinemaRating2.visibility = View.GONE
                      root.setOnClickListener {
                          clickFilm(item.filmId)
                      }
                  }
              }
                is CinemaPaggingTopFirstViewHolder->{
                    val item = getItem(position)
                    with(holder.binding) {
                        Glide.with(cinemaFirstImg2).load(item!!.posterUrlPreview).centerCrop().into(cinemaFirstImg2)
                        if (item.nameRu == null) {
                            cinemaFirstNameTxt2.text = item.nameEn
                        } else {
                            cinemaFirstNameTxt2.text = item?.nameRu
                        }
                        cinemaFirstGenre2.text = genre
                        cinemaRating2First.visibility = View.GONE
                        root.setOnClickListener {
                            clickFilm(item.filmId)
                        }
                    }
                }
       }
    }

    override fun getItemViewType(position: Int): Int {
        if(allFilmFlag) {
            if (position in 0..1) {
                return 2
            } else{
                return 3
            }
        }
        return if(position==0){ 0 }else{ 1 }
    }
}

class SimilarViewHolder(val binding:CinemaItemBinding):RecyclerView.ViewHolder(binding.root)
class SimilarFirstViewHolder(val binding:CinemaItemFirstBinding):RecyclerView.ViewHolder(binding.root)
class CinemaPaggingTopViewHolder(val binding: CinemaItem2Binding): RecyclerView.ViewHolder(binding.root)
class CinemaPaggingTopFirstViewHolder(val binding: CinemaItemFirst2Binding): RecyclerView.ViewHolder(binding.root)

class SimilarDiffCallback: DiffUtil.ItemCallback<SimilarItem>(){
    override fun areItemsTheSame(oldItem: SimilarItem, newItem: SimilarItem): Boolean {
        return oldItem.filmId==newItem.filmId
    }

    override fun areContentsTheSame(oldItem: SimilarItem, newItem: SimilarItem): Boolean {
        return oldItem==newItem
    }

}

