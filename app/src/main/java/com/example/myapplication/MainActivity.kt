package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {

    var arg1 = 0.0
    var arg2 = 0.0
    var state = 0

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            result.text = savedInstanceState.getString("result")
            operator.text = savedInstanceState.getString("operator")
            arg1 = savedInstanceState.getDouble("arg1")
            arg2 = savedInstanceState.getDouble("arg2")
            state = savedInstanceState.getInt("state")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonSetting()
    }

    private fun buttonSetting(){
        button0.setOnClickListener { numberButtonSetting(button0) }
        button1.setOnClickListener { numberButtonSetting(button1) }
        button2.setOnClickListener { numberButtonSetting(button2) }
        button3.setOnClickListener { numberButtonSetting(button3) }
        button4.setOnClickListener { numberButtonSetting(button4) }
        button5.setOnClickListener { numberButtonSetting(button5) }
        button6.setOnClickListener { numberButtonSetting(button6) }
        button7.setOnClickListener { numberButtonSetting(button7) }
        button8.setOnClickListener { numberButtonSetting(button8) }
        button9.setOnClickListener { numberButtonSetting(button9) }

        buttonClear.setOnClickListener { state = 0; result.text = "0"; operator.text = "" }

        buttonEqual.setOnClickListener { operatorButtonSetting(0,"") }
        buttonMinus.setOnClickListener { operatorButtonSetting(1,"-") }
        buttonMultiply.setOnClickListener { operatorButtonSetting(1,"x") }
        buttonPlus.setOnClickListener { operatorButtonSetting(1,"+") }
        buttonDivision.setOnClickListener { operatorButtonSetting(1,"/") }

        buttonDot.setOnClickListener { DotButtonSetting(buttonDot) }
        buttonSign.setOnClickListener { SignButtonSetting() }
        buttonPercent.setOnClickListener { compareIntAndDouble(result.text.toString().toDouble()/100) }
    }

    private fun numberButtonSetting(button : Button){
        if(state != 1) if (result.text != "0" && result.text != "오류") result.text="${result.text}${button.text}" else result.text="${button.text}"
        else {
            result.text = "${button.text}"
            state = 2
        }
    }

    private fun operatorButtonSetting(nextState : Int, operate : String){
        if(state < 2) {
            state = nextState
            arg1 = result.text.toString().toDouble()
        }
        else if (state ==2){
            arg2 = result.text.toString().toDouble()
            calculate()
            state = nextState
        }
        operator.text = operate
    }

    private fun calculate (){
        var resultValue = 0.0
        when(operator.text){
            "+" -> resultValue = (arg1 + arg2)
            "-" -> resultValue = (arg1 - arg2)
            "x" -> resultValue = (arg1 * arg2)
            "/" -> if (arg2 != 0.0) resultValue = (arg1 / arg2) else result.text = "오류"
        }
        compareIntAndDouble(resultValue)
    }

    private fun compareIntAndDouble(resultValue : Double){
        if (result.text != "오류"){
            if(resultValue-resultValue.toInt()!=0.0) result.text = resultValue.toString()
            else result.text = resultValue.toInt().toString()
        }
    }

    private fun DotButtonSetting(button : Button){
        if(state != 1) if (result.text != "오류") result.text="${result.text}${button.text}" else result.text="0${button.text}"
        else {
            result.text = "0${button.text}"
            state = 2
        }
    }

    private fun SignButtonSetting(){
        var nowValue = result.text.toString().toDouble()
        if(state != 1) if (result.text != "오류") if(nowValue<0)compareIntAndDouble(nowValue.absoluteValue) else compareIntAndDouble(nowValue*-1)
        else {
            result.text = "-0"
            state = 2
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("result", "${result.text}")
        outState?.putString("operator", "${operator.text}")
        outState?.putDouble("arg1", arg1);
        outState?.putDouble("arg2", arg2);
        outState?.putInt("state", state);
    }
}
