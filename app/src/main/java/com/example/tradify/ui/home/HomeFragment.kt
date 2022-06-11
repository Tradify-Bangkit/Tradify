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
import com.example.tradify.model.Product
import com.example.tradify.ui.item.FoodListActivity
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
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        binding.txtNamaUser.text = auth.currentUser?.displayName.toString()
        Glide.with(this)
            .load(auth.currentUser?.photoUrl)
            .circleCrop()
            .into(binding.imgUser)

//         var anjay = FirebaseClass().getProductList()
        homeViewModel.getProductList()
        homeViewModel.productuser.observe(viewLifecycleOwner) { produk ->
            setProduk(produk)
        }

        binding.btnExploreAll.setOnClickListener {
            val intent = Intent (requireActivity(), FoodListActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    private fun setProduk(produk: List<Product>) {
        val adapter = ExploreAdapter(produk)
        binding.rvExplore.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}