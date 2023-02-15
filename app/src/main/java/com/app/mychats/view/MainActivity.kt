package com.app.mychats.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.mychats.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, SignUpFragment())
            .commit()

    }
}