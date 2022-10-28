package com.example.errorboundary


import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.view.Gravity
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            // set the layout parameters of the window
//            val mParams = WindowManager.LayoutParams( // Shrink the window to wrap the content rather
//                // than filling the screen
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,  // Display it on top of other application windows
//                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,  // Don't let it grab the input focus
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,  // Make the underlying application window visible
//                // through any transparent parts
//                PixelFormat.TRANSLUCENT
//            )
//
//
//            mParams.gravity = Gravity.CENTER
//            this.window.attributes = mParams
//        }



        findViewById<Button>(R.id.make_error_button).setOnClickListener {
            makeError()
        }


        Thread.setDefaultUncaughtExceptionHandler { paramThread, paramThrowable ->
            val intent = Intent(this, ErrorActivity::class.java)
            startActivity(intent)
            killCurrentProcess() // work on my phone (if use only exitProcess I get xiomi bottomsheet)
        }


    }

    private fun killCurrentProcess() {
        Process.killProcess(Process.myPid())
        // any number
        exitProcess(0)
    }

    private fun makeError() {
        throw Error("My Error")
    }

}