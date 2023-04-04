package com.example.cinema.customView.newCollection

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cinema.R
import com.example.cinema.databinding.FragmentNewCollectionBinding
import com.example.cinema.entity.Constance
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NewCollectionFragment : DialogFragment() {

    companion object {
        fun newInstance() = NewCollectionFragment()
    }

    @Inject
    lateinit var factory: NewCollectionFactory


    private val viewModel: NewCollectionViewModel by viewModels { factory }
    lateinit var binding:FragmentNewCollectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNewCollectionBinding.inflate(inflater)
        binding.btnDone.setOnClickListener {
            viewModel.addCollection(binding.nameCollectionInput.text.toString())
        }
        binding.btnClose2.setOnClickListener {
            this.dismiss()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect {
                when (it) {
                    is StateNewCollection.SuccessSaveCollection -> {
                        val bundle=Bundle()
                        bundle.putString("name",binding.nameCollectionInput.text.toString())
                        bundle.putInt("id",it.id)
                        parentFragmentManager.setFragmentResult(Constance.NEW_COLLECTION,bundle)
                        this@NewCollectionFragment.dismiss()
                    }
                    is StateNewCollection.Error ->{

                    }
                    StateNewCollection.Loading ->{

                    }
                }
            }
        }

        binding.backDialog.setOnClickListener {
            this.dismiss()
        }

        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.DialogThemeNewCollection
    }




}