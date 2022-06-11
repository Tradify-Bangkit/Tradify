package com.example.tradify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Product(
    var product_id: String="",
    val nama_produk: String="",
    val kategori: String="",
    val gambar: String="",
    val deskripsi: String="",
    val asal_daerah: String=""
) : Parcelable
