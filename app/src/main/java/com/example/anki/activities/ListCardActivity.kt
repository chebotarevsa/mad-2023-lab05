package com.example.anki.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anki.databinding.ActivityListCardBinding


class ListCardActivity : AppCompatActivity() {
    lateinit var binding: ActivityListCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}


