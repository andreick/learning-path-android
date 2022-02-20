package com.andreick.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CustomFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Remote message from: ${remoteMessage.from}")
        remoteMessage.notification?.run {
            showNotification(1234, title.toString(), body.toString())
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    companion object {
        private const val TAG = "CustomFirebaseMessagingService"
    }
}