package com.example.android.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val req_code = "REQUEST_CODE"
    }
    private var flag = false

    @SuppressLint("SetTextI18n")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val resultLauncher: ActivityResultLauncher<String> =
            registerForActivityResult(PostActivityResult()) { result ->

                if (result == null) {
                    return@registerForActivityResult
                } else {
                    resultTextView.text = result
                    flag = true
                    showResultViews()
                }
            }

        addButton.setOnClickListener {
            resultLauncher.launch(Operations.ADD.toString())
        }
        subButton.setOnClickListener {
            resultLauncher.launch(Operations.SUBTRACT.toString())
        }
        multiplyButton.setOnClickListener {
            resultLauncher.launch(Operations.MULTIPLY.toString())
        }
        divideButton.setOnClickListener {
            resultLauncher.launch(Operations.DIVIDE.toString())
        }

        resetButton.setOnClickListener {
            flag = false
            showOperationViews()
        }
    }
    
    override fun onBackPressed() {
        if (flag) {
            flag = false
            showOperationViews()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if(flag)
            outState.putString("ResultString", resultTextView.text.toString())

        outState.putBoolean("flagValue", flag)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        flag = savedInstanceState.getBoolean("flagValue")
        if (flag) {
            resultTextView.text = savedInstanceState.getString("ResultString")
            showResultViews()
        }
        else
            showOperationViews()
    }

    private fun showResultViews() {

        result_layout.visibility = View.VISIBLE

        addButton.visibility = View.GONE
        subButton.visibility = View.GONE
        multiplyButton.visibility = View.GONE
        divideButton.visibility = View.GONE
    }

    private fun showOperationViews() {

        result_layout.visibility = View.GONE

        addButton.visibility = View.VISIBLE
        subButton.visibility = View.VISIBLE
        multiplyButton.visibility = View.VISIBLE
        divideButton.visibility = View.VISIBLE
    }
}
