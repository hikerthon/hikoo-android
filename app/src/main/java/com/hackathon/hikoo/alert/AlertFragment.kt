package com.hackathon.hikoo.alert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hackathon.hikoo.R

class AlertFragment : Fragment() {

    companion object {
        fun newInstance() = AlertFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

}
