package com.senya.pushnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.Constants.MessagePayloadKeys.SENDER_ID
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.senya.pushnotification.ui.theme.PushNotificationTheme


const val MY_PREFERENCES = "my_preferences"
const val TEST_PREF = "test_pref"

class MainActivity : ComponentActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "test_channel",
                "Channel human readable title",
                NotificationManager.IMPORTANCE_NONE
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            // Log and toast
            Log.d(TAG, token)
        })


        setContent {
            PushNotificationTheme {
                val context = LocalContext.current

                var message by remember {
                    mutableStateOf("")
                }

                LaunchedEffect(Unit) {
                    val sharedPref = context.applicationContext.getSharedPreferences(
                        MY_PREFERENCES,
                        Context.MODE_PRIVATE
                    )
                    message = sharedPref.getString(TEST_PREF, "empty").toString()
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column() {
                        Greeting(message)

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = name)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PushNotificationTheme {
        Greeting("Android")
    }
}