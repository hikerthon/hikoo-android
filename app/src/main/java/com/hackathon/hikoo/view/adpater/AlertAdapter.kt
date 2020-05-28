package com.hackathon.hikoo.view.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.R
import com.hackathon.hikoo.model.domain.Alert
import com.hackathon.hikoo.utils.DateUtils

class AlertAdapter(
    private val callback: AlertItemCallback
) : RecyclerView.Adapter<AlertAdapter.AlertItemViewHolder>() {

    private val item = mutableListOf<Alert>()

    fun addAlertItem(items: List<Alert>) {
        item.clear()
        item.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alert_item, parent, false)
        return AlertItemViewHolder(view, callback)
    }

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: AlertItemViewHolder, position: Int) {
        val alertItem = item[position]

        holder.alertDate.text = DateUtils.getFormattedTimeAndDate(alertItem.eventTime)
        holder.alertTitle.text = alertItem.eventInfo

        setAlertLevel(holder, alertItem)
    }

    private fun setAlertLevel(holder: AlertItemViewHolder, alertItem: Alert) {
        val context = holder.itemView.context
        when (alertItem.alertLevelType) {
            Alert.AlertLevel.INFORMATION -> {
                holder.alertLevel.text = "Severe Level : Information"
                holder.alertLevel.setTextColor(ContextCompat.getColor(context, R.color.teal_green))
            }
            Alert.AlertLevel.CAUTION -> {
                holder.alertLevel.text = "Severe Level : Caution"
                holder.alertLevel.setTextColor(ContextCompat.getColor(context, R.color.teal_green))
            }
            Alert.AlertLevel.DANGER -> {
                holder.alertLevel.text = "Severe Level : Danger"
                holder.alertLevel.setTextColor(ContextCompat.getColor(context, R.color.free_speech_red))
            }
            else ->{}
        }
    }

    inner class AlertItemViewHolder(itemView: View, callback: AlertItemCallback?) : RecyclerView.ViewHolder(itemView) {
        private val alertContent: View = itemView.findViewById(R.id.alert_content)
        internal val alertDate: MaterialTextView = itemView.findViewById(R.id.alert_date)
        internal val alertTitle: MaterialTextView = itemView.findViewById(R.id.alert_title)
        internal val alertLevel: MaterialTextView = itemView.findViewById(R.id.alert_level)

        init {
            alertContent.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback?.onAlertItemClicked(item[adapterPosition])
                }
            }
        }
    }

    interface AlertItemCallback {
        fun onAlertItemClicked(alert: Alert)
    }
}