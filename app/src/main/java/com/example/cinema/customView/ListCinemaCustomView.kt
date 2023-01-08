package com.example.cinema.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.databinding.ListCinemaCustomViewBinding
import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.service.CinemaAdapter

class ListCinemaCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    val adapterCinema= CinemaAdapter()


    lateinit var binding:ListCinemaCustomViewBinding
    init {
        binding=ListCinemaCustomViewBinding.inflate(LayoutInflater.from(context),this,true)
        binding.rcCinemaList.adapter=adapterCinema
        binding.rcCinemaList.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    }

    fun updateListCinema(list:List<Cinema>){
        adapterCinema.submitList(list)
    }

    fun updategenre(genre: String) {
        binding.genreName.text=genre[0].uppercase()+genre.substring(1)
    }

}
