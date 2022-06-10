package com.example.tradify.ui.forgetpassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tradify.R

class ForgetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_forget_password)
    }
}