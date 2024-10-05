package com.example.practicalwork29

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.practicalwork29.databinding.CustomWidgetBinding

class CustomWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    dafStyleAttr: Int = 0
) : LinearLayout(context, attrs, dafStyleAttr){
    val binding = CustomWidgetBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    fun setTextTopRow(text: String){
        binding.topRow.text = text
    }

    fun setTextBopRow(text: String){
        binding.botRow.text = text
    }
}