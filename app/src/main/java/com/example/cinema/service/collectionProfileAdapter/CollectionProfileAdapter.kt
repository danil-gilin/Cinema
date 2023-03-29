package com.example.cinema.service.collectionProfileAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.databinding.CollectionItemBinding
import com.example.cinema.entity.dbCinema.CollectionFilms

class CollectionProfileAdapter(val delete:(CollectionFilms)->Unit) :ListAdapter<CollectionFilms,CollectionProfileViewHolder>(CollectionProfileDiffutil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionProfileViewHolder {
        return CollectionProfileViewHolder(CollectionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CollectionProfileViewHolder, position: Int) {
        val item=getItem(position)
        with(holder.binding){
            nameCollectionProfile.text=item.title
            sizeCollectionProfile.text=item.size.toString()
            if(item.id==-1){
                iconCollectionClose.visibility= View.INVISIBLE
                iconCollectionProfile.setImageResource(R.drawable.icon_like_collection)
            }
            if (item.id==-2){
              iconCollectionClose.visibility= View.INVISIBLE
                iconCollectionProfile.setImageResource(R.drawable.icon_want_to_watch_collection)
            }

            iconCollectionClose.setOnClickListener {
                delete(item)
            }
        }
    }
}

class CollectionProfileViewHolder(val binding:CollectionItemBinding):RecyclerView.ViewHolder(binding.root)

class CollectionProfileDiffutil:DiffUtil.ItemCallback<CollectionFilms>(){
    override fun areItemsTheSame(oldItem: CollectionFilms, newItem: CollectionFilms): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: CollectionFilms, newItem: CollectionFilms): Boolean {
        return oldItem==newItem
    }

}