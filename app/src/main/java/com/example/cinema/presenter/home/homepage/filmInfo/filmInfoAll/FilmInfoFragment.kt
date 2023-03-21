package com.example.cinema.presenter.home.homepage.filmInfo.filmInfoAll

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputFilter
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.databinding.FragmentFilmInfoBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.fullInfoActor.typeListFilm.TypeListFilm
import com.example.cinema.presenter.home.homepage.bottomSheetFilm.AddCollectionFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class FilmInfoFragment : Fragment() {

    companion object {
        fun newInstance() = FilmInfoFragment()
        private const val INITIAL_IS_COLLAPSED = true
    }

    @Inject
    lateinit var factory: FilmFactory

    private val viewModel: FilmInfoViewModel by viewModels { factory }
    lateinit var binding: FragmentFilmInfoBinding
    private var isCollapsed: Boolean = INITIAL_IS_COLLAPSED
    private var maxLinesTxtView=0
    private var idFilm:Int?=0
    private var stateWatch=false
    private var stateWantToWatch=false
    private var stateLike=false
    private var textFilmDescription=""
    private lateinit var typeListFilm: TypeListFilm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmInfoBinding.inflate(inflater)
        arguments.let {
            if (idFilm == 0) {
                idFilm = it?.getInt(Constance.FILM_FILM_INFO_ID)
                typeListFilm= TypeListFilm("Похожие фильмы", semilarFilmId = idFilm)
                viewModel.getFilm(idFilm!!)
            }
        }
        viewModel.getWatchesFilm()

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.state.collect { state ->
                when (state) {
                    is FilmInfoState.Loading -> {

                    }
                    is FilmInfoState.Success -> {
                        if(state.imgLogo!=null){
                            binding.posterImg.visibility=View.VISIBLE
                            Glide.with(binding.posterImg).load(state.imgLogo).centerInside().into(binding.posterImg)
                        }
                        Glide.with(binding.filmFullImg).load(state.imgPreview).centerCrop().into(binding.filmFullImg)
                        binding.shortInfo1.text = state.shotInfo1
                        binding.shortInfo2.text = state.shotInfo2
                        binding.shortInfo3.text = state.shotInfo3

                        if(state.actorList.second.isEmpty()) {
                            binding.rcActor.visibility = View.GONE
                        }else{
                            binding.rcActor.updateListActor(state.actorList,3)
                            binding.rcActor.updateAllFilmBtn(state.actorList.second.size,20)
                            binding.rcActor.allClickEmpty {
                                val bundle = Bundle()
                                bundle.putInt(Constance.FILM_ALL_ACTOR_ID,idFilm!! )
                                bundle.putString(Constance.FILM_ALL_ACTOR_TYPE,"ACTOR")
                                findNavController().navigate(R.id.action_filmInfoFragment_to_allActorFragment, bundle)
                            }
                            binding.rcActor.clickActor { idActor ->
                                val bundle = Bundle()
                                bundle.putInt(Constance.ACTOR_ID_FOR_FULL_INFO, idActor)
                                findNavController().navigate(R.id.action_filmInfoFragment_to_actorInfoFragment, bundle)
                            }
                        }

                        if(state.workerList.second.isEmpty()) {
                            binding.rcFilmWorker.visibility = View.GONE
                        }else {
                            binding.rcFilmWorker.updateListWorker(state.workerList, 2)
                            binding.rcFilmWorker.updateAllFilmBtn(state.workerList.second.size,6)
                            binding.rcFilmWorker.allClickEmpty {
                                val bundle = Bundle()
                                bundle.putInt(Constance.FILM_ALL_ACTOR_ID,idFilm!! )
                                bundle.putString(Constance.FILM_ALL_ACTOR_TYPE,"WORKER")
                                findNavController().navigate(R.id.action_filmInfoFragment_to_allActorFragment, bundle)
                            }
                            binding.rcFilmWorker.clickActor { idActor ->
                                val bundle = Bundle()
                                bundle.putInt(Constance.ACTOR_ID_FOR_FULL_INFO, idActor)
                                findNavController().navigate(R.id.action_filmInfoFragment_to_actorInfoFragment, bundle)
                            }
                        }

                        if(state.galleryList.second.isEmpty()){
                            binding.rcGallery.visibility=View.GONE
                        }else{

                            binding.rcGallery.allClickEmpty {
                                val bundle = Bundle()
                                bundle.putInt(Constance.FILM_GALLERY_ID_FILM,idFilm!! )
                                findNavController().navigate(R.id.action_filmInfoFragment_to_filmGallery, bundle)
                            }
                            binding.rcGallery.updateListGallery(state.galleryList)
                            binding.rcGallery.updateAllFilmBtn(state.galleryList.second.size)
                            binding.rcGallery.clickGallery { url, imageView ->
                                val bundle=Bundle()
                                bundle.putString(Constance.FULL_SCREEN_IMG_URL_IMG, url)
                                val extras = FragmentNavigatorExtras(
                                    imageView to "Transition_img_full_screen"
                                )
                                findNavController().navigate(R.id.action_filmInfoFragment_to_fullScreenImgFragment, bundle,null,extras)
                            }
                        }

                       if(state.similarList.second.isEmpty()) {
                           binding.rcFilm.visibility = View.GONE
                       }else {
                           binding.rcFilm.updateListSimilarFilm(state.similarList, state.genre,state.filmsWatch)
                           binding.rcFilm.updateAllFilmBtn(state.similarList.second.size)
                           binding.rcFilm.setClickInfoFilm { id ->
                               val bundle = Bundle()
                               bundle.putInt(Constance.FILM_FILM_INFO_ID, id)
                               findNavController().navigate(R.id.action_filmInfoFragment_self, bundle)
                           }
                           binding.rcFilm.updategenre(typeListFilm)
                           binding.rcFilm.setClickAllFilm {
                                 val bundle = Bundle()
                                 bundle.putParcelable(Constance.NAME_GENRE_ALL_FILM,typeListFilm)
                                 findNavController().navigate(R.id.action_filmInfoFragment_to_allFilmFragment, bundle)
                           }
                       }


                        if(state.infoSerial!=null){
                            binding.sessonInfo.text=state.infoSerial
                            binding.serialBtn.setOnClickListener {
                                val bundle = Bundle()
                                bundle.putInt(Constance.SERIAL_ID_FOR_SEASON,idFilm!! )
                                bundle.putString(Constance.SERIAL_NAME_FOR_SEASON,state.name)
                                findNavController().navigate(R.id.action_filmInfoFragment_to_filmSerialInfoFragment, bundle)
                            }
                        }else{
                            binding.linearSerilaInfo.visibility=View.GONE
                        }

                        if(state.filmShortDescription.isNotEmpty()){
                            textFilmDescription=state.filmShortDescription
                            if (state.filmDescription.isNotEmpty()){
                                textFilmDescription=textFilmDescription+'\n'+'\n'+state.filmDescription
                            }
                        }else{
                            textFilmDescription=state.filmDescription
                        }

                        //информация о фильме
                        if(state.filmShortDescription.length +state.filmDescription.length <250){
                            binding.filmFullDescription.text=textFilmDescription
                            if(state.filmDescription.isEmpty() && state.filmShortDescription.isEmpty()) {
                                binding.filmFullDescription.visibility = View.GONE
                            }

                        }else{
                            binding.filmFullDescription.filters= arrayOf(*binding.filmFullDescription.filters,InputFilter.LengthFilter(253))
                            val sb : SpannableStringBuilder = SpannableStringBuilder(textFilmDescription)
                            val bss = StyleSpan(Typeface.BOLD)
                            val sbShort=SpannableStringBuilder(sb.take(250).toString()+"...")
                            sbShort.setSpan(bss, 0, state.filmShortDescription.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                            sb.setSpan(bss, 0, state.filmShortDescription.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                            maxLinesTxtView=binding.filmFullDescription.maxLines

                            binding.filmFullDescription.text=sbShort
                            binding.filmFullDescription.setOnClickListener {
                                if(isCollapsed) {
                                    binding.filmFullDescription.filters= arrayOf(InputFilter.LengthFilter(Integer.MAX_VALUE),)
                                    binding.filmFullDescription.maxLines = Integer.MAX_VALUE
                                    binding.filmFullDescription.text=sb
                                    isCollapsed = false
                                }else{
                                    binding.filmFullDescription.filters= arrayOf(InputFilter.LengthFilter(253))
                                    binding.filmFullDescription.maxLines = maxLinesTxtView
                                    binding.filmFullDescription.text=sbShort
                                    isCollapsed = true
                                }
                            }
                        }

                        stateWatch=state.isWatch
                        stateLike=state.isLike
                        stateWantToWatch=state.isWantToWatch


                        initBtn()
                        initStateBtn()

                    }
                    is FilmInfoState.Error -> {

                    }
                }
            }
        }

        viewModel.watchsFilm.onEach {
            binding.rcFilm.updateListSemilarWatchesFilms(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.webURL.onEach {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.filmBottom.onEach {
            val bottomSheetDialog = AddCollectionFragment()
            bottomSheetDialog.arguments = Bundle().apply {
                putParcelable(Constance.FILM_BOTTOM_SHEET, it)
            }
            bottomSheetDialog.show(childFragmentManager, bottomSheetDialog.tag)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }

    private fun initBtn(){
        binding.watchBtn.setOnClickListener {
            if (stateWatch){
                stateWatch=false
                binding.watchBtn.setImageResource(R.drawable.icon_un_watch)
            }else{
                stateWatch=true
                binding.watchBtn.setImageResource(R.drawable.icon_watch_film_info)
            }
            viewModel.addWatchFilm(stateWatch)
        }


        binding.likeBtn.setOnClickListener {
            if (stateLike){
                stateLike=false
                binding.likeBtn.setImageResource(R.drawable.icon_unlike_film)
            }else{
                stateLike=true
                binding.likeBtn.setImageResource(R.drawable.icon_like_film)
            }
            viewModel.addLikeFilm(stateLike)
        }

        binding.bookmarksBtn.setOnClickListener {
            if (stateWantToWatch){
                stateWantToWatch=false
                binding.bookmarksBtn.setImageResource(R.drawable.icon_un_bookmarks)
            }else{
                stateWantToWatch=true
                binding.bookmarksBtn.setImageResource(R.drawable.icon_bookmarks)
            }
            viewModel.addWantToWatchFilm(stateWantToWatch)
        }

        binding.shareBtn.setOnClickListener {
            viewModel.getUrlWeb()
        }

        binding.dopMenuBtn.setOnClickListener {
            //create bottom sheet dialog with AddCollectionFragment
            viewModel.getFilmToBottomSheet()
        }

    }

    private fun initStateBtn(){
        if (stateWatch){
            binding.watchBtn.setImageResource(R.drawable.icon_watch_film_info)
        }else{
            binding.watchBtn.setImageResource(R.drawable.icon_un_watch)
        }

        if (stateLike){
            binding.likeBtn.setImageResource(R.drawable.icon_like_film)
        }else{
            binding.likeBtn.setImageResource(R.drawable.icon_unlike_film)
        }

        if(stateWantToWatch){
            binding.bookmarksBtn.setImageResource(R.drawable.icon_bookmarks)
        }else{
            binding.bookmarksBtn.setImageResource(R.drawable.icon_un_bookmarks)
        }

    }

}