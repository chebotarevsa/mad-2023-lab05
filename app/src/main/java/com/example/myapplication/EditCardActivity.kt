package com.example.myapplication

import android.net.Uri
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.databinding.CardEditBinding

class EditCardActivity : AppCompatActivity() {

    private lateinit var binding: CardEditBinding
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CardEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getIntExtra("position", 0)
        val cards = Model.cards
        val card = cards[position]

        with(binding) {
            questionEditText.setText(card.question)
            hintEditText.setText(card.example)
            answerEditText.setText(card.answer)
            translationEditText.setText(card.translate)
            card.image?.let { cardImage.setImageURI(it) }
        }

        binding.fab.setOnClickListener {
            val newCard = Model.updateCard(
                card,
                binding.questionEditText.text.toString(),
                binding.hintEditText.text.toString(),
                binding.answerEditText.text.toString(),
                binding.translationEditText.text.toString(),
                imageUri ?: card.image
            )
            Model.updateList(position, newCard)

            Intent(this, ViewCardActivity::class.java).apply {
                putExtra("position", position)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }.also {
                startActivity(it)
            }
        }

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }
    }

    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        val name = packageName
        this.grantUriPermission(name, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        binding.cardImage.setImageURI(it)
    }
}