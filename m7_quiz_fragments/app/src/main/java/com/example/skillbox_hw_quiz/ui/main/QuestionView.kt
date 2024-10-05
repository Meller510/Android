package com.example.skillbox_hw_quiz.ui.main

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.QuestionViewBinding

class QuestionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding: QuestionViewBinding

    init {
        orientation = VERTICAL
        val inflatedView = inflate(context, R.layout.question_view, this)
        binding = QuestionViewBinding.bind(inflatedView)
    }
}