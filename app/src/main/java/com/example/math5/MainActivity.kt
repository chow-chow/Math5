package com.example.math5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.math5.const.Options
import com.example.math5.databinding.ActivityMainBinding

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
                Toast.makeText(this@MainActivity,
                    "You selected ${parent?.getItemAtPosition(position).toString()} at position $position",
                    Toast.LENGTH_LONG).show()
                when (position) {
                    Options.QUADRATIC -> { displayQuadratic() }
                    Options.SIMULTANEOUS -> { displaySimultaneous() }
                    Options.OHMLAW -> { displayOhmLaw() }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        Log.d(TAG, "SelectedItem: ${binding.spinner.selectedItemPosition}")
    }

    private fun displayOhmLaw() {
        TODO("Not yet implemented")
    }

    private fun displaySimultaneous() {
        TODO("Not yet implemented")
    }

    private fun displayQuadratic() {
        val quadraticLayout = LayoutInflater.from(this).inflate(R.layout.quadratic_layout, null)
        binding.formula.setImageResource(R.drawable.quadratic_equationv2)
        binding.container.removeAllViews()
        binding.container.addView(quadraticLayout)
    }
}