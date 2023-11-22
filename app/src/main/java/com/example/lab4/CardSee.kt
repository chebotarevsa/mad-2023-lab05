package com.example.lab4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab4.databinding.CardseeBinding

class CardSee : AppCompatActivity() {

    lateinit var binding: CardseeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = CardseeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", 0) //Получение данных о выбранной карточке
        val card = Model.cards.get(position) //Получение данных по id

        binding.textQuestion.text = getString(R.string.questionField, card.question) //Собственно, установление текста и картиночки
        binding.textExample.text = getString(R.string.exampleField, card.example)
        binding.textAnswer.text = getString(R.string.answerField, card.answer)
        binding.textTranslation.text = getString(R.string.translationField, card.translation)
        binding.picture.setImageURI(card.imageURI)

        binding.buttonEdit.setOnClickListener { //Кнопка редактирования
            Intent(this, CardEdit::class.java).apply {
                putExtra("position", position)
            }.also {
                startActivity(it) //Сразу запустить активность - редактирование
            }
        }

        binding.buttonBack.setOnClickListener {
            onBackPressed() //На шаг назад
        }
    }
}