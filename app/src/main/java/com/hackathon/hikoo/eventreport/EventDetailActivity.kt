package com.hackathon.hikoo.eventreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.R
import com.hackathon.hikoo.model.domain.Event
import com.hackathon.hikoo.utils.DateUtils
import com.hackathon.hikoo.utils.bindView
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
import com.hackathon.hikoo.utils.imageloader.ImageLoadToolImpl
import org.koin.android.ext.android.inject

class EventDetailActivity : AppCompatActivity() {

    companion object {
        const val EVENT_DETAIL = "EVENT_DETAIL"
    }

    private val eventTitle: MaterialTextView by bindView(R.id.event_report_title)
    private val eventDate: MaterialTextView by bindView(R.id.event_report_date)
    private val eventType: MaterialTextView by bindView(R.id.event_type)
    private val eventDescription: MaterialTextView by bindView(R.id.event_description)
    private val backButton: MaterialTextView by bindView(R.id.back_button)
    private val eventImage: AppCompatImageView by bindView(R.id.event_image)

    private val imageLoadTool: ImageLoadToolImpl by inject()

    private var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        if (intent.extras != null) {
            val bundle = intent.extras
            event = bundle?.getParcelable(EVENT_DETAIL)
            setupEventDetail(event)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupEventDetail(event: Event?) {
        event?.let {

            eventTitle.text = when (it.eventType) {
                Event.EventType.WILD_ANIMAL -> "Wild Animal"
                Event.EventType.ITEM_FOUND -> "Item Found"
                Event.EventType.BLOCKED_ROUTE -> "Blocked route"
                Event.EventType.SOS -> "SOS"
                else -> "unknown"
            }
            if (event.attachments.isNotEmpty()) {
                imageLoadTool.loadImage(this, event.attachments[0], eventImage, -1)
            }
            eventDate.text = DateUtils.getFormattedTimeAndDate(it.eventTime)
            eventType.text = "Event Type : ${it.eventTypeName}"
            eventDescription.text = it.eventInfo
        }
    }
}
