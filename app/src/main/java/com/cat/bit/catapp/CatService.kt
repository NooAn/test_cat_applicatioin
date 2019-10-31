package com.cat.bit.catapp

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class CatService : IntentService("CatService") {

    private var isInterrupted = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("CUSTOM_SERVICE", "Started...")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        try {
            while (!isInterrupted) {
                intent?.let {
                    it.action = FILTER_ACTION_KEY
                    LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
                    Log.d("CUSTOM_SERVICE", "Refreshed.")
                    Thread.sleep(REFRESH_TIME_SECONDS * 1000L)
                }
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CUSTOM_SERVICE", "Destroyed...")
        isInterrupted = true
    }
}
