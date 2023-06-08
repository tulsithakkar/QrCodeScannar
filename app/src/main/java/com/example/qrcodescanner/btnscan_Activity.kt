package com.example.qrcodescanner

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat

class btnscan_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_btnscan)
        supportActionBar?.hide()
        var user= intent.getStringExtra("user_name")

       var btn_scan:Button= findViewById(R.id.btn_scan)
        btn_scan.setOnClickListener {
            val intent= Intent(this, Scanner_Activity::class.java)
            intent.putExtra("user_name",user.toString())
            startActivity(intent)
        }
    }
}