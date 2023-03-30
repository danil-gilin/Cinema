package com.example.cinema.presenter.home.profile.allFilmProfile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinema.R
import com.example.cinema.databinding.FragmentAllFilmProfileBinding
import com.example.cinema.entity.Constance
import com.example.cinema.service.adapterAllFilmProfile.AdapterAllFilmProfile
import com.example.cinema.service.adapterHistory.AdapterAllHistory
import com.example.cinema.service.adapterHistory.AdapterHistory
import com.example.cinema.service.adapterHistory.HistoryAllViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllFilmProfileFragment : Fragment() {

    companion object {
        fun newInstance() = AllFilmProfileFragment()
        val idNameList = arrayListOf<Pair<Int,String>>(-1 to "Любимые", -2 to "Хочу посмотреть", -3 to "Просмотрено", -4 to "История просмотра")
    }

    @Inject
    lateinit var factory: AllFilmProfileFactory

    private val viewModel: AllFilmProfileViewModel by viewModels { factory }
    lateinit var binding: FragmentAllFilmProfileBinding
    private val adapter = AdapterAllFilmProfile(){idFilm->onClickFilm(idFilm)}


    private val adapterHistory = AdapterAllHistory(){idFilm,filmFlag->onClickHistoryItem(idFilm,filmFlag)}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAllFilmProfileBinding.inflate(inflater)

        arguments.let {
            val collectionID = it?.getInt(Constance.CollectionId)
            if (collectionID != null) {
                viewModel.getCollection(collectionID)
            }
        }

        binding.rcAllFilmProfile.adapter=adapter
        binding.rcAllFilmProfile.layoutManager=GridLayoutManager(context, 2,GridLayoutManager.VERTICAL,false)


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { state->
                when(state){
                    is AllFilmProfileState.Success->{
                        adapter.submitList(state.list)
                        binding.genreNameAllFilmProfile.text=state.name
                        adapter.listIdWarchFilm=state.watchFilmId
                    }
                    is AllFilmProfileState.Error->{

                    }
                    is AllFilmProfileState.Loading->{

                    }
                    is AllFilmProfileState.Empty->{

                    }
                    is AllFilmProfileState.SuccessHistory->{
                        adapterHistory.submitList(state.list)
                        binding.rcAllFilmProfile.adapter=adapterHistory
                        binding.genreNameAllFilmProfile.text=state.name
                    }
                }
            }
        }

        binding.allFilmBackProfile.setOnClickListener {
           findNavController().popBackStack()
        }

        return binding.root
    }

    private fun onClickHistoryItem(idSelectItem: Int, filmFlag: Boolean) {
        if(filmFlag){
            val bundle=Bundle()
            bundle.putInt(Constance.FILM_FILM_INFO_ID,idSelectItem)
            findNavController().navigate(R.id.action_allFilmProfileFragment_to_filmInfoFragment,bundle)
        }else{
            val bundle=Bundle()
            bundle.putInt(Constance.ACTOR_ID_FOR_FULL_INFO,idSelectItem)
            findNavController().navigate(R.id.action_allFilmProfileFragment_to_actorInfoFragment,bundle)
        }
    }

    private fun onClickFilm(idFilm: Int) {
        val bundle=Bundle()
        bundle.putInt(Constance.FILM_FILM_INFO_ID,idFilm)
        findNavController().navigate(R.id.action_allFilmProfileFragment_to_filmInfoFragment,bundle)
    }
}