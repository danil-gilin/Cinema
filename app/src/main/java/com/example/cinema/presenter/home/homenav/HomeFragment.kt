package com.example.cinema.presenter.home.homenav

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    @Inject
    lateinit var viewModelFactory: HomeFactory

    private val viewModel: HomeViewModel by viewModels{viewModelFactory}
    lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        val navHost=childFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController=navHost.navController
        binding.bottomNav.setupWithNavController(navController)

        binding.bottomNav.setOnItemReselectedListener {
            if (it.itemId==R.id.homePage){
                navController.popBackStack(R.id.homePageFragment, inclusive = false)
            }
            if(it.itemId==R.id.searchPage){
                navController.popBackStack(R.id.searchFragment, inclusive = false)
            }
            if(it.itemId==R.id.profilePage){
                navController.popBackStack(R.id.profileFragment, inclusive = false)
            }
        }



        return binding.root
    }
}