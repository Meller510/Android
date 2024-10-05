package com.example.m13_databinding.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.m13_databinding.R
import com.example.m13_databinding.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun ImageView.setImageUrl(s: State) {
            when (s) {
                is State.Init -> Glide.with(this)
                    .load(context.getString(R.string.welocme_image)).into(this)

                is State.FailedRequest, is State.Error ->
                    Glide.with(this).load(R.drawable.error_svgrepo_com).into(this)

                is State.Success ->
                    Glide.with(this)
                        .load(s.movie.poster)
                        .error(
                            Glide.with(this)
                                .load(context.getString(R.string.placeholder_image))
                        )
                        .into(this)

                else -> Glide.with(this).clear(this)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}