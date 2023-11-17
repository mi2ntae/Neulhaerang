package com.finale.neulhaerang

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.finale.neulhaerang.data.api.ArApi
import kotlinx.coroutines.runBlocking


class DestroyService : Service() {
    private val TAG = javaClass.simpleName
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand()")

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.e(TAG, "onTaskRemoved()")
        Log.e(TAG, "This task removed from task list!")
        runBlocking {
            ArApi.instance.deleteGeo()
        }
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy()")
    }
}