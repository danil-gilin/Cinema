package com.example.cinema.customView

import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentLoaderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoaderFragment : DialogFragment() {

    lateinit var binding: FragmentLoaderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }
}