package com.example.lab4

import android.net.Uri

object Model {
    private val _cards = mutableListOf<Card>()

    val cards: List<Card>
        get() = _cards.toList()

    fun removeCard(id: Int) {
        _cards.removeIf { it.id == id }
    }

    fun addCard(card: Card) {
        _cards.add(card)
    }

//    fun updateCardList(card1: Card, card2: Card) {
//        val index = _cards.indexOf(card1)
//        _cards.remove(card1)
//        _cards.add(index, card2)
//    }

    fun updateCardList(position: Int, card: Card) {
        _cards.removeAt(position)
        _cards.add(position, card)
    }

    fun updateCard(
        oldCard: Card,
        question: String,
        example: String,
        answer: String,
        translation: String,
        imageURI: Uri?
    ): Card {
        return oldCard.copy(
            question = question,
            example = example,
            answer = answer,
            translation = translation,
            imageURI = imageURI
        )
    }

    fun createNewCard(
        question: String,
        example: String,
        answer: String,
        translation: String,
        imageURI: Uri?
    ): Card {
        val nextId = _cards.maxByOrNull { it.id }?.id?.plus(1) ?: 0
        return Card(nextId, question, example, answer, translation, imageURI)
    }
}