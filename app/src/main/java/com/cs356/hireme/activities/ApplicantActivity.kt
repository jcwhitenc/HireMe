package com.cs356.hireme.activities

import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.cs356.hireme.R

class ApplicantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appilcant_activity)
        val agePicker = findViewById<NumberPicker>(R.id.age_picker)
        val ageRange = 110 - 14
        agePicker.displayedValues = Array(ageRange) { i -> ((ageRange - i) + 14).toString() }
        agePicker.maxValue = ageRange - 1
        agePicker.minValue = 0
        agePicker.value = ageRange - 3
    }
}