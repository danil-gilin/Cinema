package com.example.cinema.presenter.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cinema.R
import com.example.cinema.databinding.FragmentMainBinding
import com.example.cinema.entity.OnBoardingItem
import com.example.cinema.service.OnBoardingAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: MainFactory

    private val viewModel: MainViewModel by viewModels{viewModelFactory}
    private lateinit var binding:FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        val list= listOf<OnBoardingItem>(
            OnBoardingItem("Узнавай\nо премьерах",R.drawable.oneboarding1),
            OnBoardingItem("Создавай\nколлекции",R.drawable.oneboarding2),
            OnBoardingItem("Делись\nс друзьями",R.drawable.oneboarding3)
        )
        val adapterVP=OnBoardingAdapter(list)
        binding.onBoarding.adapter=adapterVP
        binding.onBoarding.disableOverscroll()
        TabLayoutMediator(binding.dotsIndicator, binding.onBoarding) { tab: TabLayout.Tab, position: Int ->
        tab.view.isClickable=false
        }.attach()





        return binding.root
    }
    fun ViewPager2.disableOverscroll() {getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER}
}