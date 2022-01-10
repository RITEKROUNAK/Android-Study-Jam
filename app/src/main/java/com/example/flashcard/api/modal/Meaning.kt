package com.example.flashcard.api.modal

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)