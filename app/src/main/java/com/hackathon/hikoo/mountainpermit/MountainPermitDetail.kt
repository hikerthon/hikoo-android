package com.hackathon.hikoo.mountainpermit

import android.os.Bundle
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.BaseActivity
import com.hackathon.hikoo.R
import com.hackathon.hikoo.model.domain.MountainPermit
import com.hackathon.hikoo.utils.DateUtils
import com.hackathon.hikoo.utils.bindView

class MountainPermitDetail : BaseActivity() {

    companion object {
        const val MOUNTAIN_PERMIT = "MOUNTAIN_PERMIT"
    }

    private val backButton: MaterialTextView by bindView(R.id.back_button)
    private val mountainName: MaterialTextView by bindView(R.id.mountain_name)
    private val dateOfApplication: MaterialTextView by bindView(R.id.date_of_application)
    private val applicationStatus: MaterialTextView by bindView(R.id.application_status)
    private val periodOfStay: MaterialTextView by bindView(R.id.period_of_stay)
    private val destination: MaterialTextView by bindView(R.id.description)
    private val routeMap: MaterialTextView by bindView(R.id.route_map)
    private val plan: MaterialTextView by bindView(R.id.plan)
    private val roster: MaterialTextView by bindView(R.id.roster)

    private var mountainPermit: MountainPermit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mountain_permit_detail)

        if (intent.extras != null) {
            val bundle = intent.extras
            mountainPermit = bundle?.getParcelable(MOUNTAIN_PERMIT)
            setupMountainPermitDetail(mountainPermit)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupMountainPermitDetail(mountainPermit: MountainPermit?) {
        mountainPermit?.let { permit ->
            mountainName.text = permit.permitName.split(" ")[0]
            applicationStatus.text = permit.permitAccepted


            if (permit.acceptedTime == 0L) {
                dateOfApplication.text = "Not Accepted"
                periodOfStay.text = "Not Accepted"
            } else {
                dateOfApplication.text = DateUtils.getFormattedNormal(permit.acceptedTime)
                periodOfStay.text = DateUtils.getRangeDate(permit.hikeStart, permit.hikeEnd)
            }

            if (permit.trails.isEmpty()) {
                routeMap.text = "No route map"
            } else {
                val routeStr = mutableListOf<String>()
                permit.trails.forEach {
                    routeStr.add(" ${it.id}: ${it.name}")
                }
                routeMap.text = routeStr.joinToString("\n")
            }

            if (permit.memo.isEmpty()) {
                plan.text = "No plan"
            } else {
                plan.text = permit.memo
            }
        }
    }
}
