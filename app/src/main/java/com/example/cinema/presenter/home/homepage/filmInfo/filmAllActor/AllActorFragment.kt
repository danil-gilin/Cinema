package com.example.cinema.presenter.home.homepage.filmInfo.filmAllActor

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
import com.example.cinema.databinding.FragmentAllActorBinding
import com.example.cinema.entity.Constance
import com.example.cinema.service.adapterActorWorkerAll.AdapterActorWorkerAll
import com.example.cinema.service.adapterForFullFilmInfo.ActorAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class AllActorFragment : Fragment() {

    companion object {
        fun newInstance() = AllActorFragment()
    }

    @Inject
    lateinit var factory: AllActorFactory

    private val adapterActor=AdapterActorWorkerAll(){it->onClickActor(it)}

    private val viewModel: AllActorViewModel by viewModels{factory}
    lateinit var binding: FragmentAllActorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAllActorBinding.inflate(inflater)

        binding.rcAllActor.adapter= adapterActor
        binding.rcAllActor.layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)

        arguments.let {
            val idFilm=it?.getInt(Constance.FILM_ALL_ACTOR_ID)
            val type=it?.getString(Constance.FILM_ALL_ACTOR_TYPE,"")
            if (type=="ACTOR") {
                binding.genreNameAllActor.text = "Все актеры"
            }else {binding.genreNameAllActor.text="Над фильмом работали"}
            viewModel.getActors(idFilm!!,type!!)
        }

        viewModel.listActor.onEach {
            adapterActor.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }


    private fun onClickActor(it: Int) {
        val bundle=Bundle()
        bundle.putInt(Constance.ACTOR_ID_FOR_FULL_INFO,it)
        findNavController().navigate(R.id.action_allActorFragment_to_actorInfoFragment,bundle)
    }

}