package com.khansafzh.newsapp2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private lateinit var gso : GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth
    companion object {
        fun getLaunchService(from: Context) = Intent(from, SignInActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        tv_forgot.setOnClickListener(this)
        tv_sign_up.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.tv_forgot -> startActivity(ForgotActivity.getLaunchService(this))
            R.id.tv_sign_up -> startActivity(
                SignUpActivity.getLaunchService(
                    this
                )
            )
            R.id.btn_login -> loginEmailPass()
        }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            startActivity(MainActivity.getLaunchService(this))
        }
    }

    private fun loginEmailPass() {
        val email = et_email_login.text.toString()
        val password = et_password_login.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Insert Complete Data", Toast.LENGTH_SHORT).show()
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(this, "Login Success",Toast.LENGTH_SHORT).show()
                startActivity(MainActivity.getLaunchService(this))
                return@addOnCompleteListener
            }else{
                Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            val progress = ProgressDialog(this, R.style.Theme_AppCompat_Light_Dialog)
            progress.hide()
            Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
        }
    }
 }
