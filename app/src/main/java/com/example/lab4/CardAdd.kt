package com.example.lab4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lab4.databinding.ActivityCardAddBinding


class CardAdd : AppCompatActivity() {
    lateinit var binding: ActivityCardAddBinding
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityCardAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }

        binding.addButton.setOnClickListener {
            val question = binding.enterQuestion.text.toString().takeIf { it.isNotEmpty() } ?: getString(
                R.string.unfilledQuestion
            )
            val example = binding.enterExample.text.toString().takeIf { it.isNotEmpty() } ?: getString(
                R.string.unfilledExample
            )
            val answer = binding.enterAnswer.text.toString().takeIf { it.isNotEmpty() } ?: getString(
                R.string.unfilledAnswer
            )
            val translation = binding.enterTranslation.text.toString().takeIf { it.isNotEmpty() } ?: getString(
                R.string.unfilledTranslation
            )
            val newCard = Model.createNewCard(question, example, answer, translation, imageUri)
            Model.addCard(newCard)
            Intent(this, CardList::class.java).also {
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

