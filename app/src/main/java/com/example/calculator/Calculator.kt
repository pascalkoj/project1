package com.example.calculator

import android.widget.TextView

class Calculator {

    private var display = ""
    private var nums = ArrayList<Float>()
    private var ops = ArrayList<String>()
    private var clear = false
    private var mainTextView: TextView? = null
    //private var currentOperator: String? = null
    //private var decimalClicked: Boolean = false



    fun SetMainDisplay(mainTextView: TextView) {
        this.mainTextView = mainTextView
    }

    fun updateDisplay() {
        mainTextView?.setText(display)
    }


    fun clear() {
        display = ""
        nums.clear()
        ops.clear()
        updateDisplay()
    }

    //not using below methods anymore they were giving trouble with updating display and executing the math correctly
    /*fun negate() {
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
    }*/

    /*fun setNumber(value: Double) {
        currentValue = value
    }*/

    /*fun decimal() {
        if (!decimalClicked) {
            //currentValue += 0.0 // Add decimal point

            decimalClicked = true
            updateDisplay()
        }
    }*/


    fun calculate(operator: Char) {
        calculate(operator.toString())
    }



    fun calculate(operator: String) { //always update display after each operation this was biggest issue
        if (operator == "C"){ //clearing display
            clear()
            return
        }



        if (operator == "+/-"){ //negating
            if (display[0] == '-'){
                display = display.substring(1)
            }
            else {display = '-'.plus(display)}
            updateDisplay()
            return
        }




        if (operator == "%"){ //percent aka div by 100
            display = (display.toInt()/100).toString()
            updateDisplay()
            return
        }


        var type = operator.toIntOrNull() //checks if user clicks a number or an operator number will returm int and opertor will return null
        if (type == null) { //means user pressed an operator button

            //check to see what's stored already
            var checkDisplay = display.toFloatOrNull()

            if (checkDisplay == null){ //nothing in display
                println("invalid")
                return  }
            else { // store in num array to keep track of nums pressed until = sign
                nums.add(checkDisplay) }


            if (operator != "="){ //user clicks on any operator other than equals meaning they are not finished with their math
                ops.add(operator) }



            if (nums.size == 2){ //this is where we execute the operation since the user has input 2 numbers and an operation
                var op : String? = ""

                if (operator == "="){
                    op = ops.removeLastOrNull()
                }

                if (op != null){ //where we actually operate and print the result in display
                    var executed = apply(nums.get(0), nums.get(1), op) //execute
                    display = executed //change the display to the result
                    nums.clear() //clear the arrays so you can restart the math from scratch with each formula
                    ops.clear()
                    clear = true //clear the display
                }
            }
        }


        else{
            if (!ops.isEmpty() && clear){
                display = ""
                clear = false
            }
            display = display.plus(operator)
        }

        updateDisplay()
        println(this.nums)
        println(this.ops)

    }




    fun apply(num1: Float, num2: Float, operator: String): String {
        var res = "invalid operator"
        if (operator == "+") {
            res = (num1+num2).toString()
        }
        else if (operator == "-") {
            res = (num1-num2).toString()
        }
        else if (operator == "*") {
            res = (num1*num2).toString()
        }
        else if (operator == "/") {
            res = if (num2.toInt() == 0) {
                "Error"
            } else {
                (num1 / num2).toString()
            }
        }
        if (res.endsWith(".0")) {
            res = res.split(".")[0].toInt().toString()
        }
        return res
    }



    companion object{
        var calc = Calculator()
        fun Instance(): Calculator{
            return calc
        }
    }
}




