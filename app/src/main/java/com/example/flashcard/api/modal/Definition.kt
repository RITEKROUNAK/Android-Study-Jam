package com.example.flashcard.api.modal

data class Definition(
    val definition: String,
    val example: String,
    val synonyms: List<String>
)