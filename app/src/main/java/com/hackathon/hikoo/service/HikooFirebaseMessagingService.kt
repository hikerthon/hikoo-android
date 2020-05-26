package com.hackathon.hikoo.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hackathon.hikoo.BuildConfig
import com.hackathon.hikoo.R
import com.hackathon.hikoo.maincontainer.MainActivity
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus
import java.util.*

class HikooFirebaseMessagingService: FirebaseMessagingService() {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "notification-channel"
        private const val NOTIFICATION_GROUP_ID = "notification-group"
        private const val HEADER_ID = 2020
    }

    private var localBroadcastManager: LocalBroadcastManager? = null
    private var notificationManager: NotificationManager? = null


    override fun onCreate() {
        super.onCreate()
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (BuildConfig.DEBUG) {
            Logger.d("From: %s", remoteMessage.from)
        }

        if (remoteMessage.notification != null) {
            val remoteNotification = remoteMessage.notification
            val title = remoteNotification?.title
            val body = remoteNotification?.body
            val titleLocalizationKey = remoteNotification?.titleLocalizationKey
            val bodyLocalizationKey = remoteNotification?.bodyLocalizationKey

            if (BuildConfig.DEBUG) {
                Logger.d("remoteNotification  = $title")
                Logger.d("remoteNotification  = $body")
                Logger.d("remoteNotification  = $titleLocalizationKey")
                Logger.d("remoteNotification  = $bodyLocalizationKey")
            }

            sendNotification(remoteNotification!!)
            vibrateDevice(1000)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                createNotificationHeader()
            }
        }
    }

    private fun createNotificationHeader() {
        val headerNotificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        headerNotificationBuilder.color = ContextCompat.getColor(this, R.color.iris_blue)
        headerNotificationBuilder.setContentTitle("Hikoo")
        headerNotificationBuilder.setGroupSummary(true)
        headerNotificationBuilder.setGroup(NOTIFICATION_GROUP_ID)

        headerNotificationBuilder.setSmallIcon(R.drawable.ic_mountain_logo)
        headerNotificationBuilder.setLargeIcon(
            BitmapFactory.decodeResource(
                resources,
                R.drawable.ic_mountain_logo
            )
        )

        notificationManager?.notify(HEADER_ID, headerNotificationBuilder.build())
    }

    private fun sendNotification(remoteNotification: RemoteMessage.Notification) {
        val notificationId = generateUniqueNotificationId()

        val title = remoteNotification.title!!
        val body = remoteNotification.body!!
        val notificationBuilder = createNotificationBuilder(title, body)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelTitle = getString(R.string.notification_channel_message_title)
            val channelDescription = getString(R.string.notification_channel_message_description)
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelTitle, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = channelDescription
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = ContextCompat.getColor(this, R.color.iris_blue)
            notificationChannel.group = NOTIFICATION_GROUP_ID
            notificationChannel.setShowBadge(true)
            notificationManager?.createNotificationChannelGroup(
                NotificationChannelGroup(NOTIFICATION_GROUP_ID, getString(R.string.notification_channel_message_title))
            )

            notificationManager?.createNotificationChannel(notificationChannel)

            notificationBuilder.setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
        }

        notificationManager?.notify(notificationId, notificationBuilder.build())
    }

    private fun vibrateDevice(time: Int) {
        val vibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        vibrator.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrate(VibrationEffect.createOneShot(time.toLong(), VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrate(time.toLong())
            }
        }
    }

    private fun generateUniqueNotificationId(): Int {
        return (Date().time / 1000L).toInt() % Int.MAX_VALUE
    }

    private fun createNotificationBuilder(title: String, body: String): NotificationCompat.Builder {
        val intent = Intent(this@HikooFirebaseMessagingService, MainActivity::class.java).run {
            setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(this@HikooFirebaseMessagingService, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this@HikooFirebaseMessagingService, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSound(defaultSoundUri)
            .setAutoCancel(true)
            .setGroup(NOTIFICATION_GROUP_ID)
            .setContentIntent(pendingIntent)

        notificationBuilder.setSmallIcon(R.drawable.ic_mountain_logo)
        return notificationBuilder
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        EventBus.getDefault().post(FcmTokenRefreshEvent(token))
    }

    //region EventBus Event
    class FcmTokenRefreshEvent(var fcmToken: String)
}