package com.example.m16_architecture.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.m16_architecture.R
import com.example.m16_architecture.databinding.FragmentMainBinding
import com.example.m16_architecture.di.DaggerAppComponent
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        DaggerAppComponent.create().mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.button.setOnClickListener { viewModel.reloadPokemonInfo() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is State.Init -> updateTextAndImage(
                            R.drawable.poster,
                            getString(R.string.hello)
                        )

                        is State.Loading -> updateTextAndImage(
                            R.drawable.pokeball,
                            getString(R.string.loading)
                        )

                        is State.Success -> updateTextAndImage(it.result.sprite()!!, it.result.name)

                        is State.Error -> updateTextAndImage(R.drawable.error, it.error)
                    }
                }
            }
        }
    }

    private fun updateTextAndImage(image: Any, text: String) {
        mBinding.imageView.load(image)
        mBinding.textView.text = text
    }
}