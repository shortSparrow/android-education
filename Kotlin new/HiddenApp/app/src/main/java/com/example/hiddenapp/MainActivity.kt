package com.example.hiddenapp

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MyMainActivity"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Settings.System.canWrite(this)
        findViewById<Button>(R.id.hidden_app_button).setOnClickListener { makeAppIconHidden() }
    }

    private fun makeAppIconHidden() {
        val p = packageManager
        val componentName = ComponentName(
//            this,
//            MainActivity::class.java
            "com.example.hiddenapp",
            "com.example.hiddenapp.MainActivity"
        )

//        p.setApplicationEnabledSetting(
////            "com.example.hiddenapp",
//            "com.google.android.youtube",
//            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//            PackageManager.DONT_KILL_APP
//        )


        val componentName2 = ComponentName(
//            "com.google.android.youtube",
//            "com.google.android.apps.youtube.app.YouTubeTikTokRoot_Application",
            "com.example.timeline",
            "com.example.timeline.MainActivity",
//            "com.google.android.youtube.MainActivity",
        )

//        p.setComponentEnabledSetting(
//            componentName2,
//            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//            PackageManager.DONT_KILL_APP
//        )


        p.setApplicationEnabledSetting(
            "com.example.timeline",
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )


//        p.setComponentEnabledSetting(
//            componentName,
//            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//            PackageManager.DONT_KILL_APP
//        )
    }

    private fun makeAppIconVisible() {
        val p = packageManager
        val componentName = ComponentName(this, MainActivity::class.java)
        p.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun launchAnotherApp() {
        val launchIntent = packageManager.getLaunchIntentForPackage("com.google.android.youtube")
        launchIntent?.let { startActivity(it) }
    }

    private fun getAllInstalledApp() {
        val pm = packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        Log.d(TAG, "Installed package :" + packages.size) // 68 for base variant // 94 after get_all_query
        val dd = packages.find { it.packageName == "com.google.android.youtube" }
        val ss = dd?.packageName
        for (packageInfo in packages) {
            if (packageInfo.packageName == "com.google.android.youtube") {
                Log.d(TAG, "Installed package :" + packageInfo.packageName)
                Log.d(TAG, "name :" + packageInfo.name)
            }

//                Log.d(TAG, "Source dir : " + packageInfo.sourceDir)
//                Log.d(
//                    TAG,
//                    "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName)
//                )
        }
//
    }
}