package com.example.cinema.presenter.home.homepage.allFilm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinema.R
import com.example.cinema.databinding.FragmentAllFilmBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.fullInfoActor.typeListFilm.TypeListFilm
import com.example.cinema.service.adapterFilmActor.AdapterFilmActor
import com.example.cinema.service.adapterForFullFilmInfo.SimilarAdapter
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
    private var typeListFilm: TypeListFilm?=null
     var adapterTop:CinemaTopPagginAdapter= CinemaTopPagginAdapter{onClickFilm(it)}
     var adapter: CinemaPaggingAdapter= CinemaPaggingAdapter{onClickFilm(it)}




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAllFilmBinding.inflate(inflater)
        arguments.let {
            typeListFilm = it?.getParcelable<TypeListFilm>(Constance.NAME_GENRE_ALL_FILM)
            if (typeListFilm != null) {
                Log.d("bag","typeListFilm.toString() 1")
                binding.genreNameAllFilm.text = typeListFilm?.name
                viewModel.getCinema(typeListFilm!!)
            }
           var typeListFilm2= it?.getParcelable<TypeListFilm>(Constance.ACTOR_LIST_FILM_FOR_ALL_FILM)
            if (typeListFilm2?.actorId != null) {
                Log.d("bag","typeListFilm.toString()")
                binding.genreNameAllFilm.text = typeListFilm2?.name
                viewModel.getCinemaForActor(typeListFilm2!!)
            }
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

        viewModel.semilarFilm?.onEach {
            val adapterSemillarFilm= SimilarAdapter(it.first,{onClickFilm(it)}, allFilmFlag = true)
            adapterSemillarFilm.submitList(it.second)
            binding.rcAllFilm.adapter=adapterSemillarFilm
        }?.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.actorFilm.onEach {
            val adapter= AdapterFilmActor(true,{ it->onClickFilm(it)})
            adapter.submitList(it)
            binding.rcAllFilm.adapter=adapter
        }?.launchIn(viewLifecycleOwner.lifecycleScope)


        binding.allFilmBack.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }

    fun initrc(){
        if(typeListFilm?.topFilmType!=null){
            binding.rcAllFilm.adapter=adapterTop
        }else if(typeListFilm?.semilarFilmId==null){
            binding.rcAllFilm.adapter=adapter
        }
        binding.rcAllFilm.layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
    }

    private fun onClickFilm(id: Int) {
        val bundle = Bundle()
        Log.d("idCinema", "onClickInfoFilm: $id")
        bundle.putInt(Constance.FILM_FILM_INFO_ID, id)
        findNavController().navigate(R.id.action_allFilmFragment_to_filmInfoFragment, bundle)
    }

}