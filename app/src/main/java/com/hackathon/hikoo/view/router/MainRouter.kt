package com.hackathon.hikoo.view.router

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hackathon.hikoo.R
import com.hackathon.hikoo.account.AccountFragment
import com.hackathon.hikoo.alert.AlertFragment
import com.hackathon.hikoo.eventreport.EventReportFragment
import com.hackathon.hikoo.hikoopage.HikooFragment
import com.hackathon.hikoo.mountainpermit.MountainPermitFragment

class MainRouter(
    fragmentManager: FragmentManager,
    transactionCallback: Router.OnTransactionReadyCallback
) : Router(
    fragmentManager,
    R.id.main_container,
    transactionCallback
) {

    fun navToHikooPage() {
        navTo(HikooFragment.newInstance(), "HikooFragment")
    }

    fun navToMountainPermitPage() {
        navTo(MountainPermitFragment.newInstance(), "MountainPermitFragment")
    }

    fun navToAccountPage() {
        navTo(AccountFragment.newInstance(), "AccountFragment")
    }

    fun navToEventReportPage() {
        navTo(EventReportFragment.newInstance(), "EventReportFragment")
    }

    fun navToAlertPage() {
        navTo(AlertFragment.newInstance(), "AlertFragment")
    }


    private fun navTo(fragment: Fragment, tag: String) {
        replaceFragment(fragment, tag, false)
    }
}
