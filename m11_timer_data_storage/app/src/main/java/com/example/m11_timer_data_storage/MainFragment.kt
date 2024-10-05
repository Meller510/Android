package com.example.m11_timer_data_storage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.m11_timer_data_storage.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: Repository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = Repository(binding.root.context)

        binding.textView.text = repository.getText()

        binding.btnSave.setOnClickListener {
            repository.saveText(binding.editText.text.toString())
            binding.editText.text.clear()
            binding.textView.text = repository.getText()
        }

        binding.btnClear.setOnClickListener {
            repository.clearText()
            binding.textView.text = repository.getText()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}