package com.example.cinema.presenter.home.homepage.filmInfo.filmGallery


import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.cinema.R
import com.example.cinema.databinding.FragmentFilmGalleryBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.allGallery.GalleryAllFilm
import com.example.cinema.service.adapterAllGallery.AdapterAllGallery
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FilmGallery : Fragment() {

    companion object {
        fun newInstance() = FilmGallery()
        val nameTypeGallery= listOf("Кадры", "Со съемок", "Постеры", "Фанарты", "Промо", "Концепция", "Обои", "Обложка", "Скриншот")
    }

    @Inject
    lateinit var factory: FilmGalleryFactory

    private val viewModel: FilmGalleryViewModel by viewModels {factory}
    private lateinit var binding: FragmentFilmGalleryBinding
    private var idFilm:Int?=0
    private var flagFirstChip=true
    private var selectedChip:Int=0
    private val adapterAllGallery= AdapterAllGallery(){imgUrl,ImgView->clickImage(imgUrl,ImgView)}
    private var galleryAllFilm:GalleryAllFilm?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFilmGalleryBinding.inflate(inflater)

        arguments.let {
            if (idFilm==0){
            idFilm = it?.getInt(Constance.FILM_GALLERY_ID_FILM)
            viewModel.getFilmGalery(idFilm!!)
            }
        }


        val layuotManeger= GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL, false)
        layuotManeger.spanSizeLookup=object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if((position+1)%3==0) 2 else 1
            }
        }

        binding.rcAllGallery.adapter=adapterAllGallery
        binding.rcAllGallery.layoutManager=layuotManeger

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect{ state ->
                when(state) {
                    is StateFilmGallery.Loading -> {

                    }
                    is StateFilmGallery.Success -> {
                        if(galleryAllFilm==null) {
                            adapterAllGallery.submitList(state.data.still)
                            Log.d("GalleryAll", "Success")
                        }
                        galleryAllFilm = state.data
                        flagFirstChip=true
                        setFilterFromGallery(state.data)
                        binding.filterGallery.setOnCheckedStateChangeListener { group, checkedId ->
                            val chip=group.findViewById<Chip>(checkedId[0])


                            when(chip.text.toString().filter { it != ' '&&it.isLetter()}) {
                                nameTypeGallery[0] -> {
                                    selectedChip=0
                                    adapterAllGallery.submitList(state.data.still)
                                }
                                nameTypeGallery[1].filter {it != ' '} -> {
                                    selectedChip=1
                                    adapterAllGallery.submitList(state.data.shooting)
                                }
                                nameTypeGallery[2] -> {
                                    selectedChip=2
                                    adapterAllGallery.submitList(state.data.poster)
                                }
                                nameTypeGallery[3] -> {
                                    selectedChip=3
                                    adapterAllGallery.submitList(state.data.fanArt)
                                }
                                nameTypeGallery[4] -> {
                                    selectedChip=4
                                    adapterAllGallery.submitList(state.data.promo)
                                }
                                nameTypeGallery[5] -> {
                                    selectedChip=5
                                    adapterAllGallery.submitList(state.data.concept)
                                }
                                nameTypeGallery[6] -> {
                                    selectedChip=6
                                    adapterAllGallery.submitList(state.data.wallpaper)
                                }
                                nameTypeGallery[7] -> {
                                    selectedChip=7
                                    adapterAllGallery.submitList(state.data.cover)
                                }
                                nameTypeGallery[8] -> {
                                    selectedChip=8
                                    adapterAllGallery.submitList(state.data.screenshot)
                                }
                            }
                        }
                    }
                    is StateFilmGallery.Error -> {
                        Log.d("GalleryAll", state.error)
                    }
                }
            }
        }

        return binding.root
    }

    private fun setChipGroup(name: String,isChecked:Boolean,size:Int) {
        val chip2 = layoutInflater.inflate(R.layout.single_chip, binding.filterGallery, false) as Chip
        val word: Spannable = SpannableString("  $size")
        word.setSpan(
            ForegroundColorSpan(Color.parseColor("#B5B5C9")),
            0,
            word.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        word.setSpan(
            AbsoluteSizeSpan(14, true),
            0,
            word.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        word.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            word.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        chip2.text= name
        chip2.append(word)
        chip2.isChecked=isChecked
        if (flagFirstChip) {
            val r: Resources = resources
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                26.0f,
                r.displayMetrics
            ).toInt()
            val params = chip2.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(px, 0, 0, 0)
            chip2.layoutParams = params
            flagFirstChip=false
        }
        binding.filterGallery.addView(chip2)
    }

    private fun setFilterFromGallery(galleryAllFilm: GalleryAllFilm){
        if(galleryAllFilm.still?.isNotEmpty() == true) {
            setChipGroup(nameTypeGallery[0],selectedChip==0, galleryAllFilm.still!!.size)
        }
        if(galleryAllFilm.shooting?.isNotEmpty() == true) {
            setChipGroup(nameTypeGallery[1],selectedChip==1, galleryAllFilm.shooting!!.size)
        }
        if(galleryAllFilm.poster?.isNotEmpty() == true) {
            setChipGroup(nameTypeGallery[2],selectedChip==2, galleryAllFilm.poster!!.size)
        }
        if(galleryAllFilm.fanArt?.isNotEmpty() == true) {
            setChipGroup(nameTypeGallery[3],selectedChip==3, galleryAllFilm.fanArt!!.size)
        }
        if(galleryAllFilm.promo?.isNotEmpty() == true) {
            setChipGroup(nameTypeGallery[4],selectedChip==4, galleryAllFilm.promo!!.size)
        }
        if(galleryAllFilm.concept?.isNotEmpty() == true) {
            setChipGroup(nameTypeGallery[5],selectedChip==5, galleryAllFilm.concept!!.size)
        }
        if(galleryAllFilm.wallpaper?.isNotEmpty() == true) {
            setChipGroup(nameTypeGallery[6],selectedChip==6, galleryAllFilm.wallpaper!!.size)
        }
        if(galleryAllFilm.cover?.isNotEmpty() == true) {
            setChipGroup(nameTypeGallery[7],selectedChip==7, galleryAllFilm.cover!!.size)
        }
        if(galleryAllFilm.screenshot?.isNotEmpty() == true) {
            setChipGroup(nameTypeGallery[8],selectedChip==8, galleryAllFilm.screenshot!!.size)
        }
    }


    private fun clickImage(it: String, imageView: ImageView) {
        val bundle=Bundle()
        bundle.putString(Constance.FULL_SCREEN_IMG_URL_IMG, it)
        val extras = FragmentNavigatorExtras(
            imageView to "Transition_img_full_screen"
        )
        findNavController().navigate(R.id.action_filmGallery_to_fullScreenImgFragment, bundle,null,extras)
    }
}