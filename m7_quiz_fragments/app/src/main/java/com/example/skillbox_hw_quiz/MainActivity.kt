package com.example.skillbox_hw_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skillbox_hw_quiz.databinding.MainActivityBinding
import com.example.skillbox_hw_quiz.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}