package com.example.android.calculator

import android.content.Context
import android.content.Intent
import android.icu.number.Precision
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.math.MathUtils
import java.math.BigDecimal
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToLong

class PostActivityResult : ActivityResultContract<String, String?>() {
    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, InputActivity::class.java).apply {
            putExtra(MainActivity.req_code, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        if (intent == null)
            return null
        val requestCode: Operations =
            intent.getSerializableExtra(MainActivity.req_code) as Operations

        return if (resultCode == AppCompatActivity.RESULT_OK) {
            val input1 = intent.getDoubleExtra("Input1", 0.0)
            val input2 = intent.getDoubleExtra("Input2", 0.0)

            val res = when (requestCode) {
                Operations.ADD -> input1 + input2
                Operations.SUBTRACT -> input1 - input2
                Operations.MULTIPLY -> input1 * input2
                Operations.DIVIDE -> input1 / input2
            }

            "Result is ${findWhole(res)}\n" +
                    "for inputs ${findWhole(input1)} and ${findWhole(input2)}\n" +
                    "for operation - $requestCode"
        } else null
    }

    private fun findWhole(n: Double) = if (floor(n) == n) n.toInt() else n

}