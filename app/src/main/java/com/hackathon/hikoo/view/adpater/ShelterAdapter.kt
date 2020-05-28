package com.hackathon.hikoo.view.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.R
import com.hackathon.hikoo.model.domain.Shelter
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

class ShelterAdapter(
    private val callback: ShelterItemCallback,
    private val imageLoader: ImageLoadTool
) : RecyclerView.Adapter<ShelterAdapter.ShelterItemViewHolder>() {

    private val item = mutableListOf<Shelter>()

    fun addShelterItem(items: List<Shelter>) {
        item.clear()
        item.addAll(items)
        item.removeAt(4)
        item.removeAt(3)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelterItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shelter_list_item, parent, false)
        return ShelterItemViewHolder(view, callback)
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: ShelterItemViewHolder, position: Int) {
        val shelterItem = item[position]
        val context = holder.itemView.context

        if (shelterItem.shelterName == "My Location") {
            imageLoader.loadImage(context, "", holder.shelterIcon, R.drawable.ic_current_location)
        } else {
            imageLoader.loadImage(context, "", holder.shelterIcon, R.drawable.ic_shelter_location)
        }
        holder.shelterName.text = shelterItem.shelterName
        holder.shelterLatLng.text = "${shelterItem.latpt}, ${shelterItem.lngpt}"
    }

    inner class ShelterItemViewHolder(itemView: View, callback: ShelterItemCallback?) : RecyclerView.ViewHolder(itemView) {
        private val shelterContent: View = itemView.findViewById(R.id.shelter_layout)
        internal val shelterIcon: AppCompatImageView = itemView.findViewById(R.id.shelter_icon)
        internal val shelterName: MaterialTextView = itemView.findViewById(R.id.shelter_name)
        internal val shelterLatLng: MaterialTextView = itemView.findViewById(R.id.shelter_latlng)

        init {
            shelterContent.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback?.onShelterItemClicked(item[adapterPosition])
                }
            }
        }
    }

    interface ShelterItemCallback {
        fun onShelterItemClicked(shelter: Shelter)
    }
}