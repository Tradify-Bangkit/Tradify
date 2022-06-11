package com.example.tradify.ui.item

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tradify.databinding.ActivityListFoodBinding
import com.example.tradify.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FoodListActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityListFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodViewModel =
            ViewModelProvider(this).get(FoodViewModel::class.java)

        auth = Firebase.auth

        foodViewModel.getProductList()
        foodViewModel.productuser.observe(this) { produk ->
            setProduk(produk)
        }
    }

    private fun setProduk(produk: List<Product>) {
        val adapter = FoodAdapter(produk)
        binding.rvFood.adapter= adapter
    }

}