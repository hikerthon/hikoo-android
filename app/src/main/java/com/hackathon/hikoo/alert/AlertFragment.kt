package com.hackathon.hikoo.alert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

import com.hackathon.hikoo.R
import com.hackathon.hikoo.maincontainer.MainActivity
import com.hackathon.hikoo.model.domain.Alert
import com.hackathon.hikoo.view.ListDivider
import com.hackathon.hikoo.view.adpater.AlertAdapter
import org.koin.android.ext.android.inject

class AlertFragment : Fragment(), AlertView, AlertAdapter.AlertItemCallback {

    companion object {
        fun newInstance() = AlertFragment()
    }

    private lateinit var alertList: RecyclerView

    private val presenter: AlertPresenter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        (activity as? MainActivity)?.setActionBarTitle(R.string.alert)
        alertList = view.findViewById(R.id.alert_list)

        presenter.fetchAlert()
    }

    override fun onAlertItemClicked(alert: Alert) {

    }

    override fun setupAlertList(list: List<Alert>) {
        val alertAdapter = AlertAdapter(this)
        val alertDivider = ListDivider(alertList.context, ListDivider.VERTICAL_LIST, false, true)
        alertList.let {
            it.setHasFixedSize(true)
            it.adapter = alertAdapter
            it.addItemDecoration(alertDivider)
        }
        alertAdapter.addAlertItem(list)
    }

    override fun provideLifecycleOwner(): LifecycleOwner {
        return this
    }
}
