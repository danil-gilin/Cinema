package com.example.cinema.presenter.home.homepage.filmInfo.actorInfo.actorFulInfo

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
import com.example.cinema.customView.ErrorBottomSheet
import com.example.cinema.databinding.FragmentActorInfoBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.fullInfoActor.typeListFilm.TypeListFilm
import com.example.cinema.service.adapterFilmActor.AdapterFilmActor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ActorInfoFragment : Fragment() {

    companion object {
        fun newInstance() = ActorInfoFragment()
    }

    @Inject
    lateinit var factory: ActorInfoFactory

    private val viewModel: ActorInfoViewModel by viewModels{factory}
    lateinit var binding:FragmentActorInfoBinding
    private val adapter=AdapterFilmActor(false,{it->onClickFilm(it)},{onClickAll()})



    private  var idActor=0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentActorInfoBinding.inflate(inflater,container,false)
        arguments?.let {
            if(idActor==0){
                idActor=it.getInt(Constance.ACTOR_ID_FOR_FULL_INFO)
                viewModel.getActorInfo(idActor)
            }
        }
        binding.backBtnFullInfoActor.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getWatchesFilm()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.stateActorInfo.collect{
                when(it){
                    is ActorInfoState.Loading ->{
                        binding.progressLoad7.visibility=View.VISIBLE
                    }
                    is ActorInfoState.Success ->{
                        binding.progressLoad7.visibility=View.GONE
                        Log.d("ActorInfoState","good")
                        Glide.with(binding.imgFullInfoActor).load(it.actorInfo.posterUrl).centerCrop().into(binding.imgFullInfoActor)
                        if (it.actorInfo.nameRu !=null){
                            binding.txtNameFullInfoActor.text=it.actorInfo.nameRu
                        }else{
                            binding.txtNameFullInfoActor.text=it.actorInfo.nameEn
                        }
                        binding.txtProffessionFullInfoActor.text=it.actorInfo.profession
                        binding.listCinmaActor.updateNameList("Лучшее")
                        binding.listCinmaActor.allClickEmpty{
                            val bundle=Bundle()
                            var typeFilms: TypeListFilm
                            if (it.actorInfo.nameRu==null)
                                typeFilms=
                                    TypeListFilm(name = "${it.actorInfo.nameEn} Лучшее", actorId = idActor)
                            else
                               typeFilms=
                                   TypeListFilm(name = "${it.actorInfo.nameRu} Лучшее", actorId = idActor)
                            bundle.putParcelable(Constance.ACTOR_LIST_FILM_FOR_ALL_FILM,typeFilms)
                            findNavController().navigate(R.id.action_actorInfoFragment_to_allFilmFragment,bundle)
                        }
                        adapter.updateWatchFilms(it.watchesFilms)
                        adapter.submitList(it.Films)
                        binding.listCinmaActor.updateFilmActor(adapter)

                        binding.filmhistoryInfo.text=it.infoHistoryFilms

                    }
                    is ActorInfoState.Error ->{
                        binding.progressLoad7.visibility=View.GONE
                        val errorDialog=ErrorBottomSheet(it.error)
                        errorDialog.show(childFragmentManager,"error")
                    }
                }
            }
        }

        binding.filmographyBtn.setOnClickListener {
            val bundle=Bundle()
            bundle.putInt(Constance.ACTOR_ID_FOR_FILM_HISTORY,idActor)
            findNavController().navigate(R.id.action_actorInfoFragment_to_actorFilmHistoryFragment,bundle)
        }

        viewModel.watchsFilm.onEach {
            adapter.updateWatchFilms(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }

    private fun onClickFilm(it: Int) {
        val bundle=Bundle()
        bundle.putInt(Constance.FILM_FILM_INFO_ID,it)
        findNavController().navigate(R.id.action_actorInfoFragment_to_filmInfoFragment,bundle)
    }
    private fun onClickAll() {
        val bundle=Bundle()
        var typeFilms= TypeListFilm(name = "${binding.txtNameFullInfoActor.text} Лучшее", actorId = idActor)
        bundle.putParcelable(Constance.ACTOR_LIST_FILM_FOR_ALL_FILM,typeFilms)
        findNavController().navigate(R.id.action_actorInfoFragment_to_allFilmFragment,bundle)
    }
}