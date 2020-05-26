package com.hackathon.hikoo.eventreport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hackathon.hikoo.R

class EventReportFragment : Fragment() {
    
    companion object {
        fun newInstance() = EventReportFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

}
