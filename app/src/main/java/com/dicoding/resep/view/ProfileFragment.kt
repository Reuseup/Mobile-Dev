package com.bangkit.resep.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bangkit.resep.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private lateinit var textViewUsername: TextView
    private lateinit var imageViewProfile: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        textViewUsername = view.findViewById(R.id.textViewUsername)
        imageViewProfile = view.findViewById(R.id.imageViewProfile)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        currentUser?.let {
            val username = getEmailUsername(currentUser.email)
            textViewUsername.text = username
        }
    }

    private fun getEmailUsername(email: String?): String {
        // Ambil bagian username dari alamat email
        return email?.substringBefore('@') ?: "Username"
    }
}