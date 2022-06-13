package com.example.tradify.ui.kategori

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tradify.databinding.ActivityKategoriBinding
import com.example.tradify.model.Categories
import com.example.tradify.ui.home.CategoryAdapter
import com.example.tradify.ui.ui.home.HomeViewModel

class KategoriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKategoriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityKategoriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.getCategoryList()
        homeViewModel.categoryuser.observe(this) { category ->
            setCategory(category)
        }
    }
    private fun setCategory(category: List<Categories>) {
        val adapter = CategoryAdapter(category)
        binding.rvCategories.adapter = adapter

    }

}