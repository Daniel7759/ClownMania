package com.example.clownmania.data

data class UserAuthenticate(
    val userId: Int,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val celular: String,
    val role: List<RoleAuthenticate>
)

data class RoleAuthenticate(
    val roleId: Int,
    val nombreRol: String
)