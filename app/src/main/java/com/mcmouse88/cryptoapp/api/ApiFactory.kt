package com.mcmouse88.cryptoapp.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"
    const val BASE_IMAGE_URL = "https://cryptocompare.com/"


    /**
     * Класс [ApiFactory] содержит в себе ссылку на [Retrofit]
     * ниже создадим объект [retrofit], соберем его, в метод
     * [addConverterFactory] нужно добавить [GsonConverter],
     * затем вызвать адаптор у факторы методом [addCallAdapterFactory]
     * и передать туда объект [RxJava2], указать базовый url, к которому будет обращаться
     * [Retrofit]
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()


    /**
     * Создадим свойство [apiService], которое будет выполнять роль
     * интерфейса [ApiService], то есть [Retrofit] создасть нам реализацию
     * данного интерфейса
     */
    val apiService = retrofit.create(ApiService::class.java)
}