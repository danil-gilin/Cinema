package com.example.cinema.presenter.home.homepage.filmInfo.fullScreenImg

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.databinding.FragmentFullScreenImgBinding
import com.example.cinema.entity.Constance
import dagger.hilt.android.AndroidEntryPoint
import java.nio.file.Files.move

@AndroidEntryPoint
class FullScreenImgFragment : Fragment() {
    var imgUrl=""
    lateinit var binding: FragmentFullScreenImgBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFullScreenImgBinding.inflate(inflater)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.img_transition_gallery)


        arguments?.let {
            imgUrl = it.getString(Constance.FULL_SCREEN_IMG_URL_IMG,"")
        }
       Glide.with(requireContext()).load(imgUrl).into(binding.fullScreenImg)

        binding.backFullScreenBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    companion object {
        fun newInstance() = FullScreenImgFragment()
    }
}