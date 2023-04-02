package com.example.cinema.presenter.home.homepage.filmInfo.actorInfo.actorFilmHistory

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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.customView.ErrorBottomSheet
import com.example.cinema.databinding.FragmentActorFilmHistoryBinding
import com.example.cinema.entity.Constance
import com.example.cinema.entity.allGallery.GalleryAllFilm
import com.example.cinema.presenter.home.homepage.filmInfo.filmGallery.FilmGallery
import com.example.cinema.service.adapterFilmActor.AdapterFilmHistory
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ActorFilmHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = ActorFilmHistoryFragment()
        var proffesionalKey= listOf<String>("WRITER", "OPERATOR", "EDITOR", "COMPOSER", "PRODUCER_USSR", "HIMSELF", "HERSELF", "HRONO_TITR_MALE", "HRONO_TITR_FEMALE", "TRANSLATOR", "DIRECTOR", "DESIGN", "PRODUCER", "ACTOR", "VOICE_DIRECTOR", "UNKNOWN")
        var proffesionalKeyRu= listOf<String>("Сценарист", "Оператор", "Редактор", "Композитор", "Продъюсер СССР", "Играет сам себя", "Играет сама себя", "Мужчина: главная роль", "Женщина: главная роль", "Переводчик", "Режиссер", "Художник", "Продъюсер", "Актер", "Режиссер звука", "Неизвестно")
    }

    @Inject
    lateinit var factory: ActorFilmHistoryFactory

    private val viewModel: ActorFilmHistoryViewModel by viewModels{factory}
    lateinit var binding: FragmentActorFilmHistoryBinding
    private  var idActor=0
    private var selectedChip:Int=0
    private val adapter= AdapterFilmHistory(){idFilm->clickFilm(idFilm)}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentActorFilmHistoryBinding.inflate(inflater,container,false)
        arguments?.let {
            if (idActor==0){
                idActor=it.getInt(Constance.ACTOR_ID_FOR_FILM_HISTORY)
                viewModel.getActorFilmHistory(idActor)
            }
        }
        viewModel.getWatchesFilm()

        binding.rcHistoryFilm.adapter=adapter


        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.stateActorFilmHistory.collect{
                when(it){
                    is ActorFilmHistoryState.Loading ->{
                        binding.progressLoad8.visibility=View.VISIBLE

                    }
                    is ActorFilmHistoryState.Success ->{
                        binding.progressLoad8.visibility=View.GONE
                        selectedChip= proffesionalKey.indexOf(it.filterFilms[0].first)
                        viewModel.filterEnabled.value=proffesionalKey[selectedChip]
                        binding.actorNameHistoryFilm.text=it.nameActorWorker
                        it.filterFilms.forEach{pair ->
                            Log.d("ActorFilmHistoryFragment", "filterFilms[0]: ${pair}")
                            setFilter(pair,it.famel)
                        }

                        adapter.updateWatchFilms(it.watchesFilms)

                        binding.filterHistoryFilm.setOnCheckedStateChangeListener { group, checkedIds ->
                            val chip=group.findViewById<Chip>(checkedIds[0])
                            when(chip.text.toString().filter { it != ' '&&it.isLetter()}){
                                proffesionalKeyRu[0]->{
                                    selectedChip=0
                                  viewModel.filterEnabled.value=proffesionalKey[0]
                                }
                                proffesionalKeyRu[1]->{
                                    selectedChip=1
                                    viewModel.filterEnabled.value=proffesionalKey[1]
                                }
                                proffesionalKeyRu[2]->{
                                    selectedChip=2
                                    viewModel.filterEnabled.value=proffesionalKey[2]
                                }
                                proffesionalKeyRu[3]->{
                                    selectedChip=3
                                    viewModel.filterEnabled.value=proffesionalKey[3]
                                }
                                proffesionalKeyRu[4]->{
                                    //
                                    selectedChip=4
                                    viewModel.filterEnabled.value=proffesionalKey[4]
                                }
                                proffesionalKeyRu[5].filter {it != ' '} ->{
                                    selectedChip=5
                                    viewModel.filterEnabled.value=proffesionalKey[5]
                                }
                                proffesionalKeyRu[6].filter {it != ' '} ->{
                                    selectedChip=6
                                    viewModel.filterEnabled.value=proffesionalKey[6]
                                }
                                proffesionalKeyRu[7].filter {it != ' '&&it !=':'}  ->{
                                    selectedChip=7
                                    viewModel.filterEnabled.value=proffesionalKey[7]
                                }
                                proffesionalKeyRu[8].filter {it != ' '&&it !=':'} ->{
                                    selectedChip=8
                                    viewModel.filterEnabled.value=proffesionalKey[8]
                                }
                                proffesionalKeyRu[9]->{
                                    selectedChip=9
                                    viewModel.filterEnabled.value=proffesionalKey[9]
                                }
                                proffesionalKeyRu[10]->{
                                    selectedChip=10
                                    viewModel.filterEnabled.value=proffesionalKey[10]
                                }
                                proffesionalKeyRu[11]->{
                                    selectedChip=11
                                    viewModel.filterEnabled.value=proffesionalKey[11]
                                }
                                proffesionalKeyRu[12]->{
                                    //
                                    selectedChip=12
                                    viewModel.filterEnabled.value=proffesionalKey[12]
                                }
                                proffesionalKeyRu[13]->{
                                    selectedChip=13
                                    viewModel.filterEnabled.value=proffesionalKey[13]
                                }
                                proffesionalKeyRu[14].filter {it != ' '} ->{
                                    selectedChip=14
                                    viewModel.filterEnabled.value=proffesionalKey[14]
                                }
                                proffesionalKeyRu[15]->{
                                    selectedChip=15
                                    viewModel.filterEnabled.value=proffesionalKey[15]
                                }
                                "Актриса"->{
                                    selectedChip=13
                                    viewModel.filterEnabled.value=proffesionalKey[13]
                                }
                            }
                        }
                    }
                    is ActorFilmHistoryState.Error ->{
                        binding.progressLoad8.visibility=View.GONE
                        val errorDialog=ErrorBottomSheet(it.error)
                        errorDialog.show(childFragmentManager,"errorDialog")
                    }
                }
            }
        }

       viewModel.films.onEach {
           Log.d("ActorFilmHistoryFragmentList", "$it")
           adapter.submitList(it)
       }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.watchsFilm.onEach {
           adapter.updateWatchFilms(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)



        return binding.root
    }

    private fun setChipGroup(name: String, isChecked: Boolean, size: Int) {
        val chip2 = layoutInflater.inflate(R.layout.single_chip, binding.filterHistoryFilm, false) as Chip
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
        binding.filterHistoryFilm.addView(chip2)
    }

    private fun setFilter(historyAllFilm:Pair<String, Int>,female:Boolean=false){
        val index=proffesionalKey.indexOf(historyAllFilm.first)
        if (index in 0..15){
            if (index==13){
                if(female){
                    setChipGroup("Актриса",selectedChip==index,historyAllFilm.second)
                }else{
                    setChipGroup(proffesionalKeyRu[index],selectedChip==index,historyAllFilm.second)
                }
             }else{
                setChipGroup(proffesionalKeyRu[index],selectedChip==index,historyAllFilm.second)
            }
        }
    }

    private fun clickFilm(idFilm: Int) {
        val bundle = Bundle()
        bundle.putInt(Constance.FILM_FILM_INFO_ID, idFilm)
        findNavController().navigate(R.id.action_actorFilmHistoryFragment_to_filmInfoFragment, bundle)
    }

}