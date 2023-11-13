package com.example.lab5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5.databinding.ActivityListCardBinding

class ListCardActivity : AppCompatActivity() {
    lateinit var binding: ActivityListCardBinding
    lateinit var listCardFragment: ListCardFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listCardFragment = ListCardFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .add(R.id.container, listCardFragment)
            .commit()
    }
}


