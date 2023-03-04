package com.example.cinema.service.adapter_filter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.databinding.ItemYearBinding

class AdapterYear:ListAdapter<String,YearViewHolder>(YearDiffutil()) {
    var checkYear=""
    var lastCheckedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearViewHolder {
        return YearViewHolder(ItemYearBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: YearViewHolder, position: Int) {
        holder.binding.year.text=getItem(position)
        holder.binding.year.isChecked = (position == lastCheckedPosition)
        holder.binding.year.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if(lastCheckedPosition!=-1){
                    notifyItemChanged(lastCheckedPosition)
                    lastCheckedPosition=position
                }else{
                    lastCheckedPosition=position
                }
            }
        }
    }
}


class YearViewHolder(val binding:ItemYearBinding): RecyclerView.ViewHolder(binding.root)

class YearDiffutil: DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem==newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem==newItem
    }

}