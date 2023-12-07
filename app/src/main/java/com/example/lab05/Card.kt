package com.example.lab05

import android.net.Uri

data class Card(
    val id: Int,
    val question: String,
    val example: String,
    val answer: String,
    val translation: String,
    val imageURI: Uri? = null
)