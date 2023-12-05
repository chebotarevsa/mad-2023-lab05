package com.example.lab4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab4.databinding.ActivityCardViewBinding

class CardView : AppCompatActivity() {

    private lateinit var binding: ActivityCardViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityCardViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", 0)
        val card = Model.cards[position]

        binding.textQuestion.text = getString(R.string.questionField, card.question)
        binding.textExample.text = getString(R.string.exampleField, card.example)
        binding.textAnswer.text = getString(R.string.answerField, card.answer)
        binding.textTranslation.text = getString(R.string.translationField, card.translation)
        binding.picture.setImageURI(card.imageURI)

        binding.buttonEdit.setOnClickListener {
            Intent(this, CardEdit::class.java).apply {
                putExtra("position", position)
            }.also {
                startActivity(it)
            }
        }
    }
}