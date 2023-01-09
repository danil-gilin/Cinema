package com.example.cinema.presenter.home.homepage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cinema.R
import com.example.cinema.databinding.FragmentHomePageBinding
import dagger.hilt.android.AndroidEntryPoint
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
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.cinemaGenreChannel2.collect {
                binding.cinemaList2.updateListCinema(it.second.items)
                binding.cinemaList2.updategenre(it.first)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.cinemaPremiers.collect {
                binding.cinemaList3.updateListCinema(it.second.items)
                binding.cinemaList3.updategenre(it.first)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.cinemaPopularFilm1.collect {
                binding.cinemaList4.updateListCinemaTop(it.second.films)
                binding.cinemaList4.updategenre(it.first)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.cinemaPopularFilm2.collect {
                binding.cinemaList5.updateListCinemaTop(it.second.films)
                binding.cinemaList5.updategenre(it.first)
            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.cinemaSerial.collect {
                binding.cinemaList6.updateListCinema(it.second.items)
                binding.cinemaList6.updategenre(it.first)
            }
        }



        return binding.root
    }
}