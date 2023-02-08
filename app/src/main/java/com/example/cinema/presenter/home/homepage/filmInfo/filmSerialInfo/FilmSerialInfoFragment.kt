package com.example.cinema.presenter.home.homepage.filmInfo.filmSerialInfo

import android.graphics.Color
import android.graphics.Typeface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cinema.R
import com.example.cinema.databinding.FragmentFilmSerialInfoBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.serialInfo.Episode
import com.example.cinema.presenter.home.homepage.filmInfo.filmInfoAll.FilmInfoViewModel
import com.example.cinema.service.adapterSerial.AdapterSerial
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FilmSerialInfoFragment : Fragment() {

    companion object {
        fun newInstance() = FilmSerialInfoFragment()
        private val TenNumber =
            listOf<String>("10", "11", "12", "13", "14", "15", "16", "17", "18", "19")
    }

    @Inject
    lateinit var factory: FilmSerialInfoFactory

    private val viewModel: FilmSerialInfoViewModel by viewModels { factory }
    lateinit var binding: FragmentFilmSerialInfoBinding
    private val adapter = AdapterSerial()
    var idSerial = 0
    var nameSerial = ""
    var isCheckedFirst = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            if (idSerial == 0) {
                idSerial = it.getInt(Constance.SERIAL_ID_FOR_SEASON, 0)
                nameSerial = it.getString(Constance.SERIAL_NAME_FOR_SEASON, "")
                viewModel.getSerialInfo(idSerial)
            }
        }
        binding = FragmentFilmSerialInfoBinding.inflate(inflater, container, false)
        binding.rcAllSerial.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { state ->
                when (state) {
                    is SerialInfoState.Loading -> {

                    }
                    is SerialInfoState.Error -> {

                    }
                    is SerialInfoState.Success -> {
                        binding.previewSerial.text = nameSerial
                        val episodesListtemp = listOf<Episode>(
                            Episode(
                                0,
                                "",
                                "1 сезон, ${getEpisodString(state.serialInfo.items[0].episodes.size)}",
                                null,
                                1,
                                null
                            )
                        )
                        val episodesList =episodesListtemp.plus(state.serialInfo.items[0].episodes)
                        adapter.submitList(episodesList)
                        binding.filterSerial.removeAllViews()
                        state.serialInfo.items.forEach { item ->
                            if (isCheckedFirst) {
                                setChipGroup(item.number.toString(), true)
                                isCheckedFirst = false
                            } else {
                                setChipGroup(item.number.toString(), false)
                            }
                        }

                        binding.filterSerial.setOnCheckedStateChangeListener { group, checkedId ->
                            val chip = group.findViewById<Chip>(checkedId[0])
                            val text = chip.text.toString()
                            val episodesListtemp = listOf<Episode>(
                                Episode(
                                    0,
                                    "",
                                    "$text сезон, ${getEpisodString(state.serialInfo.items[text.toInt() - 1].episodes.size)}",
                                    null,
                                    text.toInt(),
                                    null
                                )
                            )
                            val episodesList =episodesListtemp.plus( state.serialInfo.items[text.toInt() - 1].episodes)
                            adapter.submitList(episodesList)
                        }
                    }

                }
            }
        }


        return binding.root
    }


    private fun setChipGroup(name: String, isChecked: Boolean) {
        val chip2 = layoutInflater.inflate(R.layout.singl_chip_number, binding.filterSerial, false) as Chip
        chip2.id = View.generateViewId()
        chip2.text = name
        chip2.isChecked = isChecked
        binding.filterSerial.addView(chip2)
    }

    private fun getEpisodString(episodes: Int): String {
        if (episodes.toString() in TenNumber || episodes.toString().last() == '0') {
            return " ${episodes} серий"
        } else if (episodes.toString().last() == '1') {
            return " ${episodes} серия"
        } else if (episodes.toString().last() in '2'..'4') {
            return " ${episodes} серии"
        } else if (episodes.toString().last() in '5'..'9') {
            return " ${episodes} серий"
        } else {
            return " ${episodes} серий"
        }
    }
}