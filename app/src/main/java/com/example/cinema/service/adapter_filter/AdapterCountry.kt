package com.example.cinema.service.adapter_filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.databinding.ItemSearchTextBinding
import com.example.cinema.entity.filterEntity.CountryFilter

class AdapterCountry(private val click: (Int,String) -> Unit) : ListAdapter<CountryFilter, CountryViewHolder>(DiffutilCountryGenre()) {
    var selectCountry=""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            ItemSearchTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
       val item=getItem(position)
        with(holder.binding){
            if(item.country!=selectCountry){
                linearFilterItem.setBackgroundResource(R.color.white)
            }else{
                linearFilterItem.setBackgroundResource(R.color.select_item_back_ground)
            }
            nameFilter.text=item.country
            root.setOnClickListener {
                click(item.id,item.country)
            }
        }
    }

}

class CountryViewHolder(val binding: ItemSearchTextBinding) : RecyclerView.ViewHolder(binding.root)

class DiffutilCountryGenre : DiffUtil.ItemCallback<CountryFilter>() {
    override fun areItemsTheSame(oldItem: CountryFilter, newItem: CountryFilter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CountryFilter, newItem: CountryFilter): Boolean {
        return oldItem == newItem
    }
}