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
    private lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater)
        viewModel.getCinema()


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { state ->
                when (state) {
                    is HomePageState.Loading -> {

                    }
                    is HomePageState.Success -> {

                    }
                    is HomePageState.Error -> {

                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.cinemaGenreChannel1.collect {
                Log.d("MyCinema", "onCreateView: $it")
                binding.cinemaList1.updateListCinema(it.second.items)
                binding.cinemaList1.updategenre(it.first)
                binding.cinemaList1.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.cinemaGenreChannel2.collect {
                binding.cinemaList2.updateListCinema(it.second.items)
                binding.cinemaList2.updategenre(it.first)
                binding.cinemaList2.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.cinemaPremiers.collect {
                binding.cinemaList3.updateListCinema(it.second.items)
                binding.cinemaList3.updategenre(it.first)
                binding.cinemaList3.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.cinemaPopularFilm1.collect {
                binding.cinemaList4.updateListCinemaTop(it.second.films)
                binding.cinemaList4.updategenre(it.first)
                binding.cinemaList4.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.cinemaPopularFilm2.collect {
                binding.cinemaList5.updateListCinemaTop(it.second.films)
                binding.cinemaList5.updategenre(it.first)
                binding.cinemaList5.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.cinemaSerial.collect {
                binding.cinemaList6.updateListCinema(it.second.items)
                binding.cinemaList6.updategenre(it.first)
                binding.cinemaList6.setClickAllFilm { typeList -> onClickAllFilm(typeList) }
            }
        }
        return binding.root
    }

    fun onClickAllFilm(typeListFilm: TypeListFilm) {
        val bundle = Bundle()
        bundle.putParcelable(Constance.NAME_GENRE_ALL_FILM, typeListFilm)
        findNavController().navigate(R.id.action_homePageFragment_to_allFilmFragment, bundle)
    }
}