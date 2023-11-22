package com.example.myapplication

import android.net.Uri

object Model {
    val cards1 = mutableListOf(
        Card(0,
            "to decide not to do or have something",
            "After careful consideration, he chose to ... the job offer",
            "Decline",
            "Отклонить"),
        Card(1,
            "to provide information or facts that can be used to prove something",
            "Can you ... evidence to support your claim?",
            "Substantiate",
            "Подтвердить"),
        Card(2,
            "to combine two or more things to create something new",
            "The chef decided to ... different cuisines to create a unique dish",
            "Blend",
            "Смешать")
    )

    val cards
        get() = cards1.toList()


    fun updateList(position: Int, card: Card) {
        cards1[position] = card
    }

    fun updateCard(
        oldCard: Card,
        question: String,
        example: String,
        answer: String,
        translation: String,
        imageURI: Uri?
    ):Card {
        return oldCard.copy(oldCard.id, question, example, answer, translation, imageURI)
    }



    fun NewCard(
        question: String,
        example: String,
        answer: String,
        translation: String,
        imageURI: Uri?
    ): Card {
        val nextId = (cards1.maxOfOrNull { it.id } ?: 0) + 1
        val card = Card(nextId, question, example, answer, translation, imageURI)
        return card
    }
    fun addCard(card: Card) {
        cards1.add(card)
    }
    fun removeCard(id: Int) {
        cards1.removeIf { it.id == id }
    }
}