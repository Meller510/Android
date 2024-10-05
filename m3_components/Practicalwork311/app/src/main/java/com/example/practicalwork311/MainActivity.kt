package com.example.practicalwork311

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import com.example.practicalwork311.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var counterCoroutine: CoroutineScope? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStateUI()

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.textCounter.text = curCountNumber()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })


        binding.btnStartStop.setOnClickListener {
            if (binding.btnStartStop.text == getString(R.string.btn_txt_start)) {
                binding.btnStartStop.text = getString(R.string.btn_txt_stop)
                countDown()
            } else {
                binding.seekBar.isEnabled = true
                setStateUI(curCountNumber(), binding.seekBar.progress)
                stopCounterCoroutine()
            }
        }
    }

    private fun setStateUI(numTextCounter: CharSequence = "10", seekBarProg: Int = 0) {
        binding.btnStartStop.text = getString(R.string.btn_txt_start)
        binding.textCounter.text = numTextCounter
        binding.progressBarCircle.max = binding.textCounter.text.toString().toInt()
        binding.progressBarCircle.progress = binding.textCounter.text.toString().toInt()
        binding.seekBar.progress = seekBarProg
        binding.btnStartStop.text = getString(R.string.btn_txt_start)
    }

    private fun countDown() {
        counterCoroutine = CoroutineScope(Dispatchers.Main)
        counterCoroutine?.launch {
            binding.progressBarCircle.max = binding.textCounter.text.toString().toInt()
            binding.progressBarCircle.progress = binding.textCounter.text.toString().toInt()
            binding.seekBar.isEnabled = false
            while (binding.progressBarCircle.progress != 0) {
                delay(1000L)
                binding.progressBarCircle.progress = binding.progressBarCircle.progress.dec()
                binding.textCounter.text =
                    binding.textCounter.text.toString().toInt().dec().toString()
            }
            Toast.makeText(applicationContext, "Timer Task Finished", Toast.LENGTH_SHORT).show()
            binding.seekBar.isEnabled = true
            setStateUI(curCountNumber(), binding.seekBar.progress)
            stopCounterCoroutine()
        }
    }

    private fun curCountNumber() = ((binding.seekBar.progress + 1) * 10).toString()

    private fun stopCounterCoroutine() {
        counterCoroutine?.cancel()
        counterCoroutine = null
    }
}

