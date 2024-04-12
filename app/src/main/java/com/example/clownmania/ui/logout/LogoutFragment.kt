package com.example.clownmania.ui.logout

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.clownmania.LoginActivity
import com.example.clownmania.R
import com.example.clownmania.databinding.FragmentLogoutBinding
import com.google.firebase.auth.FirebaseAuth

class LogoutFragment: Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLogoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(LogoutViewModel::class.java)

        binding = FragmentLogoutBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val btnSi: Button = view.findViewById(R.id.btn_si)
        btnSi.setOnClickListener {
            auth.signOut()

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        val btnNo: Button = view.findViewById(R.id.btn_no)
        btnNo.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
    }
}