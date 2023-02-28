package com.example.cinema.presenter.home.searchPage.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    @Inject
    lateinit var factory:SearchFactory
    private val viewModel: SearchViewModel by viewModels { factory }
    lateinit var binding:FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(inflater)

        binding.searchTxtLayuot.setEndIconOnClickListener {

        }

        binding.imgFilter.setOnClickListener {
            binding.linearFilter.isPressed = true
            binding.linearFilter.postDelayed({ binding.linearFilter.isPressed = false }, 100)
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }


        return binding.root
    }

}