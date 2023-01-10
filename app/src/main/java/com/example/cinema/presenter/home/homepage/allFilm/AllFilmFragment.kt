package com.example.cinema.presenter.home.homepage.allFilm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinema.databinding.FragmentAllFilmBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.typeListFilm.TypeListFilm
import com.example.cinema.service.cinemaPaggingAdapter.CinemaPaggingAdapter
import com.example.cinema.service.cinemaPaggingAdapter.CinemaTopPagginAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class AllFilmFragment : Fragment() {

    companion object {
        fun newInstance() = AllFilmFragment()
    }

    @Inject
    lateinit var factory: AllFilmFactory

    private val viewModel: AllFilmViewModel by viewModels { factory }
    lateinit var binding: FragmentAllFilmBinding
    lateinit var typeListFilm: TypeListFilm
     var adapterTop:CinemaTopPagginAdapter= CinemaTopPagginAdapter()
     var adapter: CinemaPaggingAdapter= CinemaPaggingAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAllFilmBinding.inflate(inflater)
        arguments.let {
            typeListFilm = it?.getParcelable<TypeListFilm>(Constance.NAME_GENRE_ALL_FILM)!!
            binding.genreNameAllFilm.text=typeListFilm.name
            viewModel.getCinema(typeListFilm)
        }
        initrc()





        viewModel.pagedCinema?.onEach {
            Log.d("pagedCinema",it.toString())
            adapter.submitData(it)
        }?.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.typeListTop?.onEach {
            Log.d("pagedCinema",it.toString())

            adapterTop.submitData(it)
        }?.launchIn(viewLifecycleOwner.lifecycleScope)


        binding.allFilmBack.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }

    fun initrc(){
        if(typeListFilm.topFilmType!=null){
            binding.rcAllFilm.adapter=adapterTop
        }else{
            binding.rcAllFilm.adapter=adapter
        }
        binding.rcAllFilm.layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
    }


}