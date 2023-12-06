package com.example.lab05

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentViewCardBinding

class ViewCardFragment : Fragment() {
    private var _binding: FragmentViewCardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentViewCardBinding.inflate(layoutInflater, container, false)

        val position = intent.getIntExtra("position", 0)
        val card = Cards.cards.get(position)

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
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}