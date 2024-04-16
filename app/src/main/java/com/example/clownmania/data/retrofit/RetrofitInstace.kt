package com.example.clownmania.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstace {

    private const val URL_BASE = "http://node179706-studyfinal.jelastic.saveincloud.net:15103/"

    val apiservice: ClownmaniaApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ClownmaniaApiService::class.java)
    }
}