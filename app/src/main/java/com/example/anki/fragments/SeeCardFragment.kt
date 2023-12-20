package com.example.anki.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.anki.R
import com.example.anki.databinding.FragmentSeeCardBinding
import com.example.anki.models.Model
import com.example.anki.navigation.SeeCardFragmentArgs


class SeeCardFragment : Fragment() {
    private var _binding: FragmentSeeCardBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<SeeCardFragmentArgs>()
    private val cardId by lazy { args.cardId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeeCardBinding.inflate(inflater, container, false)


        val cardId = args.cardId
        val card = Model.getCardById(cardId)


        if (card != null) {

            binding.cardQuestion.text = getString(R.string.questionField, card.question)
            binding.cardExample.text = getString(R.string.exampleField, card.example)
            binding.cardAnswer.text = getString(R.string.answerField, card.answer)
            binding.cardTranslation.text = getString(R.string.translationField, card.translation)




            card.image?.let { image ->
                binding.cardThumbnail.setImageBitmap(image)
            }
        } else {

            Toast.makeText(context, "Карточка не найдена", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }


        binding.editButton.setOnClickListener {
            val action =
                SeeCardFragmentDirections.actionSeeCardFragmentToManageCardFragmentEdit(cardId)
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}




