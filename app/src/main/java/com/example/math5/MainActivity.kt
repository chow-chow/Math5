package com.example.math5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import com.example.math5.const.Options
import com.example.math5.databinding.ActivityMainBinding
import kotlin.math.sqrt

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creating an ArrayAdapter from the string-array and defining the default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.formulas_array,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.dropdown_item)
            binding.spinner.adapter = adapter
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    Options.QUADRATIC -> { displayQuadratic() }
                    Options.SIMULTANEOUS -> { displaySimultaneous() }
                    Options.FIRST_DEGREE -> { displayFirstDegree() }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.calculate.setOnClickListener {
            when (binding.spinner.selectedItemPosition) {
                Options.QUADRATIC -> { quadraticSolutions() }
                Options.SIMULTANEOUS -> { simultaneousSolution() }
                Options.FIRST_DEGREE -> { firstDegreeSolution() }
            }
        }
    }

    private fun firstDegreeSolution() {
        val aValue = findViewById<EditText>(R.id.a_input).text.toString().toDouble()
        val bValue = findViewById<EditText>(R.id.b_input).text.toString().toDouble()

        if (aValue == 0.0) {
            binding.result.text = getString(R.string.invalid_firstdegree)
        } else {
            val xResult = -bValue / aValue
            val formattedResult = String.format("%.3f", xResult)
            Log.d(TAG, "xResult = $xResult")
            binding.result.text = getString(R.string.result_firstdegree, formattedResult)
        }
    }

    private fun simultaneousSolution() {
        val a1Value = findViewById<EditText>(R.id.a1_input).text.toString().toDouble()
        val a2Value = findViewById<EditText>(R.id.a2_input).text.toString().toDouble()
        val b1Value = findViewById<EditText>(R.id.b1_input).text.toString().toDouble()
        val b2Value = findViewById<EditText>(R.id.b2_input).text.toString().toDouble()
        val c1Value = findViewById<EditText>(R.id.c1_input).text.toString().toDouble()
        val c2Value = findViewById<EditText>(R.id.c2_input).text.toString().toDouble()

        val determinant = a1Value * b2Value - a2Value * b1Value

        if (determinant == 0.0) {
            binding.result.text = getString(R.string.no_solutions)
        } else {
            val x = (c1Value * b2Value - c2Value * b1Value) / determinant
            val y = (a1Value * c2Value - a2Value * c1Value) / determinant
            val formattedX = String.format("%.3f", x)
            val formattedY = String.format("%.3f", y)
            binding.result.text = getString(R.string.solutions, formattedX, formattedY)
        }
    }

    private fun quadraticSolutions() {
        val aValue = findViewById<EditText>(R.id.a_input).text.toString().toDouble()
        val bValue = findViewById<EditText>(R.id.b_input).text.toString().toDouble()
        val cValue = findViewById<EditText>(R.id.c_input).text.toString().toDouble()

        val discriminant = bValue * bValue - 4 * aValue * cValue

        if (aValue == 0.0) {
            binding.result.text = getString(R.string.invalid_quadratic)
        } else if (discriminant > 0.0) {
            val x1 = (-bValue + sqrt(discriminant)) / (2 * aValue)
            val x2 = (-bValue - sqrt(discriminant)) / (2 * aValue)
            val formattedX1 = String.format("%.3f", x1)
            val formattedX2 = String.format("%.3f", x2)
            binding.result.text = getString(R.string.quadratic_solutions, formattedX1, formattedX2)
        } else if (discriminant == 0.0) {
            val x = -bValue / (2 * aValue)
            val formattedRes = String.format("%.3f", x)
            binding.result.text = getString(R.string.result_firstdegree, formattedRes)
        } else {
            binding.result.text = getString(R.string.imaginary_solutions)
        }
    }

    private fun editTextListeners(
        requiredEditTexts: List<EditText>
    ) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.calculate.isEnabled = requiredEditTexts.all { it.text.isNotEmpty() }
            }
        }
        requiredEditTexts.forEach { it.addTextChangedListener(textWatcher) }
    }

    private fun displayFirstDegree() {
        binding.formula.setImageResource(R.drawable.firstdegree)
        binding.container.removeAllViews()
        LayoutInflater.from(this).inflate(R.layout.firstdegree_layout, binding.container, true)
        val aEdit = findViewById<EditText>(R.id.a_input)
        val bEdit = findViewById<EditText>(R.id.b_input)
        val requiredEditTexts = listOf(aEdit, bEdit)
        editTextListeners(requiredEditTexts)
    }

    private fun displaySimultaneous() {
        binding.formula.setImageResource(R.drawable.simultaneous)
        binding.container.removeAllViews()
        LayoutInflater.from(this).inflate(R.layout.simultaneous_layout, binding.container, true)
        val a1Edit = findViewById<EditText>(R.id.a1_input)
        val a2Edit = findViewById<EditText>(R.id.a2_input)
        val b1Edit = findViewById<EditText>(R.id.b1_input)
        val b2Edit = findViewById<EditText>(R.id.b2_input)
        val c1Edit = findViewById<EditText>(R.id.c1_input)
        val c2Edit = findViewById<EditText>(R.id.c2_input)
        val requiredEditTexts = listOf(a1Edit, a2Edit, b1Edit, b2Edit, c1Edit, c2Edit)
        editTextListeners(requiredEditTexts)
    }

    private fun displayQuadratic() {
        binding.formula.setImageResource(R.drawable.quadratic)
        binding.container.removeAllViews()
        LayoutInflater.from(this).inflate(R.layout.quadratic_layout, binding.container, true)
        val aEdit = findViewById<EditText>(R.id.a_input)
        val bEdit = findViewById<EditText>(R.id.b_input)
        val cEdit = findViewById<EditText>(R.id.c_input)
        val requiredEditTexts = listOf(aEdit, bEdit, cEdit)
        editTextListeners(requiredEditTexts)
    }
}