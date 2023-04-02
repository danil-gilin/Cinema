package com.example.cinema.customView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinema.R
import com.example.cinema.databinding.FragmentErrorBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorBottomSheet(val error:String) :BottomSheetDialogFragment() {

    lateinit var binding:FragmentErrorBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentErrorBottomSheetBinding.inflate(inflater)
        binding.txtError.text=error

        binding.btnCloseError.setOnClickListener {
            this.dismiss()
        }

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }
}

