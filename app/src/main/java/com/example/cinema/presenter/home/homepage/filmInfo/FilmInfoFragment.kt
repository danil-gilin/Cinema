package com.example.cinema.presenter.home.homepage.filmInfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.databinding.FragmentFilmInfoBinding
import com.example.cinema.entity.Constance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_film_info.*
import javax.inject.Inject

@AndroidEntryPoint
class FilmInfoFragment : Fragment() {

    companion object {
        fun newInstance() = FilmInfoFragment()
    }

    @Inject
    lateinit var factory: FilmFactory

    private val viewModel: FilmInfoViewModel by viewModels { factory }
    lateinit var binding: FragmentFilmInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmInfoBinding.inflate(inflater)
        arguments.let {
            val id = it?.getInt(Constance.FILM_FILM_INFO_ID)
            viewModel.getFilm(id!!)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { state ->
                when (state) {
                    is FilmInfoState.Loading -> {

                    }
                    is FilmInfoState.Success -> {
                        Log.d("idCinema", state.imgLogo.toString())
                        if(state.imgLogo!=null){
                            binding.posterImg.visibility=View.VISIBLE
                            Glide.with(binding.posterImg).load(state.imgLogo).centerInside().into(binding.posterImg)
                        }
                        Glide.with(binding.filmFullImg).load(state.imgPreview).centerCrop().into(binding.filmFullImg)
                        binding.shortInfo1.text = state.shotInfo1
                        binding.shortInfo2.text = state.shotInfo2
                        binding.shortInfo3.text = state.shotInfo3

                        binding.rcActor.updateListActor(state.actorList,3)
                        binding.rcActor.updateAllFilmBtn(state.actorList.second.size)
                        binding.rcFilmWorker.updateListWorker(state.workerList,2)
                        binding.rcFilmWorker.updateAllFilmBtn(state.workerList.second.size)
                        if(state.galleryList.second.isEmpty()){
                            binding.rcGallery.visibility=View.GONE
                        }else{
                            binding.rcGallery.updateListGallery(state.galleryList)
                            binding.rcGallery.updateAllFilmBtn(state.galleryList.second.size)
                        }
                       if(state.similarList.second.isEmpty()) {
                           binding.rcFilm.visibility = View.GONE
                       }else {
                           binding.rcFilm.updateListSimilarFilm(state.similarList, state.genre)
                           binding.rcFilm.updateAllFilmBtn(state.similarList.second.size)
                       }

                        if(state.infoSerial!=null){
                            binding.sessonInfo.text=state.infoSerial
                        }else{
                            binding.linearSerilaInfo.visibility=View.GONE
                        }
                    }
                    is FilmInfoState.Error -> {

                    }
                }
            }
        }


        return binding.root
    }

}