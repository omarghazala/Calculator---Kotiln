package com.example.minicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

enum class CalculatorMode{
    None,Add,Subtract,Multiply
}
class MainActivity : AppCompatActivity() {
    var lastButtonWasMode = false
    var currentMode = CalculatorMode.None
    var labelString = ""
    var saveNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setupCalculator()
    }
    private fun setupCalculator(){
        val allButtons = arrayOf(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9)
        for(i in allButtons.indices){
            allButtons[i].setOnClickListener { didPressNumber(i) }
        }
        buttonAdd.setOnClickListener { changeMode(CalculatorMode.Add) }
        buttonSub.setOnClickListener { changeMode(CalculatorMode.Subtract) }
        buttonMultiply.setOnClickListener { changeMode(CalculatorMode.Multiply) }
        buttonEquals.setOnClickListener { didPressEquals() }
        buttonClear.setOnClickListener { didPressClear() }
    }
    private fun didPressEquals(){
        if(lastButtonWasMode){
            return
        }
        val labelInt = labelString.toInt()

        when(currentMode){
            CalculatorMode.Add -> {saveNum=saveNum +labelInt }
            CalculatorMode.Subtract ->{saveNum -= labelInt}
            CalculatorMode.Multiply ->{saveNum *= labelInt}
            CalculatorMode.None -> return
        }

        currentMode = CalculatorMode.None
        labelString = "$saveNum"
        updateText()
        lastButtonWasMode = true
    }
    private fun didPressClear(){
        lastButtonWasMode = false
        currentMode = CalculatorMode.None
        labelString = ""
        saveNum = 0
        textView.text="0"
    }
    private fun updateText(){
        if(labelString.length>5){
            didPressClear()
            textView.text="Too Big"
            return
        }
        var labelInt = labelString.toInt()
        labelString = labelInt.toString()

        if(currentMode == CalculatorMode.None){
            saveNum = labelInt
        }

        val df = DecimalFormat("#,###")

        textView.text=df.format(labelInt)
    }
    private fun changeMode(mode:CalculatorMode){
        if(saveNum==0){
            return
        }
        currentMode = mode
        lastButtonWasMode = true

    }
    private fun didPressNumber(number:Int){
        val strVal = number.toString()
        if(lastButtonWasMode){
            lastButtonWasMode = false
            labelString="0"
        }
        labelString = "$labelString$strVal"
        updateText()
    }

}
