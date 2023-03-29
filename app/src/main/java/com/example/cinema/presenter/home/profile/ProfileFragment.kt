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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cinema.databinding.FragmentProfileBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.dbCinema.CollectionFilms
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
    }

    @Inject
    lateinit var factory:ProfileFactory

    private val viewModel: ProfileViewModel by viewModels { factory }
    lateinit var binding: FragmentProfileBinding
    private val adapterHistory=AdapterHistory({idFilm,filmFlag->onClickHistoryItem(idFilm,filmFlag)},{deleteHistory()})
    private val adapterWatch=AdapterWatchFilm({idFilm->onClickWatchItem(idFilm)},{deleteWatch()})
    private val adapterCollection=CollectionProfileAdapter(){it->deleteCollection(it)}
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
                    Log.d("CollProfile","$listCollection")
                }
                Log.d("CollProfile","$id")
                adapterCollection.submitList(listCollection)
            }
        }


        return binding.root
    }

    fun onClickHistoryItem(id:Int,filmFlag:Boolean){
        Log.d("History","$id  -  is film? $filmFlag")
    }

    fun onClickWatchItem(id:Int){
        Log.d("Watch","$id")
    }

    fun deleteHistory(){
        viewModel.deleteHistory()
        binding.rcWatch.updateSizeCustom(0)
        adapterHistory.submitList(emptyList())
    }

    fun deleteWatch(){
        viewModel.deleteWatch()
        binding.rcWatch.updateSizeCustom(0)
        adapterWatch.submitList(emptyList())
    }

    fun deleteCollection(collection:CollectionFilms){
        viewModel.deleteCollection(collection.id)
      listCollection= listCollection.minus(collection)
        adapterCollection.submitList(listCollection)
    }

    fun init(){
        binding.rcHistory.updateAdapterCustom(adapterHistory)
        binding.rcHistory.updateNameList("Вам было интересно")

        binding.rcWatch.updateNameList("Просмотрено")
        binding.rcWatch.updateAdapterCustom(adapterWatch)

        binding.rcCollectionProfile.adapter=adapterCollection
        binding.rcCollectionProfile.layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
    }


}