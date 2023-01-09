package com.example.cinema.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.databinding.ListCinemaCustomViewBinding
import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.entity.cinemaTop.Film
import com.example.cinema.service.CinemaAdapter
import com.example.cinema.service.CinemaTopAdapter

class ListCinemaCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val adapterCinema= CinemaAdapter()
    val adapterCinemaTop= CinemaTopAdapter()


    lateinit var binding:ListCinemaCustomViewBinding
    init {
        binding=ListCinemaCustomViewBinding.inflate(LayoutInflater.from(context),this,true)
        binding.rcCinemaList.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    }

    fun updateListCinema(list: List<Cinema>){
        binding.rcCinemaList.adapter=adapterCinema
        if(list.size>=20){
            binding.txtAllFilm.visibility= View.VISIBLE
            adapterCinema.submitList(list.subList(0,20))
        }else{
            adapterCinema.submitList(list)
        }
    }

    fun updategenre(genre: String) {
        if(genre==""){
            binding.genreName.text="Попробуйте"
        }else {
            binding.genreName.text = genre[0].uppercase() + genre.substring(1)
        }
    }

    fun updateListCinemaTop(films: List<Film>) {
        binding.rcCinemaList.adapter=adapterCinemaTop
        if(films.size>=20){
            binding.txtAllFilm.visibility= View.VISIBLE
            adapterCinemaTop.submitList(films.subList(0,20))
        }else {
            adapterCinemaTop.submitList(films)
        }
    }

}
