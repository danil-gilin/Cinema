package com.example.cinema.presenter.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.cinema.R
import com.example.cinema.databinding.FragmentMainBinding
import com.example.cinema.entity.onBoardingItem.OnBoardingItem
import com.example.cinema.service.onBoardingAdapter.OnBoardingAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        val list= listOf<OnBoardingItem>(
            OnBoardingItem("Узнавай\nо премьерах",R.drawable.oneboarding1),
            OnBoardingItem("Создавай\nколлекции",R.drawable.oneboarding2),
            OnBoardingItem("Делись\nс друзьями",R.drawable.oneboarding3)
        )
    }

    @Inject
    lateinit var viewModelFactory: MainFactory


    private val viewModel: MainViewModel by viewModels{viewModelFactory}
    private lateinit var binding:FragmentMainBinding

    val adapterVP= OnBoardingAdapter(list) // адаптер для ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)



        activity?.getSharedPreferences("PREFERENCE", 0)?.getBoolean("isFirstRun", true)?.let {
            Log.d("isFirstRunTag", "onCreateView: $it")
            if (it) {
                activity?.getSharedPreferences("PREFERENCE", 0)?.edit()?.putBoolean("isFirstRun", false)?.apply()
            } else {
                findNavController().navigate(R.id.action_mainFragment_to_homeFragment)
            }
        }


        binding.onBoarding.adapter=adapterVP
        binding.onBoarding.disableOverscroll()
        TabLayoutMediator(binding.dotsIndicator, binding.onBoarding) { tab: TabLayout.Tab, position: Int ->
        tab.view.isClickable=false
        }.attach()

        binding.skipp.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_homeFragment)
        }

        return binding.root
    }

    fun ViewPager2.disableOverscroll() {getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER}
}