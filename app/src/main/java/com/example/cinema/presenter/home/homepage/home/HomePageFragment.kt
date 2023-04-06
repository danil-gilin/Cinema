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
import com.example.cinema.customView.ErrorBottomSheet
import com.example.cinema.customView.LoaderFragment
import com.example.cinema.databinding.FragmentHomePageBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.fullInfoActor.typeListFilm.TypeListFilm
import com.example.cinema.service.cinemaAdapter.CinemaAdapter
import com.example.cinema.service.cinemaAdapter.CinemaTopAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    val loader=LoaderFragment()
    private lateinit var binding: FragmentHomePageBinding
    val adapterCinema1 = CinemaAdapter({type-> onClickAllFilm(type) }, { id -> onClickInfoFilm(id) })
    val adapterCinema2 = CinemaAdapter({type->  onClickAllFilm(type) }, { id -> onClickInfoFilm(id) })
    val adapterCinema3 = CinemaAdapter({ type-> onClickAllFilm(type) }, { id -> onClickInfoFilm(id) })
    val adapterCinema4 = CinemaAdapter({ type-> onClickAllFilm(type) }, { id -> onClickInfoFilm(id) })
    val adapterCinemaTop = CinemaTopAdapter({type->  onClickAllFilm(type) }, { id -> onClickInfoFilm(id) })
    val adapterCinemaTop1 = CinemaTopAdapter({ type-> onClickAllFilm(type) }, { id -> onClickInfoFilm(id) })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loader.show(childFragmentManager,"loader")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater)
        if (flagGetFilm){
            viewModel.getCinema()
            flagGetFilm=false
        }
        viewModel.getWatchesFilm()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { state ->
                when (state) {
                    is HomePageState.Loading -> {
                    }
                    is HomePageState.Success -> {
                        updateListsFilm(state)
                        loader.dismiss()
                    }
                    is HomePageState.Error -> {
                        loader.dismiss()
                        val error=ErrorBottomSheet(state.error)
                        error.show(childFragmentManager,"error")
                    }
                }
            }
        }

        viewModel.watchsFilm.onEach {
            Log.d("WatchFilmPageHome", "onCreateView: ${it}")
            adapterCinema1.updateWatchFilms(it)
            adapterCinema2.updateWatchFilms(it)
            adapterCinema3.updateWatchFilms(it)
            adapterCinema4.updateWatchFilms(it)
            adapterCinemaTop.updateWatchFilms(it)
            adapterCinemaTop1.updateWatchFilms(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)



        return binding.root
    }

    fun updateListsFilm(state: HomePageState.Success) {

        binding.cinemaList1.updateAdapterCustom(adapterCinema1)
        binding.cinemaList1.updateSizeCustom2(state.genreCinema1!!.second.items.size)
        binding.cinemaList1.updategenre(state.genreCinema1!!.first)
        adapterCinema1.submitList(state.genreCinema1!!.second.items)
        if (state.genreCinema1!!.second.items.size > 20) {
            adapterCinema1.submitList(state.genreCinema1!!.second.items.subList(0, 20))
        } else {
            adapterCinema1.submitList(state.genreCinema1!!.second.items)
        }
        adapterCinema1.typeFilmList=state.genreCinema1!!.first
        binding.cinemaList1.setClickAllFilm { typeList -> onClickAllFilm(typeList) }


        binding.cinemaList2.updateAdapterCustom(adapterCinema2)
        binding.cinemaList2.updateSizeCustom2(state.genreCinema2!!.second.items.size)
        binding.cinemaList2.updategenre(state.genreCinema2!!.first)
        if (state.genreCinema2!!.second.items.size > 20) {
            adapterCinema2.submitList(state.genreCinema2!!.second.items.subList(0, 20))
        } else {
            adapterCinema2.submitList(state.genreCinema2!!.second.items)
        }
        adapterCinema2.typeFilmList=state.genreCinema2!!.first
        binding.cinemaList2.setClickAllFilm { typeList -> onClickAllFilm(typeList) }




        binding.cinemaList3.updateAdapterCustom(adapterCinema3)
        binding.cinemaList3.updateSizeCustom2(state.premiers!!.second.items.size)
        binding.cinemaList3.updategenre(state.premiers.first)
        if (state.premiers.second.items.size > 20) {
            adapterCinema3.submitList(state.premiers.second.items.subList(0, 20))
        } else {
            adapterCinema3.submitList(state.premiers.second.items)
        }
        adapterCinema3.typeFilmList=state.premiers.first
        binding.cinemaList3.setClickAllFilm { typeList -> onClickAllFilm(typeList) }




        binding.cinemaList4.updategenre(state.popular!!.first)
        binding.cinemaList4.updateAdapterCustom(adapterCinemaTop)
        binding.cinemaList4.updateSizeCustom2(state.popular.second.films.size)
        adapterCinemaTop.submitList(state.popular.second.films)
        if (state.popular.second.films.size > 20) {
            adapterCinemaTop.submitList(state.popular.second.films.subList(0, 20))
        } else {
            adapterCinemaTop.submitList(state.popular.second.films)
        }
        adapterCinemaTop.typeFilmList=state.popular.first
        binding.cinemaList4.setClickAllFilm { typeList -> onClickAllFilm(typeList) }


        binding.cinemaList5.updategenre(state.popular1!!.first)
        binding.cinemaList5.updateAdapterCustom(adapterCinemaTop1)
        binding.cinemaList5.updateSizeCustom2(state.popular1.second.films.size)
        if (state.popular1.second.films.size > 20) {
            adapterCinemaTop1.submitList(state.popular1.second.films.subList(0, 20))
        } else {
            adapterCinemaTop1.submitList(state.popular1.second.films)
        }
        adapterCinemaTop1.typeFilmList=state.popular1.first
        binding.cinemaList5.setClickAllFilm { typeList -> onClickAllFilm(typeList) }




        binding.cinemaList6.updateAdapterCustom(adapterCinema4)
        binding.cinemaList6.updateSizeCustom2(state.serial!!.second.items.size)
        binding.cinemaList6.updategenre(state.serial.first)
        if (state.serial.second.items.size > 20) {
            adapterCinema4.submitList(state.serial.second.items.subList(0, 20))
        } else {
            adapterCinema4.submitList(state.serial.second.items)
        }
        adapterCinema4.typeFilmList=state.serial.first
        binding.cinemaList6.setClickAllFilm { typeList -> onClickAllFilm(typeList) }

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