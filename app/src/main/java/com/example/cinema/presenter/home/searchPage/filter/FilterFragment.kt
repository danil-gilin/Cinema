package com.example.cinema.presenter.home.searchPage.filter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilterFragment : Fragment() {

    companion object {
        fun newInstance() = FilterFragment()
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


        return binding.root
    }

}