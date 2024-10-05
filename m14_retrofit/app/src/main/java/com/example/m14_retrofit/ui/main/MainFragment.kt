package com.example.m14_retrofit.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.m14_retrofit.R
import com.example.m14_retrofit.databinding.FragmentMainBinding
import com.google.gson.Gson


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        @BindingAdapter("imageLoad")
        fun ImageView.setImageLoad(s: State) {
            when (s) {
                is State.Success, is State.Init ->
                    Glide.with(this)
                        .load(s.user?.picture?.large)
                        .error(
                            Glide.with(this)
                                .load(context.getString(R.string.load_image))
                        )
                        .into(this)
                is State.Loading -> Glide.with(this)
                    .load(context.getString(R.string.load_image)).into(this)

                else -> Glide.with(this).clear(this)
            }
        }

        @JvmStatic
        @BindingAdapter("addTextViewUser")
        fun LinearLayout.setAddViewUser(s: State) {
            val gson = Gson()
            val json = gson.toJson(s.user)
            val jsonObject = gson.fromJson(json, Map::class.java)

            fun processJsonObject(jsonObject: Map<*, *>, level: Int) {
                for ((key, value) in jsonObject) {
                    if(level == 0 && key == "name"||level == 0 && key == "picture") continue
                    if (value is Map<*, *>) {
                        val indentation = " ".repeat(level * 4)
                        val tv = TextView(this.context)
                        tv.textSize = 22F
                        tv.text = context.getString(R.string.json_obj_to_text, indentation, key)
                        this.addView(tv)
                        processJsonObject(value, level + 1)
                    }
                    else {
                        val indentation = " ".repeat(level * 4)
                        val tv = TextView(this.context)
                        tv.textSize = 22F
                        tv.text = context.getString(
                            R.string.json_key_value_to_text,
                            indentation,
                            key,
                            value
                        )
                        this.addView(tv)
                    }
                }
            }

            when (s) {
                is State.Success, is State.Init -> processJsonObject(jsonObject, 0)
                else -> this.removeAllViews()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}