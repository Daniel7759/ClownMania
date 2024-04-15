package com.example.clownmania.data

import java.time.LocalDate
import java.time.LocalTime


data class Reserva(
    val ciudad: String,
    val fecha: String,
    val hora: String,
    val ubicacion: String,
    val observacion: String,
    val evento: Evento,
    val usuario: Usuario

)

data class Evento(
    val eventoId: Int
)

data class Usuario(
    val userId: Int
)
