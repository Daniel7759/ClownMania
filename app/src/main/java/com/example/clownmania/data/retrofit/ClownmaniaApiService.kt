package com.example.clownmania.data.retrofit

import com.example.clownmania.data.CreateUsuario
import com.example.clownmania.data.Reserva
import com.example.clownmania.data.Show
import com.example.clownmania.data.UserAuthenticate
import com.example.clownmania.data.UserResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ClownmaniaApiService {

    @GET("eventos")
    suspend fun getShows(): List<Show>

    @POST("reservas")
    fun postReserva(@Body reserva: Reserva): Call<String>

    @GET("reservas/{userId}")
    suspend fun getReservasByUserId(@Path("userId") userId: Int): List<ReservasGet>


    @GET("usuarios/correo")
    suspend fun getUsuarioCorreo(@Query("correo") correo: String): Response<UserAuthenticate>

    @GET("usuarios")
    suspend fun getUsuarios(): List<UserAuthenticate>

    @POST("usuarios")
    fun postUsuario(@Body usuario: CreateUsuario): Call<UserResponse>

}