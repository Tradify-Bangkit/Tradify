package com.example.tradify.ui.fooditem

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tradify.R
import com.example.tradify.databinding.ActivityFoodDetailBinding
import com.example.tradify.databinding.ActivityListFoodBinding
import com.example.tradify.model.Product
import com.example.tradify.ui.HomeActivity

class FoodDetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_FOOD = "extra_food"
    }

    private lateinit var binding: ActivityFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val food = intent.getParcelableExtra<Product>(EXTRA_FOOD) as Product
        Glide.with(binding.root.context)
            .load(food.gambar)
            .into(binding.imageDetailMakanan)
        binding.apply {
            tvNamaMakanan.text = food.nama_produk
            tvAsalMakanan.text = food.asal_daerah
            tvKategoriMakanan.text = food.kategori
            tvDetailMakanan.text= food.deskripsi
            btnKeluar.setOnClickListener { backToHome() }
        }
    }

    private fun backToHome() {
        val intent = Intent(this@FoodDetailActivity, HomeActivity::class.java)
        startActivity(intent)
    }
}