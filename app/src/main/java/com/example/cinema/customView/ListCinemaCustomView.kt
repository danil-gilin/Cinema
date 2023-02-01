package com.example.cinema.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.databinding.ListCinemaCustomViewBinding
import com.example.cinema.entity.actorAndWorker.ActorAndWorker

import com.example.cinema.entity.cinema.Cinema
import com.example.cinema.entity.cinemaTop.Film
import com.example.cinema.entity.galleryFilm.GalleryItem
import com.example.cinema.entity.similarFilm.SimilarItem
import com.example.cinema.entity.typeListFilm.TypeListFilm
import com.example.cinema.service.adapterForFullFilmInfo.ActorAdapter
import com.example.cinema.service.adapterForFullFilmInfo.GalleryAdapter
import com.example.cinema.service.adapterForFullFilmInfo.SimilarAdapter
import com.example.cinema.service.cinemaAdapter.CinemaAdapter
import com.example.cinema.service.cinemaAdapter.CinemaTopAdapter

class ListCinemaCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    lateinit var typeListFilmLocal: TypeListFilm
    var galleryFunction:(() -> Unit)?=null
    var typeListFunction: ((TypeListFilm) -> Unit)?=null
    var filmInfoFunction: ((Int) -> Unit)?=null
    val adapterCinemaTop= CinemaTopAdapter({onClick()}, {onClickFilm(it)})
    val adapterCinema= CinemaAdapter({onClick()}, {onClickFilm(it)})


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

    fun updategenre(typeListFilm: TypeListFilm) {
        typeListFilmLocal=typeListFilm
        if(typeListFilm.name==""){
            binding.genreName.text="Попробуйте"
        }else {
            binding.genreName.text = typeListFilm.name[0].uppercase() + typeListFilm.name.substring(1)
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

    fun updateGenreFilmInfo(name:String){
        binding.genreName.text=name
    }

    fun updateAllFilmBtn(countItem:Int){
        binding.txtAllFilm.visibility= View.VISIBLE
        binding.txtAllFilm.text="$countItem"
        binding.txtAllFilm.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.film_all,0)
    }

    fun updateListActor(listActor:Pair<String,List<ActorAndWorker>>,spanCount:Int){
        val adapterActorAndWorker=ActorAdapter(spanCount)
        binding.rcCinemaList.adapter=adapterActorAndWorker
        binding.rcCinemaList.layoutManager=GridLayoutManager(context,spanCount,GridLayoutManager.HORIZONTAL,false)
        adapterActorAndWorker.submitList(listActor.second)
        binding.genreName.text=listActor.first
    }
    fun updateListWorker(listWorker:Pair<String,List<ActorAndWorker>>,spanCount:Int){
        val adapterActorAndWorker=ActorAdapter(spanCount)
        binding.rcCinemaList.adapter=adapterActorAndWorker
        binding.rcCinemaList.layoutManager=GridLayoutManager(context,spanCount,GridLayoutManager.HORIZONTAL,false)
        adapterActorAndWorker.submitList(listWorker.second)
        binding.genreName.text=listWorker.first
    }

    fun updateListGallery(listGallery:Pair<String,List<GalleryItem>>){
        val adapterGallery=GalleryAdapter(){onClickGallery()}
        binding.rcCinemaList.adapter=adapterGallery
        binding.rcCinemaList.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        adapterGallery.submitList(listGallery.second)
        binding.genreName.text=listGallery.first
    }


    fun updateListSimilarFilm(listSimilarFilm:Pair<String,List<SimilarItem>>,genre:String){
        val adapterSimilar=SimilarAdapter(genre, { it->onClickFilm(it) })
        binding.rcCinemaList.adapter=adapterSimilar
        binding.rcCinemaList.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        adapterSimilar.submitList(listSimilarFilm.second)
        binding.genreName.text=listSimilarFilm.first
    }


    fun onClick(){
        typeListFunction?.let { it(typeListFilmLocal) }
    }

    fun onClickFilm(id:Int){
        filmInfoFunction?.let { it(id) }
    }

    fun onClickGallery(){
        galleryFunction?.let { it() }
    }

    fun setClickAllFilm(onClickAllFilm: (TypeListFilm) -> Unit) {
        typeListFunction=onClickAllFilm
        binding.txtAllFilm.setOnClickListener {
            onClickAllFilm(typeListFilmLocal)
        }
    }

    fun setClickInfoFilm(onClickFilmId: (Int) -> Unit) {
        filmInfoFunction=onClickFilmId
    }

    fun allGalleryClick(onClickAllGallery: () -> Unit) {
        binding.txtAllFilm.setOnClickListener {
            onClickAllGallery()
        }
    }

    fun clickGallery(onClickGallery: () -> Unit) {
        galleryFunction=onClickGallery
    }
}
