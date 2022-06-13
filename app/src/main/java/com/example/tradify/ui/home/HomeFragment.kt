package com.example.tradify.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tradify.databinding.FragmentHomeBinding
import com.example.tradify.model.Categories
import com.example.tradify.model.Product
import com.example.tradify.ui.fooditem.FoodDetailActivity
import com.example.tradify.ui.fooditem.FoodListActivity
import com.example.tradify.ui.kategori.KategoriActivity
import com.example.tradify.ui.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        homeViewModel.getProductList()
        homeViewModel.getCategoryList()

        binding.txtNamaUser.text = auth.currentUser?.displayName.toString()
        Glide.with(this)
            .load(auth.currentUser?.photoUrl)
            .circleCrop()
            .into(binding.imgUser)


        homeViewModel.productuser.observe(viewLifecycleOwner) { produk ->
            setProduk(produk)
        }
        homeViewModel.categoryuser.observe(viewLifecycleOwner) { category ->
            setCategory(category)
        }

        binding.apply {
            btnExploreAll.setOnClickListener {
                val intent = Intent(requireActivity(), FoodListActivity::class.java)
                startActivity(intent)
            }
            btnCategoryAll.setOnClickListener {
                val intent = Intent(requireActivity(), KategoriActivity::class.java)
                startActivity(intent)
            }
        }

        return root
    }

    private fun setCategory(category: List<Categories>) {
        val adapter = CategoryAdapter(category)
        binding.rvCategories.adapter = adapter

    }

    private fun setProduk(produk: List<Product>) {
        val adapter = ExploreAdapter(produk)
        binding.rvExplore.adapter = adapter
        adapter.setOnItemClickCallback(object : ExploreAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Product) {
                val foodDetail = Intent(requireActivity(), FoodDetailActivity::class.java)
                foodDetail.putExtra(FoodDetailActivity.EXTRA_FOOD, data)
                startActivity(foodDetail)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}