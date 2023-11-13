package com.example.lab5


import android.graphics.Bitmap
import android.net.Uri

data class Card(
    val id: Int,
    val question: String,
    val example: String,
    val answer: String,
    val translation: String,
    val image:Bitmap? = null
)
