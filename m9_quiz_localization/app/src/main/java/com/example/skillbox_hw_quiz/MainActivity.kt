package com.example.skillbox_hw_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skillbox_hw_quiz.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private  var _binding: MainActivityBinding? = null
    private val mBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}