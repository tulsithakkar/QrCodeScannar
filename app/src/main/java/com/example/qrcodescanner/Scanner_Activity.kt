package com.example.qrcodescanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import java.nio.charset.CharsetDecoder
import java.text.DateFormat
import java.util.Calendar


class Scanner_Activity : AppCompatActivity() {


    private lateinit var codeScanner: CodeScanner
    lateinit var user:String
    private var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        supportActionBar?.hide()
         user= intent.getStringExtra("user_name").toString()
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this@Scanner_Activity,
                arrayOf(android.Manifest.permission.CAMERA), 123)
        }
        else
        {
            startscaning()
        }
    }

    private fun startscaning() {
        val scannerView = findViewById<CodeScannerView>(R.id.scanner_view)
        scannerView.visibility = View.VISIBLE
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                scannerView.visibility= View.GONE
                savedata(it.text.toString())

                val intent= Intent(this, btnscan_Activity::class.java)
                startActivity(intent)
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",

                    Toast.LENGTH_LONG).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

    }

    private fun savedata(result: String) {

            val calendar = Calendar.getInstance().time
            val dateFormat = DateFormat.getDateInstance().format(calendar)
            val timeFormat = DateFormat.getTimeInstance().format(calendar)
            val user = intent.getStringExtra("user_name").toString()
        //Toast.makeText(this,dateFormat.toString(), Toast.LENGTH_LONG).show()
        //Toast.makeText(this,timeFormat.toString(), Toast.LENGTH_LONG).show()

        db  = FirebaseFirestore.getInstance();
        val resultmap = hashMapOf<String, String >(
            "user" to user,
            "result" to result,
            "Date" to dateFormat,
             "Time" to timeFormat
        )
        val userid = FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("Qrcode_Scan_result").document(userid).set(resultmap)
            .addOnSuccessListener {
                Toast.makeText(this,"Result successfully added to database", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Result not added to database", Toast.LENGTH_LONG).show()
            }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123 && grantResults.isNotEmpty())
        {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED )
            {
                Toast.makeText(this," Permission Granted", Toast.LENGTH_LONG).show()
                startscaning()
            }
            else
            {
                Toast.makeText(this," Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }



}