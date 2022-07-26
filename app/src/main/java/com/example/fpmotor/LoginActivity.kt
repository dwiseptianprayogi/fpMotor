package com.example.fpmotor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dRef: FirebaseDatabase

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPass: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin : Button = findViewById(R.id.btnLogin)
        etEmail = findViewById(R.id.etEmailVal)
        etPass = findViewById(R.id.etPassVal)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener{
            login()
        }
    }
    override fun onStart() {
        super.onStart()

        val currentUser : FirebaseUser? = auth.currentUser
        if (currentUser!=null){
            startActivity(Intent(this, NavigationLayoutActivity::class.java))
            finish()
        }
    }

    private fun login(){
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()

        if(email.isEmpty()||pass.isEmpty()) {
            Toast.makeText(this, "Check your email or password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this, "Login Succes", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,NavigationLayoutActivity::class.java))
                finish()
            } else
                Toast.makeText(this,"Login Failed", Toast.LENGTH_SHORT).show()
        }

    }
}