package com.khansafzh.newsapp2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot.*

class ForgotActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mAuth: FirebaseAuth

    companion object {
        fun getLaunchService(from: Context) = Intent(from, ForgotActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        supportActionBar?.hide()
        btn_back_forgot.setOnClickListener(this)
        fb_forgot.setOnClickListener(this)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btn_back_forgot -> startActivity(SignInActivity.getLaunchService(this))
            R.id.fb_forgot -> forgotPassword()
        }
    }

    private fun forgotPassword() {
        val email = et_email_forgot.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, getString(R.string.error_text), Toast.LENGTH_SHORT).show()
        }else{
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Check Email to reset password", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(SignInActivity.getLaunchService(this)))
                }else{
                    Toast.makeText(this,"Failed to reset password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


