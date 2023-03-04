package com.example.cinema.presenter.home.searchPage.year

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentYearBinding
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
    private val adapterYear= AdapterYear()
    private val year="2023"
    private var listYear= arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentYearBinding.inflate(inflater)
        binding.backYear.setOnClickListener {
            findNavController().popBackStack()
        }






        return binding.root
    }

}