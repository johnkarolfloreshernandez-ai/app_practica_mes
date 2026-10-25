package com.example.app_practica_mes.Modelo

import com.example.app_practica_mes.interfaceApi.ApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


object RetrofitCliente {
    private const val  BASE_URL ="http://10.248.187.59/practicaMes/" //aqui va la url del api

    private val jsonConfig= Json{
        ignoreUnknownKeys=true
        coerceInputValues=true
        isLenient=true
    }
    val apiService: ApiService by lazy {
        val contentType="application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConfig.asConverterFactory(contentType))
            .build()
            .create(ApiService::class.java)

    }
}