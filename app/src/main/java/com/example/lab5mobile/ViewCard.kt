package com.example.lab5mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.la5mobile.R
import com.example.la5mobile.databinding.ViewCardBinding
import com.example.lab5mobile.Data.CardsRepository
//import com.example.lab5mobile.databinding.ViewCardBinding

class ViewCard : AppCompatActivity() {
    private lateinit var binding: ViewCardBinding
    private var index = NEW_CARD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //получение данных из интента
        index = intent.getIntExtra("index", NEW_CARD)
        //initView()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }


    private fun initView() {
        val card = CardsRepository.getCards()[index]

        binding.questionField.text = card.question
        binding.exampleField.text = card.example
        binding.answerView.text = card.answer
        binding.translateView.text = card.translate

        if (card.image != null) {
            binding.imageView3.setImageBitmap(card.image)
        } else {
            binding.imageView3.setImageResource(R.drawable.ic_image)
        }

        binding.button.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            intent.putExtra("index", index)
            startActivity(intent)
        }
    }
}
