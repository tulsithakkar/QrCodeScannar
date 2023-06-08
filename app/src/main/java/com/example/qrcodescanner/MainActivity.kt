package com.example.qrcodescanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        var handler = android.os.Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent= Intent(this, Login_Activity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }

}