package com.hackathon.hikoo.view.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.R
import com.hackathon.hikoo.model.domain.MountainPermit
import com.hackathon.hikoo.utils.DateUtils

class MountainPermitAdapter(
    private val callback: MountainPermitItemCallback
) : RecyclerView.Adapter<MountainPermitAdapter.MountainPermitItemViewHolder>() {

    private val item = mutableListOf<MountainPermit>()

    fun addMountainPermitItem(items: List<MountainPermit>) {
        item.clear()
        item.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountainPermitItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mountain_permit_item, parent, false)
        return MountainPermitItemViewHolder(view, callback)
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: MountainPermitItemViewHolder, position: Int) {
        val mountainPermitItem = item[position]
        val context = holder.itemView.context

        holder.permitTitle.text = mountainPermitItem.permitName
        holder.permitDate.text = DateUtils.getFormattedNormal(mountainPermitItem.hikeStart)
        holder.permitState.text = mountainPermitItem.permitAccepted

        when (mountainPermitItem.permitAccessType) {
            MountainPermit.PermitAccessType.PENDING -> {
                holder.permitState.setTextColor(ContextCompat.getColor(context, R.color.dark_orange))
            }
            MountainPermit.PermitAccessType.ACCEPTED -> {
                holder.permitState.setTextColor(ContextCompat.getColor(context, R.color.dim_gray))
            }
            MountainPermit.PermitAccessType.REJECTED -> {
                holder.permitState.setTextColor(ContextCompat.getColor(context, R.color.dim_gray))
            }
            else -> {}
        }
    }

    inner class MountainPermitItemViewHolder(itemView: View, callback: MountainPermitItemCallback?) : RecyclerView.ViewHolder(itemView) {

        private val permitContent: View = itemView.findViewById(R.id.permit_content)
        internal val permitTitle: MaterialTextView = itemView.findViewById(R.id.permit_title)
        internal val permitDate: MaterialTextView = itemView.findViewById(R.id.permit_date)
        internal val permitState: MaterialTextView = itemView.findViewById(R.id.permit_action_type)

        init {
            permitContent.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback?.onMountainPermitItemClicked(item[adapterPosition])
                }
            }
        }
    }

    interface MountainPermitItemCallback {
        fun onMountainPermitItemClicked(permit: MountainPermit)
    }
}