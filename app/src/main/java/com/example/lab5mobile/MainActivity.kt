package com.example.lab5mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.la5mobile.databinding.ActivityMainBinding
import com.example.lab4mobile.Data.CallbackFun
import com.example.lab4mobile.Data.CardsAdapter
import com.example.lab5mobile.Data.CardsRepository
import com.example.lab5mobile.Data.TermCard
//import com.example.lab5mobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CardsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CardsAdapter(adapterCallBack)
        adapter.setItem(CardsRepository.getCards())
        val layoutManager = LinearLayoutManager(this)
        binding.RecyclerView.layoutManager = layoutManager
        binding.RecyclerView.adapter = adapter

        val button = binding.button
        button.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.setItem(CardsRepository.getCards())
    }

    private val adapterCallBack =  object : CallbackFun {
        override fun deleteCard(card: TermCard) {
            CardsRepository.deleteCard(card)
        }

        override fun showCard(index: Int) {
            val intent = Intent(this@MainActivity, ViewCard::class.java)
            intent.putExtra("index", index)
            this@MainActivity.startActivity(intent)
        }
    }
}