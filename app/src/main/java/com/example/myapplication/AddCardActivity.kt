package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.CardAddBinding
import com.google.android.material.snackbar.Snackbar

class AddCardActivity : AppCompatActivity() {
    private lateinit var binding: CardAddBinding
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CardAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            if (fieldsValid()) {
                val question = binding.questionAddText.text.toString()
                val example = binding.hintAddText.text.toString()
                val answer = binding.answerAddText.text.toString()
                val translation = binding.translationAddText.text.toString()
                val newCard = Model.NewCard(question, example, answer, translation, imageUri)
                Model.addCard(newCard)
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                fieldsIncompleteError()
            }
        }

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }
    }

    private fun fieldsValid(): Boolean {
        return binding.questionAddText.text.isNotEmpty() &&
                binding.hintAddText.text.isNotEmpty() &&
                binding.answerAddText.text.isNotEmpty() &&
                binding.translationAddText.text.isNotEmpty()
    }

    private fun fieldsIncompleteError() {
        val errorMessage = getString(R.string.error_message)
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        val name = this.packageName
        this.grantUriPermission(name, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        binding.cardImage.setImageURI(it)
    }
}