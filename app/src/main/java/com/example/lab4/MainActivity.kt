package com.example.lab4

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openCardList(view: View) {
        val intent = Intent(this, CardList::class.java)
        startActivity(intent)
    }

    fun openCardAdd(view: View) {
        val intent = Intent(this, CardAdd::class.java)
        startActivity(intent)
    }

    fun openCardView(view: View) {
        val intent = Intent(this, CardView::class.java)
        startActivity(intent)
    }


}