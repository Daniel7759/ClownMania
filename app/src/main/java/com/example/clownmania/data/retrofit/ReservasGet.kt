package com.example.clownmania.data.retrofit

import com.example.clownmania.data.Evento
import com.example.clownmania.data.Show
import com.example.clownmania.data.UserAuthenticate
import com.example.clownmania.data.Usuario

data class ReservasGet(
    val reservaId: Int,
    val ciudad: String,
    val fecha: String,
    val hora: String,
    val ubicacion: String,
    val observacion: String,
    val evento: Show,
    val usuario: UserAuthenticate

)

