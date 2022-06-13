package com.example.tradify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Categories(
    var id: String="",
    val gambar: String="",
    val kategori: String=""
) : Parcelable