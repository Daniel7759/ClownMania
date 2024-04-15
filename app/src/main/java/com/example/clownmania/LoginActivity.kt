package com.example.clownmania

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.clownmania.data.UserAuthenticate
import com.example.clownmania.data.retrofit.RetrofitInstace
import com.example.clownmania.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide() // Agrega esta línea para ocultar el Toolbar
        // Verificar si el usuario ya está autenticado
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // El usuario ya está autenticado, redirigir a HomeActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.tiedtxtEmail.addTextChangedListener(emailTextWatcher)
        binding.tiedtxtPassword.addTextChangedListener(passwordTextWatcher)

        binding.btnLogin.setOnClickListener {
            val email = binding.tiedtxtEmail.text.toString()
            val password = binding.tiedtxtPassword.text.toString()
            binding.btnLogin.startAnimation() // Iniciar la animación antes de realizar el inicio de sesión
            loginWithEmailAndPassword(email, password)

        }
        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }


    private fun loginWithEmailAndPassword(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            FancyToast.makeText(this, "Por favor, ingrese un correo electrónico y una contraseña", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
            binding.btnLogin.revertAnimation()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Autenticación exitosa
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    binding.btnLogin.revertAnimation()
                    FancyToast.makeText(this, "Sesión válida", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()

                    // Guardar email y contraseña en SharedPreferences
                    val sharedPreferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("email", email)
                    editor.putString("password", password)
                    editor.apply()

                    finish()
                } else {
                    binding.btnLogin.revertAnimation()
                    // Autenticación fallida
                    FancyToast.makeText(this, "Sesión inválida", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }
    }

    private val emailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = s.toString()
            val errorMessage = getEmailErrorMessage(email)
            binding.tilEmail.isErrorEnabled = errorMessage != null
            binding.tilEmail.error = errorMessage
        }

        override fun afterTextChanged(s: Editable?) {
            val email = s.toString()
            binding.btnLogin.isEnabled = isValidEmail(email) && isValidPassword(binding.tiedtxtPassword.text.toString())
        }
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val errorMessage = getPasswordErrorMessage(s.toString())
            binding.tilPassword.isErrorEnabled = errorMessage != null
            binding.tilPassword.error = errorMessage
        }

        override fun afterTextChanged(s: Editable?) {
            val password = s.toString()
            binding.btnLogin.isEnabled = isValidEmail(binding.tiedtxtEmail.text.toString()) && isValidPassword(password)
        }
    }

    private fun getEmailErrorMessage(email: String): String? {
        return when {
            email.isBlank() -> "El campo de correo electrónico no puede estar vacío"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Formato de correo electrónico inválido"
            email.length > 60 -> "El correo electrónico no puede tener más de 60 caracteres"
            else -> null
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return getEmailErrorMessage(email) == null
    }

    private fun isValidPassword(password: String): Boolean {
        return getPasswordErrorMessage(password) == null
    }

    private fun getPasswordErrorMessage(password: String): String? {
        val hasMinimumLength = password.length == 10
        val hasDigit = password.any { it.isDigit() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasSpecialChar = password.any { it in "@#$&+=!" }
        val hasNoWhitespace = password.none { it.isWhitespace() }

        return when {
            !hasMinimumLength -> "La contraseña debe tener al menos 10 caracteres"
            !hasDigit -> "La contraseña debe contener al menos un dígito"
            !hasLowerCase -> "La contraseña debe contener al menos una letra minúscula"
            !hasUpperCase -> "La contraseña debe contener al menos una letra mayúscula"
            !hasSpecialChar -> "La contraseña debe contener al menos un carácter especial (@#$&+=!)"
            !hasNoWhitespace -> "La contraseña no debe contener espacios en blanco"
            else -> null
        }
    }

}