package com.example.tradify.ui.kategori

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tradify.databinding.ActivityKategoriBinding

class KategoriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKategoriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKategoriBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}