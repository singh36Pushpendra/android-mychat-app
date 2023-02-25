package com.app.mychats.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.mychats.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {

                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }

        }, 4000)
    }
}