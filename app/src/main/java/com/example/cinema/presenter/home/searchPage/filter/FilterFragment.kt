package com.example.cinema.presenter.home.searchPage.filter

import android.os.Build.SERIAL
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentFilterBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.filterEntity.SortFilter
import com.example.cinema.entity.filterEntity.TypeFilmFilter
import com.example.cinema.entity.filterEntity.TypeFilter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class FilterFragment : Fragment() {

    companion object {
        fun newInstance() = FilterFragment()
        val Country = "Россия"
    }

    @Inject
    lateinit var factory: FilterFactory

    private val viewModel: FilterViewModel by viewModels { factory }
    lateinit var binding: FragmentFilterBinding
    var watch = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater)
        viewModel.getFilter()




        binding.backFilter.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.countryFilterLinear.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Constance.CountryFilter, binding.countryFilter.text.toString())
            findNavController().navigate(R.id.action_filterFragment_to_countryFragment, bundle)
        }

        binding.genreFilterLinear.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Constance.GenreFilter, binding.genreFilter.text.toString())
            findNavController().navigate(R.id.action_filterFragment_to_genreFragment, bundle)
        }
        binding.yearFilterLinear.setOnClickListener {
            val bundle = Bundle()
            val pattern = Regex("\\d+")
            val years = pattern.findAll(binding.yearFilter.text.toString()).map { it.value.toInt() }.toList()
                if (years.size == 2) {
                    bundle.putString(Constance.YearFilterFrom, years[0].toString())
                    bundle.putString(Constance.YearFilterTo, years[1].toString())
                }else{
                    if (binding.yearFilter.text.toString().contains("с")) {
                        bundle.putString(Constance.YearFilterFrom, years[0].toString())
                        bundle.putString(Constance.YearFilterTo, "")
                    } else if (binding.yearFilter.text.toString().contains("до")) {
                        bundle.putString(Constance.YearFilterFrom, "")
                        bundle.putString(Constance.YearFilterTo, years[0].toString())
                    } else {
                        bundle.putString(Constance.YearFilterFrom, "")
                        bundle.putString(Constance.YearFilterTo, "")
                    }
                }
            findNavController().navigate(R.id.action_filterFragment_to_yearFragment, bundle)
        }

        //смена посмотренные фильмы добавлять ил нет
        binding.linearFilterWatch.setOnClickListener {
            if (watch) {
                binding.imgFilterWatch.setImageResource(R.drawable.icon_unwatch_filter)
                binding.textFilterWatch.text = "Не просмотрен"
                watch = false
            } else {
                binding.imgFilterWatch.setImageResource(R.drawable.icon_watch_film_info)
                binding.textFilterWatch.text = "Просмотрен"
                watch = true
            }
            viewModel.setFilter(TypeFilter.WATCH, watch)
        }

        //получаем результат из фрагмента выбора страны
        setFragmentResultListener(Constance.CountryFilter) { requestKey, bundle ->
            val country = bundle.getString(Constance.CountryFilter)
            viewModel.setFilter(TypeFilter.COUNTRY, country ?: "")
            if (country == "") {
                binding.countryFilter.text = "любая"
            } else {
                binding.countryFilter.text = country
            }
        }

        //получаем результат из фрагмента выбора жанра
        setFragmentResultListener(Constance.GenreFilter) { requestKey, bundle ->
            val genre = bundle.getString(Constance.GenreFilter)
            viewModel.setFilter(TypeFilter.GENRE, genre ?: "")
            if (genre == "") {
                binding.genreFilter.text = "любой"
            } else {
                binding.genreFilter.text = genre
            }
        }

        //получаем результат из фрагмента выбора года
        setFragmentResultListener(Constance.YearFilter) { requestKey, bundle ->
            val yearFrom = bundle.getString(Constance.YearFilterFrom)
            val yearTo = bundle.getString(Constance.YearFilterTo)
            viewModel.setFilter(TypeFilter.YEARFROM, yearFrom ?: "")
            viewModel.setFilter(TypeFilter.YEARTO, yearTo ?: "")
            if (yearFrom == "" && yearTo == "") {
                binding.yearFilter.text = "любой"
            } else if (yearFrom == "") {
                binding.yearFilter.text = "до $yearTo"
            } else if (yearTo == "") {
                binding.yearFilter.text = "с $yearFrom"
            } else binding.yearFilter.text = "с $yearFrom до $yearTo"
        }

        binding.radioGroupTypeFilm.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.filter_film_all -> {
                    viewModel.setFilter(TypeFilter.TYPE, TypeFilmFilter.ALL)
                }
                R.id.filter_film_film -> {
                    viewModel.setFilter(TypeFilter.TYPE, TypeFilmFilter.FILM)
                }
                R.id.filter_film_serial -> {
                    viewModel.setFilter(TypeFilter.TYPE, TypeFilmFilter.TV_SERIES)
                }
            }
        }

        binding.sortListByGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.sort_list_by_rating -> {
                    viewModel.setFilter(TypeFilter.SORT, SortFilter.RATING)
                }
                R.id.sort_list_by_date -> {
                    viewModel.setFilter(TypeFilter.SORT, SortFilter.YEAR)
                }
                R.id.sort_list_by_popular -> {
                    viewModel.setFilter(TypeFilter.SORT, SortFilter.NUM_VOTE)
                }
            }
        }

        binding.raitingSlider.addOnChangeListener { slider, value, fromUser ->
            viewModel.setFilter(TypeFilter.RATINGFROM, Pair(slider.values[0].toInt(), slider.values[1].toInt()))
            binding.raitingFilter.text =
                if (slider.values[0].toInt() == 0 && slider.values[1].toInt() == 10)
                    "любой"
                else if(slider.values[0].toInt() == slider.values[1].toInt())
                    "${slider.values[0].toInt()}"
                else if (slider.values[0].toInt() == 0)
                    "до ${slider.values[1].toInt()}"
                else if (slider.values[1].toInt() == 10)
                    "от ${slider.values[0].toInt()}"
                else "от ${slider.values[0].toInt()} до ${slider.values[1].toInt()}"
        }



        viewModel.filterChannel.onEach {
            if (it.country == "") {
                binding.countryFilter.text = "любая"
            } else {
                binding.countryFilter.text = it.country
            }
            if (it.genre == "") {
                binding.genreFilter.text = "любой"
            } else {
                binding.genreFilter.text = it.genre
            }

            binding.yearFilter.text = if (it.yearFrom == "" && it.yearTo == "")
               "любой"
             else if (it.yearFrom == "")
               "до ${it.yearTo}"
             else if (it.yearTo == "")
               "с ${it.yearFrom}"
            else
               "с ${it.yearFrom} до ${it.yearTo}"

            when (it.type) {
                TypeFilmFilter.ALL -> {
                    binding.filterFilmAll.isChecked = true
                }
                TypeFilmFilter.FILM -> {
                    binding.filterFilmFilm.isChecked = true
                }
                TypeFilmFilter.TV_SERIES -> {
                    binding.filterFilmSerial.isChecked = true
                }
            }
            when (it.sort) {
                SortFilter.RATING -> {
                    binding.sortListByRating.isChecked = true
                }
                SortFilter.YEAR -> {
                    binding.sortListByDate.isChecked = true
                }
                SortFilter.NUM_VOTE -> {
                    binding.sortListByPopular.isChecked = true
                }
            }
            if (it.watch) {
                binding.imgFilterWatch.setImageResource(R.drawable.icon_watch_film_info)
                binding.textFilterWatch.text = "Просмотрен"
                watch = true
            } else {
                binding.imgFilterWatch.setImageResource(R.drawable.icon_unwatch_filter)
                binding.textFilterWatch.text = "Не просмотрен"
                watch = false
            }

            binding.raitingSlider.values = listOf(it.ratingFrom.toFloat(), it.ratingTo.toFloat())

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }

}