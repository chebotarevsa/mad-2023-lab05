package com.example.anki.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.anki.models.Model
import com.example.anki.utils.bitmap
import com.example.anki.databinding.FragmentManageCardBinding

class ManageCardFragment : Fragment() {

    private var _binding: FragmentManageCardBinding? = null
    private val binding get() = _binding!!
    private var image: Bitmap? = null
    private var isEditMode: Boolean = false
    private var cardId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cardId = it.getInt("cardId", -1)
            isEditMode = cardId != -1
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageCardBinding.inflate(layoutInflater, container, false)

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        if (isEditMode) {
            setupEditMode()
        } else {
            setupAddMode()
        }

        binding.cardImage.setOnClickListener {
            getSystemContent.launch("image/*")
        }
    }

    private fun setupEditMode() {
        cardId?.let { id ->
            val card = Model.cards[id]
            binding.enterQuestion.setText(card.question)
            binding.enterExample.setText(card.example)
            binding.enterAnswer.setText(card.answer)
            binding.enterTranslation.setText(card.translation)
            card.image?.let { img ->
                binding.cardImage.setImageBitmap(img)
            }
        }

        binding.saveButton.setOnClickListener {
            updateCard()
        }
    }

    private fun setupAddMode() {
        binding.saveButton.setOnClickListener {
            addNewCard()
        }
    }

    private fun addNewCard() {
        val question = binding.enterQuestion.text.toString()
        val example = binding.enterExample.text.toString()
        val answer = binding.enterAnswer.text.toString()
        val translation = binding.enterTranslation.text.toString()

        if (question.isEmpty() || example.isEmpty() || answer.isEmpty() || translation.isEmpty()) {
            Toast.makeText(requireContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val newCard = Model.createNewCard(question, example, answer, translation, image)
        Model.addCard(newCard)
        navigateToListCard()
    }

    private fun updateCard() {
        val safeCardId = cardId ?: return

        val question = binding.enterQuestion.text.toString()
        val example = binding.enterExample.text.toString()
        val answer = binding.enterAnswer.text.toString()
        val translation = binding.enterTranslation.text.toString()

        if (question.isEmpty() || example.isEmpty() || answer.isEmpty() || translation.isEmpty()) {
            Toast.makeText(requireContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val updatedCard =
            Model.updateCard(safeCardId, question, example, answer, translation, image)
        Model.updateCardList(safeCardId, updatedCard)
        navigateToSeeCard(safeCardId)
    }

    private fun navigateToListCard() {
        findNavController().navigate(com.example.anki.fragments.ManageCardFragmentDirections.actionManageCardFragmentToListCardFragment())
    }


    private fun navigateToSeeCard(id: Int) {
        val action =
            com.example.anki.fragments.ManageCardFragmentDirections.actionManageCardFragmentToSeeCardFragment(
                id
            )
        findNavController().navigate(action)
    }

    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        image = it.bitmap(requireContext())
        binding.cardImage.setImageBitmap(image)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
