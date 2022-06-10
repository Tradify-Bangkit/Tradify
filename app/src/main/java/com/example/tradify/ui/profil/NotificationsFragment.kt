package com.example.tradify.ui.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tradify.databinding.FragmentNotificationsBinding
import com.example.tradify.ui.signin.SigninActivity
import com.example.tradify.ui.ui.notifications.NotificationsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class NotificationsFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[NotificationsViewModel::class.java]

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = Firebase.auth
        binding.apply {
            btnLogout.setOnClickListener {
                signOut()
            }

            txtNamaUser.text = auth.currentUser?.displayName.toString()
            txtEmailUser.text = auth.currentUser?.email.toString()
        }

        Glide.with(this)
            .load(auth.currentUser?.photoUrl)
            .circleCrop()
            .into(binding.imgUser)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(activity, SigninActivity::class.java))
        activity?.finish()
    }
}