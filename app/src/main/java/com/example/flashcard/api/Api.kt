package com.example.flashcard

import com.example.flashcard.api.modal.WordModal
import retrofit2.http.GET
import retrofit2.http.Path

interface WallpaperApi {
    @GET("https://api.dictionaryapi.dev/api/v2/entries/en/{word}")
    suspend fun meaning(@Path("word") word: String): WordModal
}