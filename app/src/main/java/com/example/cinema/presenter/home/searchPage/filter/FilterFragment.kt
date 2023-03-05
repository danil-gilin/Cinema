package com.example.cinema.presenter.home.searchPage.filter

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
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentFilterBinding
import com.example.cinema.entity.Constance
import dagger.hilt.android.AndroidEntryPoint
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFilterBinding.inflate(inflater)
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
            //"с 1998 до 2017" взять года
            val pattern = Regex("\\d+")
            val years = pattern.findAll(binding.yearFilter.text.toString()).map { it.value.toInt() }.toList()
            bundle.putString(Constance.YearFilterFrom,years[0].toString())
            bundle.putString(Constance.YearFilterTo,years[1].toString())
            findNavController().navigate(R.id.action_filterFragment_to_yearFragment,bundle)
        }



        //получаем результат из фрагмента выбора страны
        setFragmentResultListener(Constance.CountryFilter) { requestKey, bundle ->
            val country = bundle.getString(Constance.CountryFilter)
            val idCountry = bundle.getInt(Constance.CountryFilterId)
            Log.d("FilterLog","genre $idCountry")
            binding.countryFilter.text=country
        }

        //получаем результат из фрагмента выбора жанра
        setFragmentResultListener(Constance.GenreFilter) { requestKey, bundle ->
            val genre= bundle.getString(Constance.GenreFilter)
            val idGenre = bundle.getInt(Constance.GenreFilterId)
            Log.d("FilterLog","genre $idGenre")
            binding.genreFilter.text=genre
        }

        //получаем результат из фрагмента выбора года
        setFragmentResultListener(Constance.YearFilter) { requestKey, bundle ->
            val yearFrom= bundle.getString(Constance.YearFilterFrom)
            val yearTo= bundle.getString(Constance.YearFilterTo)
            Log.d("trySelectYear", "yearFrom $yearFrom yearTo $yearTo")
            binding.yearFilter.text="с $yearFrom до $yearTo"
        }

        return binding.root
    }

}