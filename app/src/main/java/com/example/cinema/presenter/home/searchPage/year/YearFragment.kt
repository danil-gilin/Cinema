package com.example.cinema.presenter.home.searchPage.year

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentYearBinding
import com.example.cinema.entity.Constance
import com.example.cinema.service.adapter_filter.AdapterYear
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class YearFragment : Fragment() {

    companion object {
        fun newInstance() = YearFragment()
    }

    @Inject
    lateinit var factory: YearFactory

    private val viewModel: YearViewModel by viewModels { factory }
    private lateinit var binding: FragmentYearBinding
    private val year="2023"
    private var listYear= arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentYearBinding.inflate(inflater)
        arguments?.let {
            binding.yearFrom.setYear(it.getString(Constance.YearFilterFrom,""))
            binding.yearTo.setYear(it.getString(Constance.YearFilterTo,""))
            binding.yearFrom.setYearFrom(it.getString(Constance.YearFilterTo,""))
            binding.yearTo.setYearTo(it.getString(Constance.YearFilterFrom,""))
        }
        binding.backYear.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.yearTo.setClickListener {
            binding.yearFrom.setYearFrom(it)
        }

        binding.yearFrom.setClickListener {
            binding.yearTo.setYearTo(it)
        }

        binding.selectYears.setOnClickListener {
            val bundle=Bundle()
            bundle.putString(Constance.YearFilterFrom,binding.yearFrom.getYear())
            bundle.putString(Constance.YearFilterTo,binding.yearTo.getYear())
            setFragmentResult(Constance.YearFilter,bundle)
            findNavController().popBackStack()
        }





        return binding.root
    }

}