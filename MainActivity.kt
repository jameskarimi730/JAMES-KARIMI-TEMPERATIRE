package com.example.tempconvert

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import java.util.Locale

class MainActivity : Activity() {

    // The conversion calculations are implemented in C.
    private external fun celsiusToFahrenheit(celsius: Double): Double
    private external fun fahrenheitToCelsius(fahrenheit: Double): Double

    companion object {
        init {
            System.loadLibrary("tempconvert")
        }
    }

    private val navy = Color.rgb(16, 26, 44)
    private val teal = Color.rgb(35, 190, 176)
    private val pale = Color.rgb(239, 247, 249)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildScreen()
    }

    private fun buildScreen() {
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(24), dp(34), dp(24), dp(24))
            setBackgroundColor(pale)
        }

        val title = TextView(this).apply {
            text = "🌡  TempConvert"
            textSize = 29f
            setTextColor(navy)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
        }
        page.addView(title, matchWrap())

        val subtitle = TextView(this).apply {
            text = "A simple temperature converter"
            textSize = 15f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
        }
        page.addView(subtitle, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(42)
        ))

        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(22), dp(20), dp(22))
            setBackgroundColor(Color.WHITE)
        }
        val cardParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { topMargin = dp(28) }
        page.addView(card, cardParams)

        val inputLabel = label("Enter temperature")
        card.addView(inputLabel, matchWrap())

        val input = EditText(this).apply {
            hint = "e.g. 25"
            textSize = 20f
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL or
                android.text.InputType.TYPE_NUMBER_FLAG_SIGNED
            setSingleLine(true)
        }
        card.addView(input, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(58)
        ))

        card.addView(label("Convert from"), LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(42)
        ).apply { topMargin = dp(12) })

        val choices = Spinner(this).apply {
            adapter = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_spinner_dropdown_item,
                listOf("Celsius (°C) to Fahrenheit (°F)", "Fahrenheit (°F) to Celsius (°C)")
            )
        }
        card.addView(choices, matchWrap())

        val result = TextView(this).apply {
            text = "Your result will appear here"
            textSize = 21f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(navy)
            gravity = Gravity.CENTER
            setPadding(dp(10), dp(22), dp(10), dp(22))
            setBackgroundColor(Color.rgb(230, 248, 245))
        }
        card.addView(result, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { topMargin = dp(22) })

        val convert = Button(this).apply {
            text = "CONVERT"
            setTextColor(Color.WHITE)
            setBackgroundColor(teal)
            textSize = 16f
            setOnClickListener {
                val raw = input.text.toString().trim()
                if (raw.isEmpty()) {
                    input.error = "Please enter a temperature"
                    result.text = "Enter a value to convert"
                    return@setOnClickListener
                }

                val value = raw.toDoubleOrNull()
                if (value == null || value.isNaN() || value.isInfinite()) {
                    input.error = "Enter a valid number"
                    result.text = "Invalid temperature"
                    return@setOnClickListener
                }

                val answer: Double
                val outputUnit: String
                if (choices.selectedItemPosition == 0) {
                    answer = celsiusToFahrenheit(value)
                    outputUnit = "°F"
                } else {
                    answer = fahrenheitToCelsius(value)
                    outputUnit = "°C"
                }
                result.text = String.format(Locale.getDefault(), "%.2f %s", answer, outputUnit)
            }
        }
        card.addView(convert, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(54)
        ).apply { topMargin = dp(18) })

        val clear = Button(this).apply {
            text = "CLEAR"
            setOnClickListener {
                input.text.clear()
                input.error = null
                result.text = "Your result will appear here"
                choices.setSelection(0)
            }
        }
        card.addView(clear, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(50)
        ).apply { topMargin = dp(8) })

        val formula = TextView(this).apply {
            text = "Celsius to Fahrenheit: (°C × 9/5) + 32\nFahrenheit to Celsius: (°F − 32) × 5/9"
            textSize = 13f
            setTextColor(Color.DKGRAY)
            gravity = Gravity.CENTER
            setPadding(dp(8), dp(22), dp(8), dp(8))
        }
        page.addView(formula, matchWrap())

        val footer = TextView(this).apply {
            text = "Built with C and Kotlin"
            textSize = 12f
            setTextColor(Color.GRAY)
            gravity = Gravity.CENTER
        }
        page.addView(footer, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(48)
        ))

        setContentView(page)
    }

    private fun label(value: String) = TextView(this).apply {
        text = value
        textSize = 15f
        setTextColor(navy)
        typeface = Typeface.DEFAULT_BOLD
    }

    private fun matchWrap() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
