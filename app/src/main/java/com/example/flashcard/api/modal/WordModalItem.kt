package com.example.flashcard.api.modal

data class WordModalItem(
    val meanings: List<Meaning>,
    val phonetics: List<Phonetic>,
    val word: String
)