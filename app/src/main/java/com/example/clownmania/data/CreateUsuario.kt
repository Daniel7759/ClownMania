package com.example.clownmania.data

data class CreateUsuario(
    val nombre: String,
    val apellido: String,
    val correo: String,
    val password: String,
    val celular: String,
)

data class UserResponse(
    val success: Boolean,
    val message: String,
)
