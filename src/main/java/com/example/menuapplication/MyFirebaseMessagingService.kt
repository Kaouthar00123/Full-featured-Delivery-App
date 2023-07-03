package com.example.menuapplication


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.test.core.app.ApplicationProvider
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService(){

    // Override onNewToken to get new token
    companion object {
        private const val TAG = "MyFirebaseMessaging"
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        println("token")
        if (remoteMessage.getNotification() != null) {
            remoteMessage.getNotification()!!.getTitle()?.let {
                remoteMessage.getNotification()!!.getBody()?.let { it1 ->
                    println("dans remoteMessage.getNotification()")
                    println("it1")
                    println(it1)

                    showNotification(
                        it,
                        it1
                    )
                }
            }
        }
    }

    // Method to get the custom Design for the display of
    // notification.
    private fun getCustomDesign(
        title: String,
        message: String
    ): RemoteViews {
        val remoteViews = RemoteViews(
            ApplicationProvider.getApplicationContext<Context>().getPackageName(),
            R.layout.notification
        )
        remoteViews.setTextViewText(R.id.title, title)
        remoteViews.setTextViewText(R.id.notification_message, message)
        remoteViews.setImageViewResource(
            R.id.notification_icon,
            R.drawable.comment
        )
        return remoteViews
    }

    // Method to display the notifications
    fun showNotification(
        title: String,
        message: String
    ) {
        // Pass the intent to switch to the MainActivity
        val intent = Intent(this, RegistrationActivity::class.java)
        val channel_id = "notification_channel"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            ApplicationProvider.getApplicationContext<Context>(),
            channel_id
        )
            .setSmallIcon(R.drawable.comment)
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(
            getCustomDesign(title, message)
        )

        val notificationManager = getSystemService( Context.NOTIFICATION_SERVICE) as? NotificationManager
        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                channel_id, "web_app",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager!!.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager!!.notify(0, builder.build())
    }

}