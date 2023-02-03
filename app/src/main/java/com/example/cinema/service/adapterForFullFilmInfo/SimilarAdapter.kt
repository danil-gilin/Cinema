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
import com.example.cinema.entity.similarFilm.SimilarItem
import com.example.cinema.service.cinemaPaggingAdapter.CinemaPaggingTopViewHolder

class SimilarAdapter(val genre: String, val clickFilm:(Int)->Unit,private val allFilmFlag: Boolean =false) :ListAdapter<SimilarItem,RecyclerView.ViewHolder>(SimilarDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
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
       }
    }

    override fun getItemViewType(position: Int): Int {
        if(allFilmFlag) {
          return 2
        }else{
            return 1
        }
    }
}

class SimilarViewHolder(val binding:CinemaItemBinding):RecyclerView.ViewHolder(binding.root)
class CinemaPaggingTopViewHolder(val binding: CinemaItem2Binding): RecyclerView.ViewHolder(binding.root)

class SimilarDiffCallback: DiffUtil.ItemCallback<SimilarItem>(){
    override fun areItemsTheSame(oldItem: SimilarItem, newItem: SimilarItem): Boolean {
        return oldItem.filmId==newItem.filmId
    }

    override fun areContentsTheSame(oldItem: SimilarItem, newItem: SimilarItem): Boolean {
        return oldItem==newItem
    }

}

