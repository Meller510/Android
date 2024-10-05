package com.example.practicalwork49

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.practicalwork49.databinding.ActivitySecondBinding
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale

class SecondActivity : AppCompatActivity() {
    companion object {
        const val CHECK_PATRONYMIC = "patr"
        const val USER = "user"
    }

    class FullNameResultContract : ActivityResultContract<Boolean, User?>() {
        override fun createIntent(context: Context, input: Boolean): Intent {
            return Intent(context, SecondActivity::class.java)
                .putExtra(CHECK_PATRONYMIC, input)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): User? {
            if (resultCode == Activity.RESULT_OK)
                return intent?.getParcelableExtra(USER, User::class.java)
            return null
        }
    }

    private var _binding: ActivitySecondBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MyAPP SecondActivity", "onCreate")
        _binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.textInputPatronymic.isVisible = intent.getBooleanExtra(CHECK_PATRONYMIC, false)

        mBinding.textEditName.onFocusChangeListener = onFocusChangeListener
        mBinding.textEditLastName.onFocusChangeListener = onFocusChangeListener
        mBinding.textEditPatronymic.onFocusChangeListener = onFocusChangeListener

        mBinding.btnSave.setOnClickListener {
            if (inputErrorWarning(mBinding.textEditName) ||
                inputErrorWarning(mBinding.textEditLastName) ||
                (inputErrorWarning(mBinding.textEditPatronymic) &&
                        mBinding.textInputPatronymic.isVisible)
            )
                viewInputError()
            else {
                val userData = User(
                    name = mBinding.textEditName.text.toString().myCapitalize(),
                    lastName = mBinding.textEditLastName.text.toString().myCapitalize(),
                    patronymic = mBinding.textEditPatronymic.text.toString().myCapitalize()
                )
                val data = Intent()
                data.putExtra(USER, userData)
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MyAPP SecondActivity", "onStart")
    }


    override fun onResume() {
        super.onResume()
        Log.d("MyAPP SecondActivity", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MyAPP SecondActivity", "onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MyAPP SecondActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MyAPP SecondActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyAPP SecondActivity", "onDestroy")
    }

    private val onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) {
            when (v.id) {
                mBinding.textEditName.id -> {
                    if (inputErrorWarning(mBinding.textEditName)) mBinding.textInputName.error =
                        getString(R.string.error_input_name)
                    else mBinding.textInputName.error = null
                }

                mBinding.textEditLastName.id -> {
                    if (inputErrorWarning(mBinding.textEditLastName)) mBinding.textInputLastName.error =
                        getString(R.string.error_input_last_name)
                    else mBinding.textInputLastName.error = null
                }

                mBinding.textEditPatronymic.id -> {
                    if (inputErrorWarning(mBinding.textEditPatronymic)) mBinding.textInputPatronymic.error =
                        getString(R.string.error_input_patronymic)
                    else mBinding.textInputPatronymic.error = null
                }
            }
        }
    }

    private fun inputErrorWarning(textInputEdit: TextInputEditText): Boolean {
        return textInputEdit.text.toString().isEmpty() || !textInputEdit.text.toString()
            .matches("^[a-zA-Z]+\$".toRegex())
    }

    private fun viewInputError() {
        if (inputErrorWarning(mBinding.textEditName))
            mBinding.textInputName.error = getString(R.string.error_input_name)
        if (inputErrorWarning(mBinding.textEditLastName))
            mBinding.textInputLastName.error = getString(R.string.error_input_last_name)
        if (inputErrorWarning(mBinding.textEditPatronymic) && mBinding.textInputPatronymic.isVisible)
            mBinding.textInputPatronymic.error = getString(R.string.error_input_patronymic)
    }
}

fun String.myCapitalize() =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }