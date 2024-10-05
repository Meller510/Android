package com.example.skillbox_hw_quiz.ui.main

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.MainFragmentBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val DATE_BIRTH = "db"

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val mBinding get() = _binding!!

    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale("EN"))
    private var selectedDateBirth = ""

    private var animatorBtnDatePicker: ObjectAnimator? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnLaunchQuiz.setOnClickListener {
            if (selectedDateBirth.isNotEmpty())
                findNavController().navigate(R.id.action_mainFragment_to_quizFragment)
            else
                snackbarBtnLaunch()
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(DATE_BIRTH)) {
            calendar.timeInMillis = savedInstanceState.getLong(DATE_BIRTH)
            selectedDateBirth = dateFormat.format(calendar.time)
        }

        if (selectedDateBirth.isEmpty()) {
            setBtnDatePickerForAnimation()
            animBtnDatePicker()
        }

        mBinding.btnDatePicker.setOnClickListener { datePickerView() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (selectedDateBirth.isNotEmpty())
            outState.putLong(DATE_BIRTH, calendar.timeInMillis)
    }

    private fun datePickerView() {
        val dateDialog = MaterialDatePicker.Builder.datePicker()
            .setSelection(calendar.timeInMillis)
            .setTitleText(getString(R.string.date_picker_title))
            .build()

        dateDialog.addOnPositiveButtonClickListener {
            restoreStartStateBntDatePicker()
            selectedDateBirth = getDate(it)
            snackbarSelectedDate(selectedDateBirth)
        }
        dateDialog.show(parentFragmentManager, "DatePicker")
    }

    private fun snackbarBtnLaunch() {
        Snackbar.make(
            mBinding.btnDatePicker,
            getString(R.string.snackbar_btn_launch),
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(
            ContextCompat.getColor(requireActivity(), R.color.snackbarBgMainFragment)
        )
            .setActionTextColor(Color.WHITE)
            .setTextColor(Color.BLACK)
            .setAnchorView(mBinding.btnLaunchQuiz)
            .setAction(getString(R.string.action_snackbar_btn_launch)) { datePickerView() }
            .show()
    }

    private fun snackbarSelectedDate(selectDateBirth: String) {
        Snackbar.make(
            mBinding.btnDatePicker,
            getString(R.string.snackbar_select_date, selectDateBirth),
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(
            ContextCompat.getColor(requireActivity(), R.color.snackbarBgSelectedDate)
        )
            .setTextColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.colorPrimary
                )
            )
            .setAnchorView(mBinding.btnDatePicker)
            .show()
    }

    private fun animBtnDatePicker() {
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

        animatorBtnDatePicker = ObjectAnimator
            .ofPropertyValuesHolder(mBinding.btnDatePicker, scaleX, scaleY)
            .apply {
                duration = 2000
                interpolator = AccelerateInterpolator()
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }
        animatorBtnDatePicker!!.start()
    }

    private fun setBtnDatePickerForAnimation() {
        mBinding.btnDatePicker.textSize = 18f
        mBinding.btnDatePicker.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.btnLaunchMainFragment
            )
        )
    }

    private fun restoreStartStateBntDatePicker() {
        animatorBtnDatePicker?.end()
        mBinding.btnDatePicker.setTextColor(Color.BLACK)
        mBinding.btnDatePicker.textSize = 12f
    }

    private fun getDate(l: Long): String {
        calendar.timeInMillis = l
        return dateFormat.format(calendar.time)
    }
}