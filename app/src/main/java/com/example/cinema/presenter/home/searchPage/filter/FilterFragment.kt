package com.example.cinema.presenter.home.searchPage.filter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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

        //получаем результат из фрагмента выбора страны
        setFragmentResultListener(Constance.CountryFilter) { requestKey, bundle ->

            val country = bundle.getString(Constance.CountryFilter)
            val id = bundle.getInt(Constance.CountryFilterId)
            binding.countryFilter.text=country
        }
        return binding.root
    }

}