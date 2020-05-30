package com.hackathon.hikoo.alert

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.textview.MaterialTextView
import com.hackathon.hikoo.BaseActivity
import com.hackathon.hikoo.R
import com.hackathon.hikoo.model.domain.Alert
import com.hackathon.hikoo.utils.DateUtils
import com.hackathon.hikoo.utils.bindView

class AlertDetailActivity : BaseActivity() {

    companion object {
        const val ALERT_DETAIL = "ALERT_DETAIL"
    }

    private val backButton: MaterialTextView by bindView(R.id.back_button)
    private val alertDate: MaterialTextView by bindView(R.id.alert_date)
    private val alertTitle: MaterialTextView by bindView(R.id.alert_title)
    private val alertLevel: MaterialTextView by bindView(R.id.alert_level)
    private val alertDescription: MaterialTextView by bindView(R.id.alert_description)

    private var alert: Alert? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_detail)

        if (intent.extras != null) {
            val bundle = intent.extras
            alert = bundle?.getParcelable(ALERT_DETAIL)
            setupAlertDetail(alert)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupAlertDetail(alert: Alert?) {
        alert?.let {
            alertDate.text = DateUtils.getFormattedTimeAndDate(it.eventTime)
            alertTitle.text = it.eventInfo
            when (it.alertLevelType) {
                Alert.AlertLevel.INFORMATION -> {
                    alertLevel.text = "Severe Level : Information"
                    alertLevel.setTextColor(ContextCompat.getColor(this, R.color.teal_green))
                }
                Alert.AlertLevel.CAUTION -> {
                    alertLevel.text = "Severe Level : Caution"
                    alertLevel.setTextColor(ContextCompat.getColor(this, R.color.teal_green))
                }
                Alert.AlertLevel.DANGER -> {
                    alertLevel.text = "Severe Level : Danger"
                    alertLevel.setTextColor(ContextCompat.getColor(this, R.color.free_speech_red))
                }
                else ->{}
            }
            alertDescription.text = it.eventInfo
        }
    }
}
