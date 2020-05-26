package com.hackathon.hikoo.hikoopage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner

import com.hackathon.hikoo.R

class HikooFragment : Fragment(), HikooView {

    companion object {
        fun newInstance() = HikooFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hikoo, container, false)
    }


    override fun provideLifecycleOwner(): LifecycleOwner {
        return this
    }
}
