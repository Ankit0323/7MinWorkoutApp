package com.example.a7minuteworkout

import android.media.MediaDrm
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

/*
* important point
*  <com.google.android.material.textfield.TextInputLayout
* is not working because
* we initialize app compat no action bar theme
* but we have to initialize
* material component. no action bar */
class BmiActivity : AppCompatActivity() {
    val metricSystem:String="metric_unit"
    val usSystem:String="us_unit"
    var checkUnit:String=metricSystem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)
        setSupportActionBar(bmiToolBar)
       val actionBar=supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
actionBar.title="Calculate BMI"
        }
        bmiToolBar?.setNavigationOnClickListener {
            onBackPressed()
        }
        bmiButton.setOnClickListener {
            if(checkUnit==metricSystem) {

                if (checkValidity()) {
                    val myWeight = weight.text.toString().toFloat()
                    val myHeight = height.text.toString().toFloat() / 100
                    val bmi = myWeight / (myHeight * myHeight)
                    checkBmi(bmi)


                    ll_id.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this, "Please enter correct  values", Toast.LENGTH_SHORT).show()
                }
            }else{

                if (checkValidityUs()) {
                    val myWeight = usWeight.text.toString().toFloat()
                    val myFeet = feet.text.toString().toFloat()
                    val myInch=inch.text.toString().toFloat()
                    val myHeight=myInch+(myFeet*12)
                    val bmi =703* myWeight / (myHeight * myHeight)
                    checkBmi(bmi)


                    ll_id.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this, "Please enter correct values", Toast.LENGTH_SHORT).show()
                }

            }
        }


radio_group.setOnCheckedChangeListener { group, checkedId ->
    if(checkedId==R.id.metric){
        isMetric()

    }
    else{
        isUs()
    }
}

    }
    fun checkBmi(bmi:Float){
        val label:String
        val description:String
        if(bmi.compareTo(15f)<=0){
            label="Severly underweight"
            description="You really need to take care of yourself! Eat more"


        }
        else if(bmi.compareTo(15f)>0 && bmi.compareTo(18.5f)<0){
            label="Underweight"
            description="You need to take care of yourself! Eat more"


        }
        else if(bmi.compareTo(18.5f)>0 && bmi.compareTo(25f)<0){
            label="Normal"
            description="Congartulation! You are in good shape"
        }
        else if(bmi.compareTo(25f)>0  &&  bmi.compareTo(30f)<0){
            label="overweight"
            description="You need to take care of yourself! Workout more "
        }
        else {
            label = "Obese"
            description = "You are in dangerous condition!Act now"

        }

        thirdText.text=label
        fourthText.text=description
       val bmiRoundOff=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        secondText.text = bmiRoundOff.toString()
    }
    fun checkValidity():Boolean{
        var isValid=true
        if(weight.text!!.isEmpty() || height.text!!.isEmpty()){
            
            isValid=false
        }
        return isValid
    }
    fun checkValidityUs():Boolean{
        var isValid=true
        if(usWeight.text!!.isEmpty() || feet.text!!.isEmpty()|| inch.text!!.isEmpty()){

            isValid=false
        }
        return isValid
    }
    fun isMetric() {
        checkUnit = metricSystem
        llmetricSystem.visibility = View.VISIBLE
        llusSystem.visibility = View.GONE
        weight.text!!.clear()
        height.text!!.clear()
    }
    fun isUs() {
        checkUnit = usSystem
        llmetricSystem.visibility = View.GONE
        llusSystem.visibility = View.VISIBLE
        usWeight.text!!.clear()
        feet.text!!.clear()
        inch.text!!.clear()
    }


}