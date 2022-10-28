package com.senya.pushnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.senya.pushnotification.MyWorker.Companion.WORK_NAME

class GcmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        //do your stuff in the JobIntentService class

        context?.let {
            val bundle = intent!!.extras
            if (bundle != null) {
                for (key in bundle.keySet()) {
                    Log.e(
                        "MyFirebaseMsgService",
                        key + " : " + if (bundle[key] != null) bundle[key] else "NULL"
                    )
                }
            }
            Log.d(
                "MyFirebaseMsgService",
                "message one: ${intent.getStringExtra("gcm.notification.body").toString()}"
            )

            val workManager = WorkManager.getInstance(context)
            workManager.enqueueUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.APPEND, // Що робити, якщо worker з таким імям вже існує
                MyWorker.makeRequest(
                    intent.getStringExtra("gcm.notification.body").toString()
                )
            )
        }

    }
}