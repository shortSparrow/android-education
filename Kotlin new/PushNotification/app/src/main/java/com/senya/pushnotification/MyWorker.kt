package com.senya.pushnotification

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.*


class MyWorker(val context: Context, private val workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    // Метод виконується не в головному потоці
    override fun doWork(): Result {

        val message = workerParameters.inputData.getString(MESSAGE)
        log("doWork: ${message}")

        val sharedPref = context.getSharedPreferences(
            MY_PREFERENCES, Context.MODE_PRIVATE
        )

        sharedPref.edit().apply {
            putString(TEST_PREF, "message: ${message}")
            apply()
        }

        context.startService(Intent(context, MyFirebaseMessagingService::class.java))

        return Result.success()
    }

    private fun log(message: String) {
        Log.d("MyFirebaseMsgService", "MyMessage: $message")
    }

    companion object {
        const val WORK_NAME = "work name"
        const val MESSAGE = "message"

        fun makeRequest(message: String): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>()
                .apply {
                    setInputData(
                        workDataOf(
                            Pair(
                                MESSAGE,
                                message
                            )
                        ) // or workDataOf(PAGE to page). Це так само сторить Pair
                    )
                }
                .build()
        }
    }

}