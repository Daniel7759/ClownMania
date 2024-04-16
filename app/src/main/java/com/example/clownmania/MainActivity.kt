package com.example.clownmania

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.clownmania.data.UserAuthenticate
import com.example.clownmania.data.retrofit.RetrofitInstace
import com.example.clownmania.databinding.ActivityMainBinding
import com.example.clownmania.ui.home.HomeFragment
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_pagossadmin,
                R.id.navigation_userregistrado
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE)
        val correo = sharedPreferences.getString("email", "")
        if (correo != null && correo.isNotEmpty()) {
            autenticarUser(correo)

        }


        val role = sharedPreferences.getString("role","USER") // replace this with actual role fetching logic

        val menuNav = navView.menu
       if (role == "ADMIN") {
            menuNav.findItem(R.id.navigation_dashboard).isVisible = false
            menuNav.findItem(R.id.navigation_notifications).isVisible = true
            menuNav.findItem(R.id.navigation_home).isVisible = true
            menuNav.findItem(R.id.navigation_userregistrado).isVisible = true
            menuNav.findItem(R.id.navigation_pagossadmin).isVisible = true
        }
        if (role == "USER") {
            menuNav.findItem(R.id.navigation_dashboard).isVisible = true
            menuNav.findItem(R.id.navigation_notifications).isVisible = true
            menuNav.findItem(R.id.navigation_home).isVisible = true
            menuNav.findItem(R.id.navigation_userregistrado).isVisible = false
            menuNav.findItem(R.id.navigation_pagossadmin).isVisible = false
        }
    }

    private fun autenticarUser(correo: String){
        lifecycleScope.launch {
            try {
                val response = RetrofitInstace.apiservice.getUsuarioCorreo(correo)
                if(response.isSuccessful){
                    val user = response.body()
                    user?.let { saveUserUtils(it) }
                }else{
                    Toast.makeText(this@MainActivity, "Error al cargar el usuario", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                android.app.AlertDialog.Builder(this@MainActivity)
                    .setMessage("Error al cargar el usuario: ${e.message}")
                    .setCancelable(true)
                    .setPositiveButton("OK"){ dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()
            }
        }
    }
    private fun saveUserUtils(user: UserAuthenticate){
        UserUtils.setUserId(user.userId)
        UserUtils.setNombre(user.nombre)
        UserUtils.setApellido(user.apellido)
        UserUtils.setCorreo(user.correo)
        UserUtils.setCelular(user.celular)
        val role = user.role.stream().findFirst().get().nombreRol
        UserUtils.setRole(role)

        // Guardar el rol en SharedPreferences
        val sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("role", role)
        editor.apply()
    }
}