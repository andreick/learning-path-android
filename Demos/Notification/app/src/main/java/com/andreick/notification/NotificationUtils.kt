package com.andreick.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat

fun Context.showNotification(notificationId: Int, title: String, body: String) {
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel =
            NotificationChannel("$notificationId", title, NotificationManager.IMPORTANCE_HIGH).apply {
                lightColor = Color.BLUE
                enableVibration(true)
        }
        notificationManager.createNotificationChannel(channel)
    }
    val intent = Intent(this, MainActivity::class.java)
        //.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    val pendingIntent = PendingIntent.getActivity(
        this,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(this, "$notificationId")
        .setSmallIcon(R.drawable.ic_baseline_refresh_24)
        .setContentTitle(title)
        .setContentText(body)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
    notificationManager.notify(notificationId, builder.build())
}