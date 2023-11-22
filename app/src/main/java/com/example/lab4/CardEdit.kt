package com.example.lab4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lab4.databinding.CardeditBinding

class CardEdit : AppCompatActivity() {

    lateinit var binding: CardeditBinding
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = CardeditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", 0) //Опять же получение нужного элемента
        val cards = Model.cards
        val card = cards.get(position) //Получение самой карточки

        binding.questionField.setText(card.question)
        binding.exampleField.setText(card.example)
        binding.answerField.setText(card.answer)
        binding.translationField.setText(card.translation)
        binding.cardImage.setImageURI(card.imageURI)
        imageUri = card.imageURI

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }

        binding.saveButton.setOnClickListener {//Сохранение, считывая то, что введено
            val question = when {
                binding.questionField.text.toString()
                    .isNotEmpty() -> binding.questionField.text.toString()

                else -> "Поле вопроса отсутствует"
            }
            val example = when {
                binding.exampleField.text.toString()
                    .isNotEmpty() -> binding.exampleField.text.toString()

                else -> "Поле примера отсутствует"
            }
            val answer = when {
                binding.answerField.text.toString()
                    .isNotEmpty() -> binding.answerField.text.toString()

                else -> "Поле ответа отсутствует"
            }
            val translation = when {
                binding.translationField.text.toString()
                    .isNotEmpty() -> binding.translationField.text.toString()

                else -> "Поле перевода отсутствует"
            }
            val newCard = Model.updateCard(
                card, question, example, answer, translation, imageUri
            )
            Model.updateCardList(position, newCard)
            Intent(this, CardSee::class.java).apply {
                putExtra("position", position)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }.also {
                startActivity(it)
            }
        }

    }

    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        val name = this.packageName
        this.grantUriPermission(name, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        binding.cardImage.setImageURI(it)
    }
}