package com.example.cinema.presenter.home.homepage.filmInfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
                        Glide.with(binding.filmFullImg).load(state.film.posterUrl).centerCrop()
                            .into(binding.filmFullImg)
                        if (state.film.logoUrl != null) {
                            Glide.with(binding.posterImg).load(state.film.logoUrl).centerInside()
                                .into(binding.posterImg)
                            binding.posterImg.visibility = View.VISIBLE
                        }
                        if (state.film.nameRu != null) {
                            binding.shortInfo1.text =
                                "${state.film.ratingKinopoisk} ${state.film.nameRu}"
                        } else if (state.film.nameEn != null) {
                            binding.shortInfo1.text =
                                "${state.film.ratingKinopoisk} ${state.film.nameEn}"
                        } else {
                            binding.shortInfo1.text =
                                "${state.film.ratingKinopoisk} ${state.film.nameOriginal}"
                        }
                        binding.shortInfo2.text =
                            "${state.film.year}, ${state.film.genres?.get(0)?.genre}"
                        //only number in string
                        var filmLength = ""
                        if (state.film.filmLength != null) {
                            val hour = state.film.filmLength!! / 60
                            val minute = state.film.filmLength % 60
                            if (hour != 0) {
                                filmLength = "${hour}ч ${minute}мин"
                            } else {
                                filmLength = "${minute}мин"
                            }
                            val age = state.film.ratingAgeLimits?.filter { it.isDigit() }
                            binding.shortInfo3.text =
                                "${state.film.countries?.get(0)?.country}, ${filmLength}, ${age}+"
                        } else {
                            val age = state.film.ratingAgeLimits?.filter { it.isDigit() }
                            binding.shortInfo3.text =
                                "${state.film.countries?.get(0)?.country}, ${age}+"
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