package com.example.cinema.presenter.home.searchPage.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSearchBinding
import com.example.cinema.entity.Constance
import com.example.cinema.service.adapterSearchFilms.AdapterSearchFilms
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    private val adapter=AdapterSearchFilms(){it->clickFilm(it)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(inflater)
        binding.recyclerSearch.adapter=adapter

       viewModel.getWatchesFilm()

        binding.imgFilter.setOnClickListener {
            binding.linearFilter.isPressed = true
            binding.linearFilter.postDelayed({ binding.linearFilter.isPressed = false }, 100)
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }

       binding.searchTxt.addTextChangedListener {
            viewModel.getSearchListPagging(it.toString())
            viewModel.listSearchPaggin?.onEach {film->
                adapter.submitData(film)
            }?.launchIn(viewLifecycleOwner.lifecycleScope)
        }

        viewModel.listSearchPaggin.onEach {film->
            adapter.submitData(film)
        }?.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.listwatchesFilm.onEach {
            adapter.watchFilm=it
            adapter.notifyDataSetChanged()
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }

    private fun clickFilm(id:Int){
        val bundle=Bundle()
        bundle.putInt(Constance.FILM_FILM_INFO_ID,id)
    }

}