package com.example.flashcard.api

import com.example.flashcard.WallpaperApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private lateinit var client: WallpaperApi

    fun getApiClient(): WallpaperApi {
        if (!this::client.isInitialized) {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .build()
            client = Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(WallpaperApi::class.java)
        }
        return client
    }
}