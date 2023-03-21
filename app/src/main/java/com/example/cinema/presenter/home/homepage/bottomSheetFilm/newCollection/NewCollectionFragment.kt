package com.example.cinema.presenter.home.homepage.bottomSheetFilm.newCollection

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.cinema.R
import com.example.cinema.databinding.FragmentNewCollectionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NewCollectionFragment : DialogFragment() {

    companion object {
        fun newInstance() = NewCollectionFragment()
    }

    @Inject
    lateinit var factory:NewCollectionFactory


    private val viewModel: NewCollectionViewModel by viewModels { factory }
    lateinit var binding:FragmentNewCollectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNewCollectionBinding.inflate(inflater)
        binding.btnDone.setOnClickListener {
            viewModel.addCollection(binding.nameCollectionInput.text.toString())
            this.dismiss()
        }
        binding.btnClose2.setOnClickListener {
            this.dismiss()
        }
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

}