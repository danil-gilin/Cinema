package com.example.cinema.presenter.home.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinema.R
import com.example.cinema.databinding.FragmentProfileBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.dbCinema.CollectionFilms
import com.example.cinema.entity.dbCinema.HistoryCollectionDB
import com.example.cinema.entity.dbCinema.WatchFilm
import com.example.cinema.presenter.home.homepage.bottomSheetFilm.newCollection.NewCollectionFragment
import com.example.cinema.service.adapterHistory.AdapterHistory
import com.example.cinema.service.adapterWatch.AdapterWatchFilm
import com.example.cinema.service.collectionProfileAdapter.CollectionProfileAdapter

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
        val idNameList = arrayListOf(-1 to "Любимые", -2 to "Хочу посмотреть", -3 to "Просмотрено", -4 to "История просмотра")
        val listEmptyWatch= arrayListOf(WatchFilm(-1,"","","",1.0,false))
        val listEmptyHistory= arrayListOf(HistoryCollectionDB(-1,-1,"","","",1.0,false,-1))
    }

    @Inject
    lateinit var factory:ProfileFactory

    private val viewModel: ProfileViewModel by viewModels { factory }
    lateinit var binding: FragmentProfileBinding
    private val adapterHistory=AdapterHistory({idFilm,filmFlag->onClickHistoryItem(idFilm,filmFlag)},{deleteHistory()})
    private val adapterWatch=AdapterWatchFilm({idFilm->onClickWatchItem(idFilm)},{deleteWatch()})
    private val adapterCollection=CollectionProfileAdapter({it->deleteCollection(it)}, {id->selectColection(id)})



    private var listCollection= emptyList<CollectionFilms>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(inflater)
        init()

        viewModel.getProfile()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect{
                when(it){
                    ProfileState.Error ->{

                    }
                    ProfileState.Loading ->{

                    }
                    is ProfileState.Success ->{
                        binding.rcHistory.updateSizeCustom(it.listHisotry.size-1)
                        adapterHistory.submitList(it.listHisotry)

                        adapterWatch.submitList(it.listWatch)
                        binding.rcWatch.updateSizeCustom(it.listWatch.size-1)

                        adapterCollection.submitList(it.listCollection)
                        listCollection=it.listCollection
                    }
                }
            }
        }

        binding.creatCollectionProfile.setOnClickListener {
            val newCollectionFragment= NewCollectionFragment()
            newCollectionFragment.show(parentFragmentManager,"newCollectionFragment")
            newCollectionFragment.setFragmentResultListener(Constance.NEW_COLLECTION){ _, bundle->
               val name= bundle.getString("name","")
                val id=bundle.getInt("id", 0)
                if(id!=0){
                listCollection=listCollection.plus(CollectionFilms(id,name,0))
                }
                adapterCollection.submitList(listCollection)
            }
        }


        return binding.root
    }

    fun onClickHistoryItem(id:Int,filmFlag:Boolean){
        Log.d("History","$id  -  is film? $filmFlag")
        if(filmFlag){
            val bundle=Bundle()
            bundle.putInt(Constance.FILM_FILM_INFO_ID,id)
            findNavController().navigate(R.id.action_profileFragment_to_filmInfoFragment,bundle)
        }else{
            val bundle=Bundle()
            bundle.putInt(Constance.ACTOR_ID_FOR_FULL_INFO,id)
            findNavController().navigate(R.id.action_profileFragment_to_actorInfoFragment,bundle)
        }


    }

    fun onClickWatchItem(id:Int){
        val bundle=Bundle()
        bundle.putInt(Constance.FILM_FILM_INFO_ID,id)
        findNavController().navigate(R.id.action_profileFragment_to_filmInfoFragment,bundle)
    }

    fun deleteHistory(){
        viewModel.deleteHistory()
        binding.rcWatch.updateSizeCustom(0)
        adapterHistory.submitList(listEmptyHistory)
    }

    fun deleteWatch(){
        viewModel.deleteWatch()
        binding.rcWatch.updateSizeCustom(0)
        adapterWatch.submitList(listEmptyWatch)
    }

    fun deleteCollection(collection:CollectionFilms){
        viewModel.deleteCollection(collection.id)
      listCollection= listCollection.minus(collection)
        adapterCollection.submitList(listCollection)
    }

    fun init(){
        binding.rcHistory.updateAdapterCustom(adapterHistory)
        binding.rcHistory.updateNameList("Вам было интересно")
        binding.rcHistory.updateClickAllCustom { clickAllHistory() }

        binding.rcWatch.updateNameList("Просмотрено")
        binding.rcWatch.updateAdapterCustom(adapterWatch)
        binding.rcWatch.updateClickAllCustom { clickAllWatch() }

        binding.rcCollectionProfile.adapter=adapterCollection
        binding.rcCollectionProfile.layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
    }

    private fun selectColection(id: Int) {
        val bundle=Bundle()
        bundle.putInt(Constance.CollectionId,id)
        findNavController().navigate(R.id.action_profileFragment_to_allFilmProfileFragment,bundle)
    }

    private fun clickAllHistory(){
        val bundle=Bundle()
        bundle.putInt(Constance.CollectionId,idNameList[3].first)
        findNavController().navigate(R.id.action_profileFragment_to_allFilmProfileFragment,bundle)
    }

    private fun clickAllWatch(){
        val bundle=Bundle()
        bundle.putInt(Constance.CollectionId, idNameList[2].first)
        findNavController().navigate(R.id.action_profileFragment_to_allFilmProfileFragment,bundle)
    }

}