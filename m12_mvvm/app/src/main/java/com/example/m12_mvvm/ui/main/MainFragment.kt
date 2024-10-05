package com.example.m12_mvvm.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.m12_mvvm.R
import com.example.m12_mvvm.databinding.FragmentMainBinding
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.btnSearch.setOnClickListener {
            viewModel.onSearchMovies(mBinding.searchText.text.toString())
        }

        initTextWatcherForEditText()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is State.Init -> mBinding.textView.text = getString(R.string.greeting)

                        is State.Loading -> {
                            mBinding.imageView.setImageBitmap(null)
                            mBinding.progressBar.isVisible = true
                            mBinding.btnSearch.isEnabled = false
                        }

                        is State.Success -> showResponseView(
                            getString(R.string.title_novie, it.result.title, it.result.year),
                            it.result.poster.toString(), 2.2f, 2.2f
                        )

                        is State.FailedRequest -> showResponseView(
                            getString(R.string.request_not_found, it.fRequest),
                            R.drawable.error_svgrepo_com, 0.8f, 0.8f
                        )

                        is State.Error -> showResponseView(
                            it.error,
                            R.drawable.error_svgrepo_com, 0.8f, 0.8f
                        )
                    }
                }
            }
        }
    }

    private fun initTextWatcherForEditText() {
        mBinding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable) {
                val regex = Pattern.compile("^[a-zA-Z0-9]+$")
                if (editable.length <= 2 || !regex.matcher(editable).matches()) {
                    mBinding.searchLayout.error = getString(R.string.search_layout_error)
                    mBinding.btnSearch.isEnabled = false
                } else {
                    mBinding.searchLayout.error = null
                    mBinding.btnSearch.isEnabled = true
                }
            }
        })
    }

    private fun showResponseView(text: String, image: Any, imageScaleX: Float, imageScaleY: Float) {
        mBinding.progressBar.isVisible = false
        mBinding.textView.text = text
        mBinding.imageView.load(image)
        mBinding.imageView.scaleX = imageScaleX
        mBinding.imageView.scaleY = imageScaleY
        mBinding.searchText.text?.clear()
        mBinding.searchLayout.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


