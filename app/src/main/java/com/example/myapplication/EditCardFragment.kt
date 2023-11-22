package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentEditCardBinding

class EditCardFragment : Fragment() {
    private var _binding: FragmentEditCardBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null
    private val args by navArgs<EditCardFragmentArgs>()
    private val cardId by lazy { args.cardId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = FragmentEditCardBinding.inflate(layoutInflater, container, false)
        val card = Model.getCardById(cardId)

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
            Model.updateList(cardId, newCard)

            val action = EditCardFragmentDirections.actionEditCardFragmentToViewCardFragment(cardId)
            findNavController().navigate(action)
        }

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }
        return binding.root
    }
    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        val name = requireActivity().packageName
        requireActivity().grantUriPermission(name, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        binding.cardImage.setImageURI(it)
    }

}