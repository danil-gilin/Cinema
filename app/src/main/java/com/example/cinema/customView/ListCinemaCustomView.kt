package com.example.cinema.customView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
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
import com.example.cinema.service.adapterFilmActor.AdapterFilmActor
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
    var galleryFunction:((String,ImageView) -> Unit)?=null
    var actorFunction:((Int) -> Unit)?=null
    var typeListFunction: ((TypeListFilm) -> Unit)?=null
    var filmInfoFunction: ((Int) -> Unit)?=null
    var itemCount=0
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

    fun updateNameList(name:String){
        binding.genreName.text=name
    }

    fun updateAllFilmBtn(countItem:Int,itemCounts:Int=20){
        itemCount=itemCounts
        if (countItem<itemCounts){
            binding.txtAllFilm.visibility= View.GONE
        }else{
            binding.txtAllFilm.visibility= View.VISIBLE
        }
        binding.txtAllFilm.text="$countItem"
        binding.txtAllFilm.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.film_all,0)
    }

    fun updateListActor(listActor:Pair<String,List<ActorAndWorker>>,spanCount:Int,itemCounts:Int=20){
        val adapterActorAndWorker=ActorAdapter(){id->onClickActor(id)}
        binding.rcCinemaList.adapter=adapterActorAndWorker
        binding.rcCinemaList.layoutManager=GridLayoutManager(context,spanCount,GridLayoutManager.HORIZONTAL,false)
        if(listActor.second.size>itemCounts) {
            adapterActorAndWorker.submitList(listActor.second.subList(0, itemCounts))
        }else{
            adapterActorAndWorker.submitList(listActor.second)
        }
        binding.genreName.text=listActor.first
    }
    fun updateListWorker(listWorker:Pair<String,List<ActorAndWorker>>,spanCount:Int,itemCounts:Int=6){
        val adapterActorAndWorker=ActorAdapter(){id->onClickActor(id)}
        binding.rcCinemaList.adapter=adapterActorAndWorker
        binding.rcCinemaList.layoutManager=GridLayoutManager(context,spanCount,GridLayoutManager.HORIZONTAL,false)
        if (listWorker.second.size>itemCounts) {
            adapterActorAndWorker.submitList(listWorker.second.subList(0, itemCounts))
        }else{
            adapterActorAndWorker.submitList(listWorker.second)
        }
        binding.genreName.text=listWorker.first
    }

    fun updateListGallery(listGallery:Pair<String,List<GalleryItem>>){
        val adapterGallery=GalleryAdapter(){url,img->onClickGallery(url,img)}
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

    fun updateFilmActor(adapter:AdapterFilmActor){
        binding.txtAllFilm.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.film_all,0)
        if (adapter.itemCount<10){
            binding.txtAllFilm.visibility= View.VISIBLE
        }
        binding.rcCinemaList.adapter=adapter
    }

    fun onClick(){
        typeListFunction?.let { it(typeListFilmLocal) }
    }

    private fun onClickFilm(id:Int){
        filmInfoFunction?.let { it(id) }
    }

   private fun onClickGallery(imgUrl:String,view:ImageView){
        galleryFunction.let { it?.invoke(imgUrl,view) }
    }
    private fun onClickActor(id:Int){
        actorFunction?.let { it(id) }
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
    fun allClickEmpty(onClickAllGallery: () -> Unit) {
        binding.txtAllFilm.setOnClickListener {
            onClickAllGallery()
        }
    }
    fun clickGallery(onClickGallery: (String,ImageView) -> Unit) {
        galleryFunction=onClickGallery
    }
    fun clickActor(actorfun: ((Int) -> Unit)) {
        actorFunction=actorfun
    }
}
