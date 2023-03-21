package com.example.cinema.presenter.home.homepage.bottomSheetFilm

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.databinding.FragmentAddCollectionBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.dbCinema.FilmDBLocal
import com.example.cinema.presenter.home.homepage.bottomSheetFilm.newCollection.NewCollectionFragment
import com.example.cinema.presenter.home.homepage.bottomSheetFilm.newCollection.StateAddCollection
import com.example.cinema.service.collectionAdapter.AdapterCollection
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class AddCollectionFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = AddCollectionFragment()
    }

    @Inject
    lateinit var viewModelFactory: AddCollectionFactory

    private val viewModel: AddCollectionViewModel by viewModels { viewModelFactory }
    lateinit var binding: FragmentAddCollectionBinding
    lateinit var film:FilmDBLocal
    private val adapter= AdapterCollection()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCollectionBinding.inflate(inflater)
        binding.rcCollection.adapter=adapter
        viewModel.getCollection()

        arguments?.let {
            film=it.get(Constance.FILM_BOTTOM_SHEET) as FilmDBLocal
            viewModel.getSelectCollection(film.id)
            init()
        }

        viewModel.collection.onEach { collection->
            adapter.submitList(collection)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.collectionSelect.onEach { collectionSelect->
            adapter.selectCollection.addAll(collectionSelect)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        binding.btnClose.setOnClickListener {
            viewModel.addFilmToCollection(film,adapter.selectCollection)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect{it->
                when(it){
                    is StateAddCollection.Error ->
                    {

                    }
                    StateAddCollection.Loading -> {

                    }
                    StateAddCollection.Success -> {
                        dismiss()
                    }
                }
            }
        }


        binding.addCollectionLinear.setOnClickListener {
            val newCollectionFragment=NewCollectionFragment()
            newCollectionFragment.show(parentFragmentManager,"newCollectionFragment")

        }

        return binding.root
    }


    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    private fun init(){
        binding.cinemaGenreFilmCollection.text="${film.year}, ${film.genre}"
        binding.cinemaNameTxtFilmCollection.text=film.nameFilm
        if (film.rating!=null){
            binding.cinemaRatingFilmCollection.text=film.rating.toString()
            binding.cinemaRatingFilmCollection.visibility=View.VISIBLE
        }else{
            binding.cinemaRatingFilmCollection.visibility=View.GONE
        }
        Glide.with(binding.root).load(film.img).centerCrop().into(binding.cinemaImgFilmCollection)
        if(film.watch){
            binding.gradientImgFilmCollection.visibility=View.VISIBLE
            binding.cinemaSeenFilmCollection.visibility=View.VISIBLE
        }else {
            binding.gradientImgFilmCollection.visibility=View.GONE
            binding.cinemaSeenFilmCollection.visibility=View.GONE
        }

    }

    override fun onResume() {
        viewModel.getCollection()
        super.onResume()
    }

    override fun onDestroy() {
        viewModel.addFilmToCollection(film,adapter.selectCollection)
        super.onDestroy()
    }
}