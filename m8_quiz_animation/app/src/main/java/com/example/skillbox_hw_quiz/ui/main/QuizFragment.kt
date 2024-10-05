package com.example.skillbox_hw_quiz.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.FragmentQuizBinding
import com.example.skillbox_hw_quiz.databinding.QuestionViewBinding
import com.example.skillbox_hw_quiz.quiz.Quiz
import com.example.skillbox_hw_quiz.quiz.QuizStorage
import kotlin.collections.set


class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var quizStorage: QuizStorage
    private lateinit var quiz: Quiz
    private var listQuestionView = mutableListOf<QuestionViewBinding>()
    private val questionAnswers = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater)

        quizStorage = QuizStorage
        quiz = quizStorage.getQuiz(QuizStorage.Locale.Ru)
        initQuestionView()

        var delay = 200L
        listQuestionView.forEach {
            animationQuestionView(it,delay)
            delay += delay / 2

            groupAnswerOnClickListener(it)
        }

        mBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mBinding.btnSend.setOnClickListener {
            val result = QuizStorage.answer(quiz, questionAnswers.values.toList())
            questionAnswers.clear()
            findNavController().navigate(
                R.id.action_quizFragment_to_resultsFragment,
                bundleOf("param" to result)
            )
        }

        return mBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = QuizFragment()
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    private fun initQuestionView() {
        quiz.questions.forEach { it ->
            val questionView = QuestionViewBinding.inflate(layoutInflater)
            questionView.textQuestion.setTextAppearance(R.style.TextStyleQuestion)

            val content = SpannableString(it.question)
            content.setSpan(UnderlineSpan(), 0, content.length, 0)
            questionView.textQuestion.text = content

            it.answers.forEachIndexed { i, text ->
                val newRadioButton = RadioButton(context)
                newRadioButton.id = i
                newRadioButton.text = text
                newRadioButton.tag = it.question
                questionView.groupAnswer.addView(newRadioButton)
            }

            mBinding.linearLayoutVertical.addView(questionView.root)
            listQuestionView.add(questionView)
        }
    }

    private fun groupAnswerOnClickListener(questionView: QuestionViewBinding) {
        questionView.groupAnswer.setOnCheckedChangeListener { group, _ ->
            val selectedRadioId = group.checkedRadioButtonId

            if (selectedRadioId != -1) {
                val radioButton: RadioButton = group.findViewById(selectedRadioId)
                questionAnswers[radioButton.tag.toString()] = radioButton.id
            }

            if (quiz.questions.count() == questionAnswers.count()) mBinding.btnSend.isEnabled =
                true
        }
    }

    private fun animationQuestionView(questionView: QuestionViewBinding , delay: Long ) {
        questionView.textQuestion.translationY = -200f
        questionView.textQuestion.animate().apply {
            duration = 1500
            translationY(0.0f)
            interpolator = AccelerateDecelerateInterpolator()
            startDelay = delay
        }.start()

        val delayQuestion = 1500L
        questionView.groupAnswer.alpha = 0.0f
        questionView.groupAnswer.animate().apply {
            duration = 1500
            alpha(1.0f)
            startDelay = delayQuestion + delay
        }.start()
    }
}