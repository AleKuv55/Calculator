package com.example.calculator.presentation.common

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    private val tag = this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "OnCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")

    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")

    }
}

