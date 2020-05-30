package com.hackathon.hikoo.eventreport

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.hackathon.hikoo.R
import com.hackathon.hikoo.eventreport.newevent.NewEventActivity
import com.hackathon.hikoo.maincontainer.MainActivity
import com.hackathon.hikoo.model.domain.Event
import com.hackathon.hikoo.utils.imageloader.ImageLoadTool
import com.hackathon.hikoo.view.ListDivider
import com.hackathon.hikoo.view.adpater.EventAdapter
import org.koin.android.ext.android.inject

class EventReportFragment : Fragment(), IEventReport, EventAdapter.EventItemCallback {
    
    companion object {
        fun newInstance() = EventReportFragment()
    }

    private lateinit var fabAdd: FloatingActionButton
    private lateinit var eventList: RecyclerView

    private val presenter: EventReportPresenter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        (activity as? MainActivity)?.setActionBarTitle(R.string.event_report)
        retrieveViews(view)
        addListener()
    }

    override fun onResume() {
        super.onResume()
        presenter.fetchEvent()
    }

    private fun retrieveViews(view: View) {
        eventList = view.findViewById(R.id.event_list)
        fabAdd = view.findViewById(R.id.fab_add)
    }

    private fun addListener() {
        fabAdd.setOnClickListener {
            val intent = Intent(context,  NewEventActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onEventItemClicked(event: Event) {
        val context = context ?: return
        val intent = Intent(context, EventDetailActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(EventDetailActivity.EVENT_DETAIL, event)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun setupEventList(list: List<Event>, imageLoadTool: ImageLoadTool) {
        val eventAdapter = EventAdapter(this, imageLoadTool)
        val listDivider = ListDivider(eventList.context, ListDivider.VERTICAL_LIST, false, true)
        eventList.let {
            it.setHasFixedSize(true)
            it.adapter = eventAdapter
            it.addItemDecoration(listDivider)
        }
        eventAdapter.addEventItem(list)
    }

    override fun provideLifecycleOwner(): LifecycleOwner {
        return this
    }
}
