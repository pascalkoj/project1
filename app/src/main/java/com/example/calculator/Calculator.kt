package com.example.calculator

import android.widget.TextView

class Calculator {

    private var mainTextView: TextView? = null
    private var display = ""
    private var nums = ArrayList<Float>()
    private var ops = ArrayList<Float>()
    //private var currentValue: Double = 0.0
    //private var previousValue: Double = 0.0
    private var clr = false
    private var currentOperator: String? = null
    private var decimalClicked: Boolean = false



    fun SetMainDisplay(mainTextView: TextView) {
        this.mainTextView = mainTextView
    }

    private fun updateDisplay() {
        mainTextView?.setText(display)
    }


    fun clear() {
        display = ""
        //currentValue = 0.0
        //previousValue = 0.0
        //currentOperator = null
        nums.clear()
        ops.clear()
        decimalClicked  = false
        updateDisplay()
    }

    fun negate() {
        //currentValue = -currentValue
        if (display[0] == '-'){display = display.substring(1)}
        else {display = '-'.plus(display)
        }
        updateDisplay()
        return
    }

    fun percent() {
        //currentValue /= 100
        var temp = display.toFloatOrNull()
        if (temp != null) {
            display = (temp / 100.0f).toString()
        }
        updateDisplay()
        return
    }

    /*fun setNumber(value: Double) {
        currentValue = value
    }*/

    fun decimal() {
        if (!decimalClicked) {
            //currentValue += 0.0 // Add decimal point

            decimalClicked = true
            updateDisplay()
        }
    }


    fun setOperator(operator: String) {
        currentOperator = operator
        previousValue = currentValue
        currentValue = 0.0
        decimalClicked = false
    }

    fun operate(text: String): Boolean {
        return text in arrayOf("+", "-", "*", "/")
    }



    fun calculate(toString: String): Double {
        var temp = display.toIntOrNull()
        when (currentOperator) {
            //do the operation of each button
            if (temp == null) {

                    } else {
                        ret
                }
            }
        }
    }

    fun PushButton(buttonText: String){
        when{
            buttonText == "C" -> clear()
            buttonText == "+/-" -> negate()
            buttonText == "%" -> percent()
            buttonText == "." -> decimal()
            operate(buttonText) -> setOperator(buttonText)
            else -> {
                if (decimalClicked) {
                    currentValue = (currentValue.toString() + buttonText).toDouble()
                } else {
                    currentValue = (currentValue * 10) + buttonText.toDouble()
                }
                updateDisplay()
            }
        }

    }

    companion object{
        var calc = Calculator()
        fun Instance(): Calculator{
            return calc
        }
    }
}
