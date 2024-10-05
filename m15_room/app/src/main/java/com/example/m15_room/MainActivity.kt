package com.example.m15_room

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.m15_room.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        @Suppress("UNCHECKED_CAST")
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dictionaryDao = (application as App).db?.dictionaryDao()
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    return dictionaryDao?.let { MainViewModel(it) } as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTextWatcherForEditText()

        binding.btnAdd.setOnClickListener {
            viewModel.onAddBtn(binding.editText.text.toString())
            binding.editText.text?.clear()
        }

        binding.btnClear.setOnClickListener { viewModel.onClearBtnAsync() }

        binding.btnSwitchView.setOnClickListener {
            val text = binding.btnSwitchView.text
            val switch = text.toString() == getString(R.string.all_view)
            viewModel.switchLimitedEntries(switch)

            if (switch) binding.btnSwitchView.text = getString(R.string.view_5)
            else binding.btnSwitchView.text = getString(R.string.all_view)
        }


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.entries.collect {
                    replayViewDictionary(it)
                }
            }
        }
    }

    private fun replayViewDictionary(dictionary: List<Word>) {
        binding.scrollViewLinear.removeAllViews()
        dictionary.forEach { word ->
            binding.scrollViewLinear.addView(
                createTextView(
                    getString(
                        R.string.view_word,
                        word.id,
                        word.word,
                        word.counter
                    )
                )
            )
        }
    }

    private fun createTextView(text: String, textSize: Float = 20f): TextView {
        val tv = TextView(binding.root.context)
        tv.text = text
        tv.textSize = textSize
        return tv
    }

    private fun initTextWatcherForEditText() {
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val regex = Pattern.compile("[a-zA-Zа-яА-Я-]+")
                if (editable.isNotEmpty() && !regex.matcher(editable).matches()) {
                    binding.editTextLayout.error = getString(R.string.layout_error)
                    binding.btnAdd.isEnabled = false
                    binding.btnClear.isEnabled = false
                } else {
                    binding.editTextLayout.error = null
                    binding.btnAdd.isEnabled = true
                    binding.btnClear.isEnabled = true
                }
            }
        })
    }
}