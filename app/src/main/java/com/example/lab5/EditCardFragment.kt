package com.example.lab5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lab5.databinding.FragmentEditCardBinding

private const val ARG_PARAM1 = "param1"

class EditCardFragment : Fragment() {

    private var _binding: FragmentEditCardBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null

    private var cardId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cardId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCardBinding.inflate(layoutInflater, container, false)
        val card = Model.cards.get(cardId)

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
            val question = when {
                binding.questionField.text.toString()
                    .isNotEmpty() -> binding.questionField.text.toString()

                else -> "Поле вопроса отсутствует"
            }
            val example = when {
                binding.exampleField.text.toString()
                    .isNotEmpty() -> binding.exampleField.text.toString()

                else -> "Поле примера отсутствует"
            }
            val answer = when {
                binding.answerField.text.toString()
                    .isNotEmpty() -> binding.answerField.text.toString()

                else -> "Поле ответа отсутствует"
            }
            val translation = when {
                binding.translationField.text.toString()
                    .isNotEmpty() -> binding.translationField.text.toString()

                else -> "Поле перевода отсутствует"
            }
            val newCard = Model.updateCard(
                card, question, example, answer, translation, imageUri
            )
            Model.updateCardList(cardId, newCard)
            val action = EditCardFragmentDirections.actionEditCardFragmentToSeeCardFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }


    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        val name = requireActivity().packageName
        requireActivity().grantUriPermission(name, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        binding.cardImage.setImageURI(it)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            EditCardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}