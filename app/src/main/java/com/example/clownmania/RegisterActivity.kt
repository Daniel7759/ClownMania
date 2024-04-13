package com.example.clownmania

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clownmania.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tiedtxtNombre.addTextChangedListener(firstNameTextWatcher)
        binding.tiedtxtApellido.addTextChangedListener(lastNameTextWatcher)
        binding.tiedtxtEmail.addTextChangedListener(emailTextWatcher)
        binding.tiedtxtPassword.addTextChangedListener(passwordTextWatcher)
        binding.tiedtxtCellphone.addTextChangedListener(phoneTextWatcher)

        binding.btnRegister.setOnClickListener {
            val firstname = binding.tiedtxtNombre.text.toString().trim()
            val lastname = binding.tiedtxtApellido.text.toString().trim()
            val email = binding.tiedtxtEmail.text.toString().trim()
            val password = binding.tiedtxtPassword.text.toString().trim()
            val phone = binding.tiedtxtCellphone.text.toString().trim()

            if (isValidEmail(email) && isValidPassword(password)) {
                registerUserInFirebase(email, password)
            } else {
                Toast.makeText(this, "Por favor, revise los campos ingresados", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUserInFirebase(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registro en Firebase exitoso
                    FancyToast.makeText(this, "Registro completado", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                    // Iniciar LoginActivity
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // Finalizar RegisterActivity
                } else {
                    // Si el registro en Firebase falla, mostrar mensaje de error
                    FancyToast.makeText(this, "Registro fallido en Firebase.", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }
    }

    private fun registerUser(email: String, password: String) {
        val firstname = binding.tiedtxtNombre.text.toString().trim()
        val lastname = binding.tiedtxtApellido.text.toString().trim()
        val phone = binding.tiedtxtCellphone.text.toString().trim()
        /*val userData = UserData(firstname, lastname, email, username, password, phone, level)*/

        // Llamar a la función para registrar en el servidor
        registerUserInFirebase(email, password)
    }

    /*private fun registerUserInServer(userData: UserData) {
        RetrofitInstance.apiService.createUser(userData)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        // Registro exitoso en el servidor
                        FancyToast.makeText(this@RegisterActivity, "1/2 registrando", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                        // Después del registro en el servidor, proceder al registro en Firebase
                        val email = userData.email
                        val password = userData.password
                        registerUserInFirebase(email, password)
                    } else {
                        // Mostrar mensaje de error si el registro en el servidor falla
                        FancyToast.makeText(
                            this@RegisterActivity,
                            "Cuenta existente o Error del servidor",
                            FancyToast.LENGTH_SHORT,
                            FancyToast.ERROR,
                            false
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    // Mostrar mensaje de error si la solicitud falla
                    FancyToast.makeText(
                        this@RegisterActivity,
                        "Error en la solicitud al servidor: ${t.message}",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            })
    }*/


    private val firstNameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val firstName = s.toString()
            val errorMessage = getFirstNameErrorMessage(firstName)
            binding.tilNombre.isErrorEnabled = errorMessage != null
            binding.tilNombre.error = errorMessage
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val lastNameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val lastName = s.toString()
            val errorMessage = getLastNameErrorMessage(lastName)
            binding.tilApellido.isErrorEnabled = errorMessage != null
            binding.tilApellido.error = errorMessage
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val emailTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = s.toString()
            val errorMessage = getEmailErrorMessage(email)
            binding.tilEmail.isErrorEnabled = errorMessage != null
            binding.tilEmail.error = errorMessage
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val password = s.toString()
            val errorMessage = getPasswordErrorMessage(password)
            binding.tilPassword.isErrorEnabled = errorMessage != null
            binding.tilPassword.error = errorMessage
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val phoneTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val phone = s.toString()
            val errorMessage = getPhoneErrorMessage(phone)
            binding.tilCellphone.isErrorEnabled = errorMessage != null
            binding.tilCellphone.error = errorMessage
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun getFirstNameErrorMessage(firstName: String): String? {
        return when {
            firstName.isBlank() -> "El campo de nombre no puede estar vacío"
            firstName.length < 2 -> "El nombre debe tener entre 2 y 40 caracteres"
            firstName.length > 40 -> "El nombre no puede tener más de 40 caracteres"
            else -> null
        }
    }

    private fun isValidFirstName(firstName: String): Boolean {
        return getFirstNameErrorMessage(firstName) == null
    }

    private fun getLastNameErrorMessage(lastName: String): String? {
        return when {
            lastName.isBlank() -> "El campo de apellido no puede estar vacío"
            lastName.length < 2 -> "El apellido debe tener entre 2 y 40 caracteres"
            lastName.length > 40 -> "El apellido no puede tener más de 40 caracteres"
            else -> null
        }
    }

    private fun isValidLastName(lastName: String): Boolean {
        return getLastNameErrorMessage(lastName) == null
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

    private fun getPasswordErrorMessage(password: String): String? {
        val hasMinimumLength = password.length >= 10
        val hasDigit = password.any { it.isDigit() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasSpecialChar = password.any { it in "@#$&+=!" }
        val hasNoWhitespace = password.none { it.isWhitespace() }


        return when {
            password.isBlank() -> "El campo de contraseña no puede estar vacío"
            !hasMinimumLength -> "La contraseña debe tener al menos 10 caracteres"
            !hasDigit -> "La contraseña debe contener al menos un dígito"
            !hasLowerCase -> "La contraseña debe contener al menos una letra minúscula"
            !hasUpperCase -> "La contraseña debe contener al menos una letra mayúscula"
            !hasSpecialChar -> "La contraseña debe contener al menos un carácter especial (@#$&+=!)"
            !hasNoWhitespace -> "La contraseña no debe contener espacios en blanco"
            else -> null
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return getPasswordErrorMessage(password) == null
    }

    private fun getPhoneErrorMessage(phone: String): String? {
        return when {
            phone.isBlank() -> "El campo de teléfono no puede estar vacío"
            !Patterns.PHONE.matcher(phone).matches() -> "Formato de número de teléfono inválido"
            phone.length != 9 -> "El número de teléfono debe tener 9 dígitos"
            else -> null
        }
    }

    private fun isValidPhone(phone: String): Boolean {
        return getPhoneErrorMessage(phone) == null
    }
}