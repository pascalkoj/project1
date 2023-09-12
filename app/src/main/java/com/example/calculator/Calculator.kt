package com.example.calculator

import android.util.Log
import android.widget.TextView
import java.lang.Math
import kotlin.jvm.internal.Intrinsics.Kotlin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan
import kotlin.math.ln
import kotlin.math.log10
import java.util.LinkedList
import java.util.Queue
import java.util.Stack
import kotlin.math.ln


class Calculator {
    // storage for numbers we're operating on
    var numBuffer = ArrayList<Float>()
    // storage for operands we will apply to numbers
    var opBuffer = ArrayList<String>()
    // text displayed on calculator textview
    var display = ""
    var shouldClearDisplayInput = true
    // main textview of the calculator
    var mainTextView: TextView? = null

    var justAddedOp = false
    var justComputedOp = false

    // cache reference to calculator textview for text display
    fun SetMainDisplay(mainTextView: TextView)
    {
        this.mainTextView = mainTextView
    }
    // actually update the textview
    fun DisplayUpdate()
    {
        mainTextView?.setText(display)
    }

    // clear all buffered operations/numbers and clear display
    fun Clear() {
        display = ""
        numBuffer.clear()
        opBuffer.clear()
        DisplayUpdate()
    }
    // overload to take single chars too
    fun PushOp(op: Char) {
        PushOp(op.toString())
    }

