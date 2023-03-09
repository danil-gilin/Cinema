package com.example.cinema.presenter.home.searchPage.country

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
import com.example.cinema.entity.Constance
import com.example.cinema.service.adapter_filter.AdapterCountry
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class CountryFragment : Fragment() {

    companion object {
        fun newInstance() = CountryFragment()
    }

    @Inject
    lateinit var factory: CountryFactory

    private val viewModel: CountryViewModel by viewModels { factory }
    lateinit var binding: FragmentCountryBinding
    var adapter = AdapterCountry() { id, name -> selectCountry(id, name) }
    private lateinit var selectCountry:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            selectCountry = it.getString(Constance.CountryFilter,"").toString()
            viewModel.getCountryList(selectCountry)
            adapter.selectCountry=selectCountry
        }
        binding = FragmentCountryBinding.inflate(inflater)
        binding.rcCountry.adapter = adapter


        viewModel.listCountryChannel.onEach {
            Log.d("CountryList",it.toString())
            adapter.submitList(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.searchTxtCountry.addTextChangedListener {
            viewModel.getCountryListSearch(it.toString())
        }

        binding.backCountry.setOnClickListener {
            selectCountry(0,adapter.selectCountry)
        }

        binding.clearCountry.setOnClickListener {
            adapter.selectCountry=""
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    private fun selectCountry(id:Int,name:String){
        //popBackStack with result
        val bundle=Bundle()
        bundle.putInt(Constance.CountryFilterId,id)
        bundle.putString(Constance.CountryFilter,name)
        setFragmentResult(Constance.CountryFilter,bundle)
        findNavController().popBackStack()
    }
}