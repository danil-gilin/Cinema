package com.example.cinema.service.adapterSearchFilms

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.databinding.SearchFilmItemBinding
import com.example.cinema.entity.searchFilm.SearchItem

class AdapterSearchFilms(val click: (Int) -> Unit) : PagingDataAdapter<SearchItem, SearchFilmsViewHolder>(SearchFilmsDiffUtil()){
    var watchFilm= listOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFilmsViewHolder {
        return SearchFilmsViewHolder(SearchFilmItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SearchFilmsViewHolder, position: Int) {
        val item=getItem(position)
        with(holder.binding){
            if(item?.nameRu!=null)
                cinemaNameTxtFilmSearch.text= item.nameRu
            else if (item?.nameEn!=null){
                cinemaNameTxtFilmSearch.text= item.nameEn
            }else{
                cinemaNameTxtFilmSearch.text=item?.nameOriginal
            }

            if(item?.ratingKinopoisk == null)
                cinemaRatingFilmHistory.visibility= View.GONE
            else
                cinemaRatingFilmHistory.text=item?.ratingKinopoisk.toString()
            Glide.with(cinemaImgFilmSearch).load(item?.posterUrl).centerCrop().into(cinemaImgFilmSearch)


            if(watchFilm.contains(item?.kinopoiskId)) {
                Log.d("watchFilm", "gooo")
                gradientImgFilmSearch.visibility = View.VISIBLE
                cinemaSeenFilmSearch.visibility = View.VISIBLE
            }
            else{
                gradientImgFilmSearch.visibility = View.GONE
                cinemaSeenFilmSearch.visibility = View.GONE
            }
            cinemaGenreFilmSearch.text="${item?.year}, ${item?.genres?.get(0)?.genre}"

            root.setOnClickListener {
                click(item?.kinopoiskId!!)
            }
        }
    }
}


class SearchFilmsViewHolder(val binding:SearchFilmItemBinding):RecyclerView.ViewHolder(binding.root)

class SearchFilmsDiffUtil: DiffUtil.ItemCallback<SearchItem>(){
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem.kinopoiskId==newItem.kinopoiskId
    }

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem==newItem
    }
}