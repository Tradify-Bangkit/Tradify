package com.example.tradify.ui.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tradify.R

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_signup)

    }
}