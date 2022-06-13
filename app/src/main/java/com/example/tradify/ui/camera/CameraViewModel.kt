package com.example.tradify.ui.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tradify.model.Product
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CameraViewModel : ViewModel() {

    private val _productuser = MutableLiveData<List<Product>>()
    val productuser: LiveData<List<Product>> = _productuser

    val db = Firebase.firestore

    fun getProduct() {
        db.collection("products")
            .whereEqualTo("nama_produk", "Pempek")
            .get()
            .addOnSuccessListener { documents ->
                val productsList: ArrayList<Product> = ArrayList()
                for (i in documents.documents) {
                    val product = i.toObject(Product::class.java)
                    product!!.product_id = i.id
                    productsList.add(product)
                Log.e("Detail Produk", "${documents.documents}")
                }
                _productuser.value = productsList
            }
            .addOnFailureListener { e ->
                Log.e("Get Product List", "Error while getting product list.", e)
            }
    }
}