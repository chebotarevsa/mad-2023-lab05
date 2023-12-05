package com.example.lab4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4.databinding.ActivityCardListBinding

class CardList : AppCompatActivity() {
    lateinit var binding: ActivityCardListBinding
    private lateinit var adapter: AdapterRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityCardListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cards = Model.cards

        val recyclerView: RecyclerView = binding.recyclerId
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdapterRecyclerView(cards, this)
        recyclerView.adapter = adapter

        binding.addButton.setOnClickListener {
            Intent(this, CardAdd::class.java).also {
                startActivity(it)
            }
        }
    }

    fun removeCard(card: Card) {
        Model.removeCard(card.id)
        adapter.refreshCardsViewWith(Model.cards)
    }

    override fun onResume() {
        super.onResume()
        adapter.refreshCardsViewWith(Model.cards)
    }
}