package com.example.tradify.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tradify.databinding.CardCategoryBinding
import com.example.tradify.model.Categories

class CategoryAdapter(private val listCategory: List<Categories>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    class CategoryHolder(var binding: CardCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CategoryHolder {
        val binding =
            CardCategoryBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val (id, gambar, kategori) = listCategory[position]
        Glide.with(holder.itemView.context)
            .load(gambar)
            .into(holder.binding.imgCategory)
        holder.binding.apply {
            txtCategory.text = kategori

        }
    }

    override fun getItemCount(): Int = listCategory.size
}