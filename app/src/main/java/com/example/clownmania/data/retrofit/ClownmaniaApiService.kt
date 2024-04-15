package com.example.clownmania.data.retrofit

import com.example.clownmania.data.Reserva
import com.example.clownmania.data.Show
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ClownmaniaApiService {

    @GET("eventos")
    suspend fun getShows(): List<Show>

    @POST("reservas")
    fun postReserva(@Body reserva: Reserva): Call<String>

}