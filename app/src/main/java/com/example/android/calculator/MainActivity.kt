package com.example.android.calculator

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val REQUEST_CODE = "REQUEST_CODE"

        val resultLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val requestCode: Operations = result.data?.extras?.getSerializable(REQUEST_CODE) as Operations

                val resultData = result.data
                if (resultData == null || result.resultCode != RESULT_OK) {
                    return@registerForActivityResult
                } else {
                    val input1 = resultData.getDoubleExtra("Input1", 0.0)
                    val input2 = resultData.getDoubleExtra("Input2", 0.0)

                    val res = when (requestCode) {
                        Operations.ADD -> input1 + input2
                        Operations.SUBTRACT -> input1 - input2
                        Operations.MULTIPLY -> input1 * input2
                        Operations.DIVIDE -> input1 / input2
                    }

                    resultTextView.apply {
                        text = "Result is ${findWhole(res)}\n" +
                                "for inputs ${findWhole(input1)} and ${findWhole(input2)}\n" +
                                "for operation - $requestCode" //res.toString()
                        visibility = View.VISIBLE
                    }
                    resetButton.visibility = View.VISIBLE

                    addButton.visibility = View.GONE
                    subButton.visibility = View.GONE
                    multiplyButton.visibility = View.GONE
                    divideButton.visibility = View.GONE
                }
            }
        val intent = Intent(this, InputActivity::class.java)

        addButton.setOnClickListener {
            intent.putExtra(REQUEST_CODE, Operations.ADD.toString())
            resultLauncher.launch(intent)
        }
        subButton.setOnClickListener {
            intent.putExtra(REQUEST_CODE, Operations.SUBTRACT.toString())
            resultLauncher.launch(intent)
        }
        multiplyButton.setOnClickListener {
            intent.putExtra(REQUEST_CODE, Operations.MULTIPLY.toString())
            resultLauncher.launch(intent)
        }
        divideButton.setOnClickListener {
            intent.putExtra(REQUEST_CODE, Operations.DIVIDE.toString())
            resultLauncher.launch(intent)
        }

        resetButton.setOnClickListener {
            recreate()
        }
    }

    private fun findWhole(n: Double) = if (Math.floor(n) == n) n.toInt() else n

}