package com.example.qrcodescanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Login_Activity : AppCompatActivity() {
    private lateinit var firebaseauth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        firebaseauth=FirebaseAuth.getInstance()
        var login_Email: TextInputEditText = findViewById(R.id.login_Email)
        var login_password: TextInputEditText = findViewById(R.id.login_password)
        var new_Account: TextView = findViewById(R.id.txt_newAccount)
        var btn_login: Button =findViewById(R.id.btn_login)
        new_Account.setOnClickListener {
            val intent= Intent(this, Signup_Activity::class.java)
            startActivity(intent)
        }
        btn_login.setOnClickListener {


            val email = login_Email.text.toString()
            val pass = login_password.text.toString()
            if (email.isEmpty() && pass.isEmpty()) {
                Toast.makeText(this, "Please Enter Credential", Toast.LENGTH_LONG).show()
            } else {
                firebaseauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent= Intent(this, btnscan_Activity::class.java)
                        intent.putExtra("user_name",login_Email.toString())
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }
}