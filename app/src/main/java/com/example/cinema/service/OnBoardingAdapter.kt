package com.example.cinema.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.databinding.OneboardingItemBinding
import com.example.cinema.entity.OnBoardingItem


class OnBoardingAdapter(private val listOnBoarding:List<OnBoardingItem>) :RecyclerView.Adapter<OnBoardingViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(OneboardingItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        val item=listOnBoarding[position]
        holder.binding.imgBoarding.setImageResource(item.image)
        holder.binding.txtBoarding.text=item.title
    }

    override fun getItemCount(): Int {
        return listOnBoarding.size
    }
}

class OnBoardingViewHolder (val binding: OneboardingItemBinding):RecyclerView.ViewHolder(binding.root)