package com.example.cinema.presenter.home.searchPage.genre

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentCountryBinding
import com.example.cinema.databinding.FragmentGenreBinding
import com.example.cinema.entity.Constance
import com.example.cinema.service.adapter_filter.AdapterGenre
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class GenreFragment : Fragment() {

    companion object {
        fun newInstance() = GenreFragment()
    }

    @Inject
    lateinit var factory :GenreFactory

    private val viewModel: GenreViewModel by viewModels { factory }
    lateinit var binding:FragmentGenreBinding
    private lateinit var selectGenre:String
    val adapter=AdapterGenre(){ id, name -> selectGenre(id, name) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentGenreBinding.inflate(inflater)


        arguments?.let {
            selectGenre = it.getString(Constance.GenreFilter,"").toString()
            viewModel.getGenreList(selectGenre)
            adapter.selectGenre=selectGenre
        }
        binding = FragmentGenreBinding.inflate(inflater)
        binding.rcGenre.adapter = adapter


        viewModel.listGenreChannel.onEach {
            Log.d("GenreList",it.toString())
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.searchTxtGenre.addTextChangedListener {
            viewModel.getGenreListSearch(it.toString())
        }

        binding.backGenre.setOnClickListener {
            findNavController().popBackStack()
        }


        return binding.root
    }

    private fun selectGenre(id:Int,name:String){
        //popBackStack with result
        val bundle=Bundle()
        bundle.putInt(Constance.GenreFilterId,id)
        bundle.putString(Constance.GenreFilter,name)
        setFragmentResult(Constance.GenreFilter,bundle)
        findNavController().popBackStack()
    }
}