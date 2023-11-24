package com.example.lab5mobile

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.la5mobile.R
import com.example.la5mobile.databinding.AddCardBinding
import com.example.lab5mobile.Data.CardsRepository




const val NEW_CARD = -1
class AddCardActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_PICK = 1
    private lateinit var binding: AddCardBinding
    private var imageBIT:Bitmap? = null
    private var index = NEW_CARD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddCardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        index = intent.getIntExtra("index", index)

        if (index != NEW_CARD) {
            val card = CardsRepository.getCards()[index]
            // получение данных из интента editButton ViewCard
            binding.editTextText.setText(card.question)
            binding.editTextText2.setText(card.example)
            binding.editTextText3.setText(card.answer)
            binding.editTextText4.setText(card.translate)
            imageBIT = card.image
            if (imageBIT != null) {
                binding.imageView2.setImageBitmap(imageBIT)
            } else {
                binding.imageView2.setImageResource(R.drawable.ic_image)
            }
        }

        binding.imageView2.setOnClickListener {
            getImage.launch( "image/*")
        }

        binding.button.setOnClickListener {
            addTermCard()
        }
    }

    private val getImage  = registerForActivityResult(ImageContract()){ uri ->
        uri?.let {
            val image = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.createSource(this.contentResolver,uri)
            } else {
                TODO("VERSION.SDK_INT < P")
            }
            imageBIT = ImageDecoder.decodeBitmap(image)
            binding.imageView2.setImageBitmap(imageBIT)
        }
    }

    //добавление карточки
    private fun addTermCard() {

        val question = binding.editTextText.text.toString()
        val hint = binding.editTextText2.text.toString()
        val answer = binding.editTextText3.text.toString()
        val translate = binding.editTextText4.text.toString()
        val image = imageBIT

        // проверка на заполненность полей
        if (question.isEmpty() || hint.isEmpty() || answer.isEmpty() || translate.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        val newCard = CardsRepository.createNewCard(question, hint, answer, translate, image)
        if (index == NEW_CARD) {
            CardsRepository.addCard(newCard)
        } else {
            CardsRepository.replaceCard(newCard, index)
        }

        finish()
    }
}