package com.example.lab05

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCardListBinding

class CardListFragment : Fragment() {
    private var _binding: FragmentCardListBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = FragmentCardListBinding.inflate(layoutInflater, container, false)

        val cards = Cards.cards

        val recyclerView: RecyclerView = binding.recyclerid
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = RecyclerAdapter(cards, requireContext()){ card->
            val alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Удаление карточки?")
                .setMessage("Вы действительно хотите удалить карточку:\n ${card.translation}")
                .setPositiveButton("Да") { _, _ ->
                    Cards.removeCard(card.id)
                    adapter.setCards(Cards.cards)
                }.setNegativeButton("Нет") { _, _ ->
                }.create()

            alertDialog.setOnShowListener {
                val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                positiveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

                val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                negativeButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }

            alertDialog.show()
        }

        recyclerView.adapter = adapter

        adapter.enableSwipeToDelete(recyclerView)

        binding.addButton.setOnClickListener {
            Intent(this, AddCardActivity::class.java).also {
                startActivity(it)
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.refreshCardsViewWith(Cards.cards)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}