package com.hackathon.hikoo.eventreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.R
import com.hackathon.hikoo.model.domain.Event
import com.hackathon.hikoo.utils.DateUtils
import com.hackathon.hikoo.utils.bindView

class EventDetailActivity : AppCompatActivity() {

    companion object {
        const val EVENT_DETAIL = "EVENT_DETAIL"
    }

    private val eventTitle: MaterialTextView by bindView(R.id.event_report_title)
    private val eventDate: MaterialTextView by bindView(R.id.event_report_date)
    private val eventType: MaterialTextView by bindView(R.id.event_type)
    private val eventDescription: MaterialTextView by bindView(R.id.event_description)
    private val backButton: MaterialTextView by bindView(R.id.back_button)

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
            eventTitle.text = it.eventInfo
            eventDate.text = DateUtils.getFormattedTimeAndDate(it.eventTime)
            eventType.text = "Event Type : ${it.eventType}"
            eventDescription.text = it.eventInfo
        }
    }
}
