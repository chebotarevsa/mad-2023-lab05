package com.example.lab5

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5.databinding.ActivitySeeCardBinding

class SeeCardActivity : AppCompatActivity() {

    lateinit var binding: ActivitySeeCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", 0)
        val card = Model.cards.get(position)

        binding.cardQuestion.text = getString(R.string.questionField, card.question)
        binding.cardExample.text = getString(R.string.exampleField, card.example)
        binding.cardAnswer.text = getString(R.string.answerField, card.answer)
        binding.cardTranslation.text = getString(R.string.translationField, card.translation)
        binding.cardThumbnail.setImageURI(card.imageURI)

        binding.editButton.setOnClickListener {
            Intent(this, EditCardActivity::class.java).apply {
                putExtra("position", position)
            }.also {
                startActivity(it)
            }
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
}