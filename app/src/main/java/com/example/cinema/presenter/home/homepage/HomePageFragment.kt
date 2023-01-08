package com.example.cinema.presenter.home.homepage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cinema.R
import com.example.cinema.databinding.FragmentHomePageBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    companion object {
        fun newInstance() = HomePageFragment()
    }

    @Inject
    lateinit var viewModelFactory: HomePageFactory

    private val viewModel: HomePageViewModel by viewModels{viewModelFactory}
    private lateinit var binding: FragmentHomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater)
        viewModel.getCinemaGenre()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { state ->
                when(state) {
                    is HomePageState.Loading -> {

                    }
                    is HomePageState.Success -> {
                        binding.cinemaList1.updategenre(state.genreName)
                       binding.cinemaList1.updateListCinema(state.cinemaList)
                    }
                    is HomePageState.Error -> {

                    }
                }
            }
        }


        return binding.root
    }
}