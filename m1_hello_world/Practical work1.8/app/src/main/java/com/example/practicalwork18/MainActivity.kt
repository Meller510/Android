package com.example.practicalwork18

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import com.example.practicalwork18.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private var counter = 0

    companion object {
        const val capacity = 49
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (binding.textCounter.text == "0") startState()

        binding.btnPlus.setOnClickListener {
            counter = binding.textCounter.text.toString().toInt().inc()
            binding.textCounter.text = counter.toString()
            update()
        }

        binding.btnMinus.setOnClickListener {
            if (binding.textCounter.text.toString().toInt() > 0) {
                counter = binding.textCounter.text.toString().toInt().dec()
                binding.textCounter.text = counter.toString()
                update()
            }

            if (binding.textCounter.text.toString().toInt() == 0) startState()
            if (binding.btnReset.isVisible &&
                binding.textCounter.text.toString().toInt() < 50
            ) binding.btnReset.isVisible = false
        }
        binding.btnReset.setOnClickListener {
            startState()
        }
    }

    private fun update() {
        if (binding.textCounter.text.toString().toInt() in 0..capacity) {
            binding.btnMinus.isEnabled = true
            val curCount = (capacity - binding.textCounter.text.toString().toInt()).toString()
            binding.textStatus.text = getString(R.string.count_seats, curCount)
            if (binding.textStatus.currentTextColor != getColor(R.color.blue)) binding.textStatus.setTextColor(
                getColor(R.color.blue)
            )
        } else if (binding.textCounter.text.toString().toInt() > capacity) {
            binding.btnReset.isVisible = true
            binding.textStatus.setText(R.string.limit_exceed)
            if (binding.textStatus.currentTextColor != getColor(R.color.red)) binding.textStatus.setTextColor(
                getColor(R.color.red)
            )
        }
    }

    private fun startState() {
        binding.btnReset.isVisible = false
        binding.btnMinus.isEnabled = false
        binding.textStatus.setText(R.string.free_seats)
        binding.textStatus.setTextColor(getColor(R.color.green))
        binding.textCounter.text = "0"
    }
}
