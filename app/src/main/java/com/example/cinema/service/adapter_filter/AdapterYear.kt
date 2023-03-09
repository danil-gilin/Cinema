package com.example.cinema.service.adapter_filter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.databinding.ItemYearBinding
import java.util.logging.Handler

class AdapterYear(clickYear: ((String)->Unit)?):ListAdapter<String,YearViewHolder>(YearDiffutil()) {
    var click=clickYear
    var checkYear=""
    var yearTo=""
    var yearFrom=""
    var lastCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearViewHolder {
        return YearViewHolder(ItemYearBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: YearViewHolder, position: Int) {
        holder.binding.year.text=getItem(position)
        holder.binding.year.isChecked = (position == lastCheckedPosition)
        if (holder.binding.year.text.toString()==checkYear) {
            holder.binding.year.isChecked = true
            holder.binding.year.isEnabled=false
            lastCheckedPosition=position
        }else{
            holder.binding.year.isChecked = false
            holder.binding.year.isEnabled=true
        }
        holder.binding.year.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                if (trySelectYear(getItem(position))) {
                    if (lastCheckedPosition != -1 && checkYear != holder.binding.year.text.toString()) {
                        notifyItemChanged(lastCheckedPosition)
                        checkYear = holder.binding.year.text.toString()
                        lastCheckedPosition = position
                        buttonView.isEnabled=false
                        Log.d("clickYear", "1   $checkYear")
                        click?.invoke(getItem(position))
                    } else {
                        checkYear = holder.binding.year.text.toString()
                        Log.d("clickYear", "2  $checkYear")
                        click?.invoke(getItem(position))
                        lastCheckedPosition = position
                    }
                }else{
                    buttonView.isChecked = false
                }
            }else{
                if(lastCheckedPosition==position){
                    buttonView.postDelayed({
                        buttonView.isChecked = true
                    },50)
                }
            }
        }
    }

    private fun trySelectYear(yearSelect: String): Boolean {
        if (yearTo != "") {
            if(yearSelect.toInt()<=yearTo.toInt()){
                return false
            }
        }else if(yearFrom!=""){
            if(yearSelect.toInt()>=yearFrom.toInt()){
                Log.d("yearSelect",yearSelect)
                Log.d("yearSelect",yearFrom.toInt().toString())
                return false
            }
            Log.d("yearSelect",yearSelect)
            Log.d("yearSelect",yearFrom.toInt().toString())
        }
        Log.d("yearSelect",yearSelect)
        Log.d("yearSelect",yearFrom)
        return true
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