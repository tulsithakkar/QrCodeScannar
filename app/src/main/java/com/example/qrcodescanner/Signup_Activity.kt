package com.example.qrcodescanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class Signup_Activity : AppCompatActivity() {
    private lateinit var firebaseauth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()
        firebaseauth=FirebaseAuth.getInstance()
        var old_Account: TextView = findViewById(R.id.txt_MovetoLogin)
        var signup_Email: TextInputEditText = findViewById(R.id.Signup_Email)
        var signup_pass: TextInputEditText = findViewById(R.id.signup_password)
        var signup_cpass: TextInputEditText = findViewById(R.id.signup_cpassword)
        var btn_signup: Button = findViewById(R.id.btn_signup)
        old_Account.setOnClickListener {
            val intent= Intent(this, Login_Activity::class.java)
            startActivity(intent)
        }
        btn_signup.setOnClickListener {
            val Email= signup_Email.text.toString()
            val pass= signup_pass.text.toString()
            val cpass= signup_cpass.text.toString()

            if (Email.isNotEmpty() && pass.isNotEmpty() && cpass.isNotEmpty())
            {
                if (pass==cpass)
                {
                    firebaseauth.createUserWithEmailAndPassword(Email,pass).addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            val intent= Intent(this, Login_Activity::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else
                {
                    Toast.makeText(this,"password not matched!!", Toast.LENGTH_LONG).show()
                }

            }

        }
        }

}