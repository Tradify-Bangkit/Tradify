package com.example.tradify.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tradify.databinding.ItemRowFoodlistBinding
import com.example.tradify.model.Product

class FoodAdapter(private val listProduct: List<Product>) :
    RecyclerView.Adapter<FoodAdapter.FoodHolder>() {

    class FoodHolder(var binding: ItemRowFoodlistBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FoodHolder {
        val binding =
            ItemRowFoodlistBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return FoodHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        val (product_id, nama_produk, kategori, gambar, deskripsi, asal_daerah) = listProduct[position]
        Glide.with(holder.itemView.context)
            .load(gambar)
            .into(holder.binding.imgItemFood)
        holder.binding.apply {
            tvItemFood.text = nama_produk
            tvFoodSummary.text = deskripsi

        }
    }

    override fun getItemCount(): Int = listProduct.size
}