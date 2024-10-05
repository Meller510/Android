package com.example.skillbox_hw_quiz.ui.main

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.FragmentResultsBinding


private const val ARG_PARAM = "param"

class ResultsFragment : Fragment() {
    private var _binding: FragmentResultsBinding? = null
    private val mBinding get() = _binding!!
    private var param: String? = null
    private  var btnValueAnimator : ValueAnimator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater)

        animTitle()
        animBtnRestart()

        mBinding.btnRestart.setOnClickListener {
            findNavController().navigate(R.id.action_resultsFragment_to_quizFragment)
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.textResults.text = param
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ResultsFragment()
    }

    private fun animTitle() {
        val scaleX = PropertyValuesHolder.ofFloat(
            View.SCALE_X,
            1.0f,
            1.1f
        )
        val scaleY = PropertyValuesHolder.ofFloat(
            View.SCALE_Y,
            1.0f,
            1.1f
        )

        ObjectAnimator.ofPropertyValuesHolder(mBinding.textTitle, scaleX, scaleY).apply {
            duration = 3000
            interpolator = AccelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        btnValueAnimator?.end()
    }

    private fun animBtnRestart() {
       btnValueAnimator = ValueAnimator.ofObject(
            GradientArgbEvaluator,
            intArrayOf(Color.RED, Color.GREEN, Color.YELLOW),
            intArrayOf(Color.YELLOW, Color.RED, Color.GREEN),
            intArrayOf(Color.GREEN, Color.YELLOW, Color.RED)
        ).apply {
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            duration = 2000
            addUpdateListener {
                val shader = LinearGradient(
                    0f, 0f,
                    mBinding.btnRestart.paint.measureText(mBinding.btnRestart.text.toString()),
                    mBinding.btnRestart.textSize,
                    it.animatedValue as IntArray,
                    null,
                    Shader.TileMode.CLAMP
                )
                mBinding.btnRestart.paint.shader = shader
                mBinding.btnRestart.invalidate()
            }
            start()
        }
    }


    object GradientArgbEvaluator : TypeEvaluator<IntArray> {
        private val argbEvaluator = ArgbEvaluator()
        override fun evaluate(fraction: Float, startValue: IntArray, endValue: IntArray): IntArray {
            return startValue.mapIndexed { index, i ->
                argbEvaluator.evaluate(fraction, i, endValue[index]) as Int
            }.toIntArray()
        }
    }
}