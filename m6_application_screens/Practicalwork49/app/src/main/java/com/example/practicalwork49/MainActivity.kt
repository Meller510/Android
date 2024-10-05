package com.example.practicalwork49

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.practicalwork49.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val USER_DATA = "uData"
    }

    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    private var userData: User? = null
    private val launcherActivity =
        registerForActivityResult(SecondActivity.FullNameResultContract()) { uData: User? ->
            if (uData != null) {
                mBinding.textUserData.text = uData.toString()
                userData = uData
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MyAPP MainActivity", "onCreate")
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnPresent.setOnClickListener {
            launcherActivity.launch(mBinding.checkBox.isChecked)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MyAPP MainActivity", "onStart")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("MyAPP MainActivity", "onRestoreInstanceState")
        userData = savedInstanceState.getParcelable(USER_DATA, User::class.java) ?: User()
        mBinding.textUserData.text = userData.toString()
    }

    override fun onResume() {
        super.onResume()
        Log.d("MyAPP MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MyAPP MainActivity", "onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MyAPP MainActivity", "onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.d("MyAPP MainActivity", "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("MyAPP MainActivity", "onSaveInstanceState")
        outState.putParcelable(USER_DATA, userData)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyAPP MainActivity", "onDestroy")
    }
}


//MyAPP MainActivity   onCreate
//MyAPP MainActivity   onStart
//MyAPP MainActivity   onResume
//MyAPP MainActivity   onPause
//MyAPP SecondActivity onCreate
//MyAPP SecondActivity onStart
//MyAPP SecondActivity onResume
//MyAPP MainActivity   onStop
//MyAPP MainActivity   onSaveInstanceState




