package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CardItemBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: CardItemBinding
    lateinit var adapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CardItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cards = Model.cards

        val recyclerView: RecyclerView = binding.recid
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CardAdapter(cards, this, R.color.red, R.color.green)
        recyclerView.adapter = adapter


        binding.add.setOnClickListener {
            Intent(this, AddCardActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.setCards(Model.cards)
    }
}