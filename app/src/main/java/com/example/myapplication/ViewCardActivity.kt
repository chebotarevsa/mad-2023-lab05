package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.CardViewBinding

class ViewCardActivity : AppCompatActivity() {

    lateinit var binding: CardViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CardViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", 0)
        val cards = Model.cards
        val card = cards.get(position)

        binding.questionTextCard.text = getString(R.string.questionT, card.question)
        binding.hintTextCard.text = getString(R.string.hintT, card.example)
        binding.answerTextCard.text = getString(R.string.answerT, card.answer)
        binding.translationTextCard.text = getString(R.string.translateT, card.translate)
        binding.cardImage.setImageURI(card.image)

        binding.edit.setOnClickListener {
            Intent(this, EditCardActivity::class.java).apply {
                putExtra("position", position)
            }.also {
                startActivity(it)
            }
        }
    }
}