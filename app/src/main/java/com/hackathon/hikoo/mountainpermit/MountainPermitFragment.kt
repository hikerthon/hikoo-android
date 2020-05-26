package com.hackathon.hikoo.mountainpermit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hackathon.hikoo.R

class MountainPermitFragment : Fragment() {

    companion object {
        fun newInstance() = MountainPermitFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mountain_permit, container, false)
    }

}
