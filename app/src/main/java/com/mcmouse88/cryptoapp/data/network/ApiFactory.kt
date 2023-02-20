package com.mcmouse88.cryptoapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()


    /**
     * Создадим свойство [apiService], которое будет выполнять роль
     * интерфейса [ApiService], то есть [Retrofit] создасть нам реализацию
     * данного интерфейса
     */
    val apiService = retrofit.create(ApiService::class.java)
}