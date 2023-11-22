package com.example.myapplication

import android.net.Uri

data class Card (
    val id: Int,
    val question: String,
    val example:String,
    val answer: String,
    val translate: String,
    val image: Uri?=null,
)