    // main logic, take some "op" (operation - could be a number or a symbol) cache and process it
    fun PushOp(op: String) {
        Log.v("[DEBUG]","Button pushed: $op");
        // C is clear
        if (op == "C")
        {
            Clear()
            return
        }

        // flip sign
        else if (op == "+/-")
        {
            if (display[0] == '-') { display = display.substring(1) }
            else { display = '-'.plus(display) }
            DisplayUpdate()
            return
        }
        else if (op == ".")
        {
            display = display.plus(".");
            DisplayUpdate()
            return
        }
        // percentage -> divide by 100
        else if (op == "%")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                display = (tryParseNum / 100.0f).toString()
            }
            DisplayUpdate()
            return
        }
        else if (op == "cos")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                display = (Math.cos(tryParseNum.toDouble())).toString()
            }
            DisplayUpdate()
            return
        }
        else if (op == "sin")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                display = (Math.sin(tryParseNum.toDouble())).toString()
            }
            DisplayUpdate()
            return
        }
        else if (op == "tan")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                display = (Math.tan(tryParseNum.toDouble())).toString()
            }
            DisplayUpdate()
            return
        }
        else if (op == "ln")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                if (tryParseNum > 0)
                    display = (ln(tryParseNum.toDouble())).toString()
            }
            DisplayUpdate()
            return
        }
        else if (op == "Log 10")
        {
            var tryParseNum = display.toFloatOrNull()
            if (tryParseNum != null)
            {
                if (tryParseNum > 0)
                    display = (Math.log10(tryParseNum.toDouble())).toString()
            }
            DisplayUpdate()
            return
        }

        var num = op.toIntOrNull()
        if (num == null)
        {
            // add current display number to num buf
            var tryParseFullDisplayNum = display.toFloatOrNull()
            if (tryParseFullDisplayNum == null)
            {
                // error state, display should only ever display numbers
                Log.e("[ERROR]", "invalid display result")
                return
            }
            else if (!justComputedOp && !justAddedOp)
            {
                // take the number on the display and make it a num, then add to buf
                numBuffer.add(tryParseFullDisplayNum)
                println("added " + tryParseFullDisplayNum + " to num buf")
            }
            if (op != "=" && op != "." && !justAddedOp)
            {
                opBuffer.add(op)
                justAddedOp = true
                justComputedOp = false
                println("added " + op + " to op buf")
            }
            if (numBuffer.size > 1 && opBuffer.isNotEmpty())
            {
                DoOperation()
            }
        }
        else
        {
            if (justAddedOp)
            {
                display = ""
                justAddedOp = false
                println("Clear display")
            }
            display = display.plus(op)
            justComputedOp = false
        }




        /*
                // see if our op is a number or a symbol
                var tryParseNum = op.toIntOrNull()
                if (tryParseNum == null)
                {
                    // op (I.E. +, -, /, etc)
                    // take current number on screen and add to num buffer
                    var tryParseFullDisplayNum = display.toFloatOrNull()
                    if (tryParseFullDisplayNum == null)
                    {
                        // error state, display should only ever display numbers
                        println("invalid display result")
                        return
                    }
                    else
                    {
                        // take the number on the display and make it a num, then add to buf
                        numBuffer.add(tryParseFullDisplayNum)
                    }


                    // add ops to buffer if we aren't computing a result
                    if (op != "=")
                    {
                        if (!opBuffer.isEmpty())
                        {
                            DoOperation(op);
                        }
                        opBuffer.add(op)
                    }
                    if (numBuffer.size == 2)
                    {
                        // we have more than 1 number, and can apply the operation
                        var operation: String? = ""
                        if (op == "=")
                        {
                            operation = opBuffer.removeLastOrNull()
                        }
                        if (operation != null && operation.isNotEmpty())
                        {
                            // actually apply the operation and display the result
                            DoOperation(operation);
                        }
                    }
                }
                else
                {
                    if (!opBuffer.isEmpty() || shouldClearDisplayInput)
                    {
                        // only clear previous number once we enter another number
                        display = ""
                        shouldClearDisplayInput = false
                    }
                    // number
                    display = display.plus(op)
                }
                */
        DisplayUpdate()

        println("numBuf = $numBuffer  opBuf = $opBuffer")
    }

    fun DoOperation(operation: String)
    {
        var opResult = ApplyOp(numBuffer[0], numBuffer[1], operation)
        display = opResult.toString()
        //numBuffer.clear()
        //opBuffer.clear()
        shouldClearDisplayInput = true
    }
    fun DoOperation()
    {
        println("Doing operation... numBuf = $numBuffer  opBuf = $opBuffer")
        var op1 = numBuffer.removeFirst()
        var op2 = numBuffer.removeFirst()
        var operation: String = opBuffer.removeFirstOrNull() ?: return
        var opResult = ApplyOp(op1, op2, operation)
        numBuffer.add(opResult)
        println("result = $opResult")
        println("bufs after operation: numBuf = $numBuffer  opBuf = $opBuffer")
        display = opResult.toString()
        //numBuffer.clear()
        //opBuffer.clear()
        shouldClearDisplayInput = true
        justComputedOp = true
    }
    fun ApplyOp(num1: Float, num2: Float, op: String) : Float
    {
        var result = 0.0f
        if (op == "+")
        {
            result = (num1+num2)
        }
        else if (op == "-")
        {
            result = (num1-num2)
        }
        else if (op == "*" || op == "x")
        {
            result = (num1*num2)
        }
        else if (op == "/")
        {
            result = (num1/num2)
        }
        return result
    }

    companion object {
        var calc = Calculator()
        fun Instance(): Calculator {
            return calc
        }
    }


    fun calculate(s: String): Int {
        return evaluatePostfix(infixToPostfix(s))
    }

    private fun rank(op: Char): Int {
        return when {
            op == '+' || op == '-' -> 1
            op == '*' || op == '/' -> 2
            else -> 0
        }
    }

    private fun infixToPostfix(infixExp: String): Queue<String> {
        val ans: Queue<String> = LinkedList()
        val stack: Stack<Char> = Stack()
        var i = 0
        val len = infixExp.length
        while (i < len) {
            val num = StringBuilder()
            while (i < len && infixExp[i].isDigit()) num.append(infixExp[i++])
            if (num.isNotEmpty()) ans.add(num.toString())
            if (i == len) break
            val ch = infixExp[i++]
            when {
                ch.isWhitespace() -> {}
                else -> {
                    while (stack.isNotEmpty() && rank(stack.peek()) >= rank(ch)) {
                        ans.add("${stack.pop()}")
                    }
                    stack.push(ch)
                }
            }
        }
        while (stack.isNotEmpty()) {
            ans.add("${stack.pop()}")
        }
        return ans
    }

    private fun evaluatePostfix(postfix: Queue<String>): Int {
        val stack: Stack<Long> = Stack()
        for (exp in postfix) {
            if (exp[0].isDigit())
                stack.push(exp.toLong())
            else {
                val b = stack.pop()
                val a = stack.pop()
                when (exp[0]) {
                    '+' -> stack.push(a + b)
                    '-' -> stack.push(a - b)
                    '*' -> stack.push(a * b)
                    '/' -> stack.push(a / b)
                    else -> stack.push(0)
                }
            }
        }
        return stack.pop().toInt()
    }
}




