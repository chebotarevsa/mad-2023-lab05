package com.example.lab4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab4.databinding.FragmentCardViewActivityBinding

class CardViewFragment : Fragment() {
    private var _binding: FragmentCardViewActivityBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<CardViewFragmentArgs>()
    private val cardId by lazy { args.cardId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardViewActivityBinding.inflate(inflater, container, false)
        val card = Model.getCardById(cardId)

        binding.textQuestion.text = getString(R.string.questionField, card.question)
        binding.textExample.text = getString(R.string.exampleField, card.example)
        binding.textAnswer.text = getString(R.string.answerField, card.answer)
        binding.textTranslation.text = getString(R.string.translationField, card.translation)
        card.imageUri?.let {
            binding.picture.setImageURI(it)
        }
        binding.buttonEdit.setOnClickListener {
            val action = CardSeeFragmentDirections.actionCardSeeFragmentToCardEditFragment(cardId)
            findNavController().navigate(action)
        }

        binding.buttonBack.setOnClickListener {
            val action = CardSeeFragmentDirections.actionCardSeeFragmentToCardListFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}