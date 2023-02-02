package com.example.cinema.presenter.home.homepage.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentHomePageBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.typeListFilm.TypeListFilm
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    companion object {
        fun newInstance() = HomePageFragment()
    }

    @Inject
    lateinit var viewModelFactory: HomePageFactory

    private val viewModel: HomePageViewModel by viewModels { viewModelFactory }
    private var flagGetFilm=true
    private lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater)
        if (flagGetFilm){
            viewModel.getCinema()
            flagGetFilm=false
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { state ->
                when (state) {
                    is HomePageState.Loading -> {

                    }
                    is HomePageState.Success -> {
                            updateListsFilm(state)
                    }
                    is HomePageState.Error -> {

                    }
                }
            }
        }


        return binding.root
    }

    fun updateListsFilm(state: HomePageState.Success) {

                binding.cinemaList1.updateListCinema(state.genreCinema1!!.second.items)
                binding.cinemaList1.updategenre(state.genreCinema1!!.first)
                binding.cinemaList1.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
                binding.cinemaList1.setClickInfoFilm{id->onClickInfoFilm(id)}


                binding.cinemaList2.updateListCinema(state.genreCinema2!!.second.items)
                binding.cinemaList2.updategenre(state.genreCinema2!!.first)
                binding.cinemaList2.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
                binding.cinemaList2.setClickInfoFilm{id->onClickInfoFilm(id)}



                binding.cinemaList3.updateListCinema(state.premiers!!.second.items)
                binding.cinemaList3.updategenre(state.premiers.first)
                binding.cinemaList3.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
                binding.cinemaList3.setClickInfoFilm{id->onClickInfoFilm(id)}



                binding.cinemaList4.updateListCinemaTop(state.popular!!.second.films)
                binding.cinemaList4.updategenre(state.popular!!.first)
                binding.cinemaList4.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
                binding.cinemaList4.setClickInfoFilm{id->onClickInfoFilm(id)}




                binding.cinemaList5.updateListCinemaTop(state.popular1!!.second.films)
                binding.cinemaList5.updategenre(state.popular1!!.first)
                binding.cinemaList5.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
                binding.cinemaList5.setClickInfoFilm{id->onClickInfoFilm(id)}



                binding.cinemaList6.updateListCinema(state.serial!!.second.items)
                binding.cinemaList6.updategenre(state.serial!!.first)
                binding.cinemaList6.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
                binding.cinemaList6.setClickInfoFilm{id->onClickInfoFilm(id)}

    }

    fun onClickAllFilm(typeListFilm: TypeListFilm) {
        val bundle = Bundle()
        bundle.putParcelable(Constance.NAME_GENRE_ALL_FILM, typeListFilm)
        findNavController().navigate(R.id.action_homePageFragment_to_allFilmFragment, bundle)
    }

    fun onClickInfoFilm(id:Int) {
        val bundle = Bundle()
        Log.d("idCinema", "onClickInfoFilm: $id")
        bundle.putInt(Constance.FILM_FILM_INFO_ID, id)
        findNavController().navigate(R.id.action_homePageFragment_to_filmInfoFragment, bundle)
    }
}