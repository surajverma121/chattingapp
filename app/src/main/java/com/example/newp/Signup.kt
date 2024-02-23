package com.example.newp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {
    private  lateinit var  edtEmail : EditText
    private  lateinit var  edname : EditText
    private  lateinit var  edtPassword : EditText
    private  lateinit var  btnSignup: Button
    private lateinit var  mDbRef : DatabaseReference

    private  lateinit var  mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()

       mAuth =FirebaseAuth.getInstance()
        edname = findViewById(R.id.edit_name)
        edtEmail = findViewById(R.id.edit_email)
        edtPassword = findViewById(R.id.edt_pass)
        btnSignup = findViewById(R.id.btnsingup)

        btnSignup.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val  name = edname.text.toString()

            signUp (email,password,name)
        }

    }

    private fun signUp(email: String, password: String,name:String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                 // code for jumping home activity

                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val  intent  =Intent(this ,MainActivity::class.java)
                   finish()
                    startActivity(intent)
                } else {
                  Toast.makeText(this,"some error occured", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private  fun  addUserToDatabase (name: String,email: String,uid:String){
         mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}