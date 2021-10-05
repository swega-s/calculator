package com.example.android.calculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat
import androidx.core.view.children
import com.example.android.calculator.databinding.ActivityInputBinding
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_input.*

class InputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_input)
        val REQUEST_CODE = "REQUEST_CODE"

        val data = intent.getStringExtra(REQUEST_CODE)
        opTypeTextView.text = data.toString()
        opButton.text = data.toString()

        opButton.setOnClickListener {
            val val1 = input1.text.toString()
            val val2 = input2.text.toString()

            if (val1 == "" || val2 == "") {
                input1.text.clear()
                input2.text.clear()
                Toast.makeText(this, "enter both values!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            try {
                val value1 = val1.toDouble()
                val value2 = val2.toDouble()

                Intent().also {
                    it.putExtra(REQUEST_CODE, Operations.valueOf(data!!))
                    it.putExtra("Input1", value1)
                    it.putExtra("Input2", value2)
                    setResult(RESULT_OK, it)
                    finish()
                }
            } catch (exception: Exception) {
                input1.text.clear()
                input2.text.clear()
                Toast.makeText(this, "enter valid values!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }
    }

}