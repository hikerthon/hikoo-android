package com.hackathon.hikoo.view.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.R
import com.hackathon.hikoo.model.domain.Event
import com.hackathon.hikoo.utils.DateUtils
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool

class EventAdapter(
    private val callback: EventItemCallback,
    private val imageLoader: ImageLoadTool
) : RecyclerView.Adapter<EventAdapter.EventItemViewHolder>() {

    private val item = mutableListOf<Event>()

    fun addEventItem(items: List<Event>) {
        item.clear()
        item.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_report_item, parent, false)
        return EventItemViewHolder(view, callback)
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        val eventItem = item[position]

        holder.eventDate.text = DateUtils.getFormattedTimeAndDate(eventItem.eventTime)
        holder.eventTitle.text = when (eventItem.eventType) {
            Event.EventType.WILD_ANIMAL -> "Wild Animal"
            Event.EventType.ITEM_FOUND -> "Item Found"
            Event.EventType.BLOCKED_ROUTE -> "Blocked route"
            Event.EventType.SOS -> "SOS"
            else -> "unknown"
        }
     }

    inner class EventItemViewHolder(itemView: View, callback: EventItemCallback?) : RecyclerView.ViewHolder(itemView) {
        private val eventContent: View = itemView.findViewById(R.id.event_content)
        internal val eventDate: MaterialTextView = itemView.findViewById(R.id.event_report_date)
        internal val eventTitle: MaterialTextView = itemView.findViewById(R.id.event_report_title)

        init {
            eventContent.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback?.onEventItemClicked(item[adapterPosition])
                }
            }
        }
    }

    interface EventItemCallback {
        fun onEventItemClicked(event: Event)
    }
}