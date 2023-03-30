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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class FilmGallery : Fragment() {

    companion object {
        fun newInstance() = FilmGallery()
        val nameTypeGallery= listOf("Кадры", "Со съемок", "Постеры", "Фанарты", "Промо", "Концепция", "Обои", "Обложка", "Скриншот")
        val nameTypeGalleryEng= listOf("STILL","SHOOTING","POSTER","FAN_ART","PROMO","CONCEPT","WALLPAPER","COVER","SCREENSHOT")
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
            viewModel.getFilterGalery(idFilm!!)
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
                            viewModel.getGallery(idFilm!!,nameTypeGalleryEng[0])
                            setFlowGallery()
                        }
                        galleryAllFilm = state.data
                        setFilterFromGallery(state.data)
                        binding.filterGallery.setOnCheckedStateChangeListener { group, checkedId ->
                            val chip=group.findViewById<Chip>(checkedId.getOrNull(0) ?: 0)
                            when(chip.text.toString().filter { it != ' '&&it.isLetter()}) {
                                nameTypeGallery[0] -> {
                                    selectedChip=0
                                  viewModel.getGallery(idFilm!!,nameTypeGalleryEng[0])
                                    setFlowGallery()
                                }
                                nameTypeGallery[1].filter {it != ' '} -> {
                                    selectedChip=1
                                    viewModel.getGallery(idFilm!!,nameTypeGalleryEng[1])
                                    setFlowGallery()
                                }
                                nameTypeGallery[2] -> {
                                    selectedChip=2
                                    viewModel.getGallery(idFilm!!,nameTypeGalleryEng[2])
                                    setFlowGallery()
                                }
                                nameTypeGallery[3] -> {
                                    selectedChip=3
                                    viewModel.getGallery(idFilm!!,nameTypeGalleryEng[3])
                                    setFlowGallery()
                                }
                                nameTypeGallery[4] -> {
                                    selectedChip=4
                                    viewModel.getGallery(idFilm!!,nameTypeGalleryEng[4])
                                    setFlowGallery()
                                }
                                nameTypeGallery[5] -> {
                                    selectedChip=5
                                    viewModel.getGallery(idFilm!!,nameTypeGalleryEng[5])
                                    setFlowGallery()
                                }
                                nameTypeGallery[6] -> {
                                    selectedChip=6
                                    viewModel.getGallery(idFilm!!,nameTypeGalleryEng[6])
                                    setFlowGallery()
                                }
                                nameTypeGallery[7] -> {
                                    selectedChip=7
                                    viewModel.getGallery(idFilm!!,nameTypeGalleryEng[7])
                                    setFlowGallery()
                                }
                                nameTypeGallery[8] -> {
                                    selectedChip=8
                                    viewModel.getGallery(idFilm!!,nameTypeGalleryEng[8])
                                    setFlowGallery()
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

        binding.backGallery.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun setFlowGallery() {
        viewModel.listGalleryType?.onEach {
            Log.d("PaggeGallery","it.toString()")
            adapterAllGallery.submitData(it)
        }?.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setChipGroup(name: String,isChecked:Boolean,size:Int) {
        val chip2 = layoutInflater.inflate(R.layout.single_chip, binding.filterGallery, false) as Chip
        chip2.id= View.generateViewId()
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
        binding.filterGallery.addView(chip2)
    }

    private fun setFilterFromGallery(galleryAllFilm: GalleryAllFilm){
        if(galleryAllFilm.still!=0) {
            setChipGroup(nameTypeGallery[0],selectedChip==0, galleryAllFilm.still)
        }
        if(galleryAllFilm.shooting!=0) {
            setChipGroup(nameTypeGallery[1],selectedChip==1, galleryAllFilm.shooting)
        }
        if(galleryAllFilm.poster!=0) {
            setChipGroup(nameTypeGallery[2],selectedChip==2, galleryAllFilm.poster)
        }
        if(galleryAllFilm.fanArt!=0) {
            setChipGroup(nameTypeGallery[3],selectedChip==3, galleryAllFilm.fanArt)
        }
        if(galleryAllFilm.promo!=0) {
            setChipGroup(nameTypeGallery[4],selectedChip==4, galleryAllFilm.promo)
        }
        if(galleryAllFilm.concept!=0) {
            setChipGroup(nameTypeGallery[5],selectedChip==5, galleryAllFilm.concept)
        }
        if(galleryAllFilm.wallpaper!=0) {
            setChipGroup(nameTypeGallery[6],selectedChip==6, galleryAllFilm.wallpaper)
        }
        if(galleryAllFilm.cover!=0) {
            setChipGroup(nameTypeGallery[7],selectedChip==7, galleryAllFilm.cover)
        }
        if(galleryAllFilm.screenshot!=0) {
            setChipGroup(nameTypeGallery[8],selectedChip==8, galleryAllFilm.screenshot)
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