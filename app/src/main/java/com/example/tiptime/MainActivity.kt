package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener{calculatorTip()}
        binding.costOfServiceEditText.setOnKeyListener{view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun calculatorTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null || cost == 0.0){
            binding.tipResult.text = ""
            return
        }
        val tipPercentage= when(binding.tipOption.checkedRadioButtonId){
            R.id.twenty_percent_option -> 0.20
            R.id.option_eigthteen_percent ->0.18
            else -> 0.15
        }

        var tip = tipPercentage * cost
        if(binding.roundUpSwitch.isChecked){
            tip= ceil(tip)
        }
        val formattedNumber = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedNumber)
    }
    private fun handleKeyEvent(view:View, keyCode:Int):Boolean{
        if (keyCode == KeyEvent.KEYCODE_ENTER){
            //hide the keyboard
            val hide = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hide.hideSoftInputFromWindow(view.windowToken,0)
        return true
        }
        return false
    }
}