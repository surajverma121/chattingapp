package com.example.newp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private  lateinit var  edtEmail :EditText
    private  lateinit var  edtPassword :EditText
    private  lateinit var  btnLogin :Button
    private  lateinit var  btnSignup:  Button

    private  lateinit var  mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()


        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_login)
        edtEmail = findViewById(R.id.edit_email)
        edtPassword = findViewById(R.id.edt_pass)
        btnLogin = findViewById(R.id.btnlogin)
        btnSignup = findViewById(R.id.btnsingup)

        btnSignup.setOnClickListener {
            val intent = Intent(this , Signup::class.java)
            startActivity(intent)
        }
btnLogin.setOnClickListener {
    val email = edtEmail.text.toString()
    val pass = edtPassword.text.toString()

    login(email,pass)
}

    }

    private  fun login(email :String ,passs:String){
        mAuth.signInWithEmailAndPassword(email, passs)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val  intent  =Intent(this ,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                Toast.makeText(this,"user does not exist",Toast.LENGTH_SHORT).show()
                }
            }
    }

}