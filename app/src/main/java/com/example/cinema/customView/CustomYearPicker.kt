package com.example.cinema.customView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.databinding.CustomYearPickerBinding
import com.example.cinema.databinding.ListCinemaCustomViewBinding
import com.example.cinema.service.adapter_filter.AdapterYear

class CustomYearPicker@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val year=2023
    private var yearTemp=year
    private var listYear= arrayListOf<String>()
    private val adapterYear= AdapterYear()

    lateinit var binding: CustomYearPickerBinding
    init {
        binding= CustomYearPickerBinding.inflate(LayoutInflater.from(context),this,true)
        binding.rcYear.layoutManager= GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        binding.rcYear.adapter=adapterYear

        //crete year list from year.toInt()-9 to year.toInt()
        for (i in year-11..year){
            listYear.add(i.toString())
        }
        adapterYear.submitList(listYear)
        binding.yearCount.text ="${year-11} - $year"

        binding.backYears.setOnClickListener {
            Log.d("yearTemp",yearTemp.toString())
            yearTemp -= 12
            binding.yearCount.text ="${yearTemp-11} - $yearTemp"
            listYear.clear()
            for (i in yearTemp-11..yearTemp){
                listYear.add(i.toString())
            }
            Log.d("yearTemp",yearTemp.toString())
            Log.d("listYear",listYear.toString())
           adapterYear.submitList(listYear)
            adapterYear.notifyDataSetChanged()
        }
        binding.nextYears.setOnClickListener {
            Log.d("yearTemp",yearTemp.toString())
            if(yearTemp+12>=year) {
                binding.yearCount.text ="${year-11} - $year"
                yearTemp = year
                listYear.clear()
                for (i in yearTemp-11..yearTemp){
                    listYear.add(i.toString())
                }
                adapterYear.submitList(listYear)
                adapterYear.notifyDataSetChanged()
            }else{
                binding.yearCount.text ="${yearTemp+1} - ${yearTemp+12}"
                yearTemp += 12
                listYear.clear()
                for (i in yearTemp-11..yearTemp){
                    listYear.add(i.toString())
                }
                adapterYear.submitList(listYear)
                adapterYear.notifyDataSetChanged()
                Log.d("listYear","${listYear}= ${yearTemp}")
            }
            Log.d("listYear",listYear.toString())
            Log.d("yearTemp",yearTemp.toString())
        }

    }



    fun setYear(year: String){

    }

    fun setDateFrom(year: String){

    }

    fun setDateTo(year: String){

    }


}