package com.example.clownmania.data

data class Show(
    val eventoId: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val duracion: Int,
    val imagenUrl: String,
    val encargados: List<Encargado>
)

data class Encargado(
    val encargadoId: Int,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val email: String
)
