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
        val Country="Россия"
    }

    @Inject
    lateinit var factory :FilterFactory

    private val viewModel: FilterViewModel by viewModels {factory}
    lateinit var binding:FragmentFilterBinding
    var watch=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFilterBinding.inflate(inflater)
        viewModel.getFilter()




        binding.backFilter.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.countryFilterLinear.setOnClickListener {
            val bundle=Bundle()
            bundle.putString(Constance.CountryFilter,binding.countryFilter.text.toString())
            findNavController().navigate(R.id.action_filterFragment_to_countryFragment,bundle)
        }

        binding.genreFilterLinear.setOnClickListener {
            val bundle=Bundle()
            bundle.putString(Constance.GenreFilter,binding.genreFilter.text.toString())
            findNavController().navigate(R.id.action_filterFragment_to_genreFragment,bundle)
        }
        binding.yearFilterLinear.setOnClickListener {
            val bundle=Bundle()
            val pattern = Regex("\\d+")
            val years = pattern.findAll(binding.yearFilter.text.toString()).map { it.value.toInt() }.toList()
            bundle.putString(Constance.YearFilterFrom,years[0].toString())
            bundle.putString(Constance.YearFilterTo,years[1].toString())
            findNavController().navigate(R.id.action_filterFragment_to_yearFragment,bundle)
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
            viewModel.setFilter(TypeFilter.WATCH,watch)
        }

        //получаем результат из фрагмента выбора страны
        setFragmentResultListener(Constance.CountryFilter) { requestKey, bundle ->
            val country = bundle.getString(Constance.CountryFilter)
            val idCountry = bundle.getInt(Constance.CountryFilterId)
            viewModel.setFilter(TypeFilter.COUNTRY,country ?: "0")
            binding.countryFilter.text=country
        }

        //получаем результат из фрагмента выбора жанра
        setFragmentResultListener(Constance.GenreFilter) { requestKey, bundle ->
            val genre= bundle.getString(Constance.GenreFilter)
            val idGenre = bundle.getInt(Constance.GenreFilterId)
            viewModel.setFilter(TypeFilter.GENRE,genre ?: "0")
            binding.genreFilter.text=genre
        }

        //получаем результат из фрагмента выбора года
        setFragmentResultListener(Constance.YearFilter) { requestKey, bundle ->
            val yearFrom= bundle.getString(Constance.YearFilterFrom)
            val yearTo= bundle.getString(Constance.YearFilterTo)
            viewModel.setFilter(TypeFilter.YEARFROM,yearFrom ?: "0")
            viewModel.setFilter(TypeFilter.YEARTO,yearTo ?: "0")
            binding.yearFilter.text="с $yearFrom до $yearTo"
        }

        binding.radioGroupTypeFilm.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.filter_film_all->{
                    viewModel.setFilter(TypeFilter.TYPE,TypeFilmFilter.ALL)
                }
                R.id.filter_film_film->{
                    viewModel.setFilter(TypeFilter.TYPE,TypeFilmFilter.FILM)
                }
                R.id.filter_film_serial->{
                    viewModel.setFilter(TypeFilter.TYPE,TypeFilmFilter.TV_SERIES)
                }
            }
        }

        binding.sortListByGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.sort_list_by_rating->{
                    viewModel.setFilter(TypeFilter.SORT,SortFilter.RATING)
                }
                R.id.sort_list_by_date->{
                    viewModel.setFilter(TypeFilter.SORT,SortFilter.YEAR)
                }
                R.id.sort_list_by_popular->{
                    viewModel.setFilter(TypeFilter.SORT,SortFilter.NUM_VOTE)
                }
            }
        }

        binding.raitingSlider.addOnChangeListener { slider, value, fromUser ->
            viewModel.setFilter(TypeFilter.RATINGFROM,slider.values[0].toInt())
            viewModel.setFilter(TypeFilter.RATINGTO,slider.values[1].toInt())
        }



        viewModel.filterChannel.onEach {
            binding.countryFilter.text=it.country
            binding.genreFilter.text=it.genre
            binding.yearFilter.text="с ${it.yearFrom} до ${it.yearTo}"
            when(it.type){
                TypeFilmFilter.ALL->{
                    binding.filterFilmAll.isChecked=true
                }
                TypeFilmFilter.FILM->{
                    binding.filterFilmFilm.isChecked=true
                }
                TypeFilmFilter.TV_SERIES->{
                    binding.filterFilmSerial.isChecked=true
                }
            }
            when(it.sort){
                SortFilter.RATING->{
                    binding.sortListByRating.isChecked=true
                }
                SortFilter.YEAR->{
                    binding.sortListByDate.isChecked=true
                }
                SortFilter.NUM_VOTE->{
                    binding.sortListByPopular.isChecked=true
                }
            }
            if (it.watch){
                binding.imgFilterWatch.setImageResource(R.drawable.icon_watch_film_info)
                binding.textFilterWatch.text = "Просмотрен"
                watch = true
            }else{
                binding.imgFilterWatch.setImageResource(R.drawable.icon_unwatch_filter)
                binding.textFilterWatch.text = "Не просмотрен"
                watch = false
            }

            binding.raitingSlider.values= listOf(it.ratingFrom.toFloat(),it.ratingTo.toFloat())

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }

}