package com.example.lab4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lab4.databinding.ActivityCardEditBinding


class CardEdit : AppCompatActivity() {
    lateinit var binding: ActivityCardEditBinding
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityCardEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", 0)
        val cards = Model.cards
        val card = cards[position]

        binding.questionField.setText(card.question)
        binding.exampleField.setText(card.example)
        binding.answerField.setText(card.answer)
        binding.translationField.setText(card.translation)
        binding.cardImage.setImageURI(card.imageURI)
        imageUri = card.imageURI

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }

        binding.saveButton.setOnClickListener {
            val question = binding.questionField.text.toString().takeIf { it.isNotEmpty() } ?: getString(
                R.string.unfilledQuestion
            )
            val example = binding.exampleField.text.toString().takeIf { it.isNotEmpty() } ?: getString(
                R.string.unfilledExample
            )
            val answer = binding.answerField.text.toString().takeIf { it.isNotEmpty() } ?: getString(
                R.string.unfilledAnswer
            )
            val translation = binding.translationField.text.toString().takeIf { it.isNotEmpty() } ?: getString(
                R.string.unfilledTranslation
            )
            val newCard = Model.updateCard(card, question, example, answer, translation, imageUri)
            Model.updateCardList(position, newCard)
            Intent(this, CardView::class.java).apply {
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
