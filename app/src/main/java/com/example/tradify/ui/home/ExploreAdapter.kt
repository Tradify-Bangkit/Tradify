package com.example.tradify.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tradify.databinding.CardProductExploreBinding
import com.example.tradify.model.Product

class ExploreAdapter(private val listProduct: List<Product>) :
    RecyclerView.Adapter<ExploreAdapter.ExploreHolder>() {

    class ExploreHolder(var binding: CardProductExploreBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ExploreHolder {
        val binding =
            CardProductExploreBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ExploreHolder(binding)
    }

    override fun onBindViewHolder(holder: ExploreHolder, position: Int) {
        val (product_id, nama_produk, kategori, gambar, deskripsi, asal_daerah) = listProduct[position]
        Glide.with(holder.itemView.context)
            .load(gambar)
            .into(holder.binding.imgProduct)
        holder.binding.apply {
            namaProduk.text = nama_produk
            kategoriProduk.text = kategori

        }
    }

    override fun getItemCount(): Int = listProduct.size
}