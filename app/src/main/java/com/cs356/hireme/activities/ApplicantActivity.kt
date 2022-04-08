package com.cs356.hireme.activities

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cs356.hireme.R
import com.google.android.material.slider.Slider

class ApplicantActivity : AppCompatActivity() {
    companion object {
        const val AGE = "age"
        const val SEX = "sex"
        const val ADDRESS = "address"
        const val JOB_TYPE = "job_type"
        const val MAX_DISTANCE = "max_distance"
        const val MIN_PAY = "minimum_play"
        const val FULL_NAME = "full_name"
        const val SHARED_PREFERENCES = "shared_preferences"
    }

    private lateinit var submitButton: Button
    private lateinit var preferredMinPaySlider: Slider
    private lateinit var preferredDistanceSlider: Slider
    private lateinit var jobRadioGroup: RadioGroup
    private lateinit var addressView: EditText
    private lateinit var sexRadioGroup: RadioGroup
    private lateinit var fullNameView: EditText
    private lateinit var agePicker: NumberPicker

    private var minPrefPay = 10F
    private var maxPrefDist = 0.5F
    private var userAge: Int = 0
    private var userSex = "OTHER"
    private var userJobType = "Part-Time"
    private var userAddress = "No Address"
    private var userFullName = "No Name"
    private var jobType = "Job Type"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appilcant_activity)

        //set up settings for the age picker
        agePicker = findViewById(R.id.age_picker)
        val ageRange = 110 - 14
        agePicker.displayedValues = Array(ageRange) { i -> ((ageRange - i) + 14).toString() }
        agePicker.maxValue = ageRange - 1
        agePicker.minValue = 0
        agePicker.value = ageRange - 3
        userAge = agePicker.value

        fullNameView = findViewById(R.id.name)
        sexRadioGroup = findViewById(R.id.sex_radio_group)
        sexRadioGroup.setOnCheckedChangeListener { _, id ->
            userSex = when (id) {
                R.id.radio_male -> "Male"
                R.id.radio_female -> "Female"
                else -> "Other"
            }
        }

        addressView = findViewById(R.id.address)
        jobRadioGroup = findViewById(R.id.job_type_group)
        jobRadioGroup.setOnCheckedChangeListener { _, id ->
            jobType = when (id) {
                R.id.part_time -> "Part-Time"
                else -> "Full-Time"
            }
        }

        preferredDistanceSlider = findViewById(R.id.max_distance_slider)
        preferredMinPaySlider = findViewById(R.id.min_pay_slider)
        submitButton = findViewById(R.id.submit_button)
        submitButton.setOnClickListener {
            // leave this activity
            finishActivity(RESULT_OK)
        }

    }

    override fun onPause() {
        super.onPause()
        // set the values from shared preferences
        val sharedPref = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(FULL_NAME, fullNameView.text.toString())
        editor.putInt(AGE, userAge)
        editor.putString(SEX, userSex)
        editor.putString(ADDRESS, addressView.text.toString())
        editor.putString(JOB_TYPE, jobType)
        editor.putFloat(MAX_DISTANCE, preferredDistanceSlider.value)
        editor.putFloat(MIN_PAY, preferredMinPaySlider.value)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()

        val sharedPref = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
        userFullName = sharedPref.getString(FULL_NAME, fullNameView.text.toString()) ?: "No Name"
        userAge = sharedPref.getInt(AGE, userAge)
        userSex = sharedPref.getString(SEX, userSex) ?: "Other"
        userAddress = sharedPref.getString(ADDRESS, addressView.text.toString()) ?: "No Address"
        userJobType = sharedPref.getString(JOB_TYPE, jobType) ?: "Part-Time"
        maxPrefDist = sharedPref.getFloat(MAX_DISTANCE, preferredDistanceSlider.value)
        minPrefPay = sharedPref.getFloat(MIN_PAY, preferredMinPaySlider.value)

        fullNameView.setText(userFullName)
        agePicker.value = userAge

        val maleButton = findViewById<RadioButton>(R.id.radio_male)
        val femaleButton = findViewById<RadioButton>(R.id.radio_female)
        val otherButton = findViewById<RadioButton>(R.id.radio_other)
        maleButton.isChecked = false
        femaleButton.isChecked = false
        otherButton.isChecked = false
        when (userSex) {
            "Male" -> {
                maleButton.isChecked = true
            }
            "Female" -> {
                femaleButton.isChecked = true
            }
            else -> {
                otherButton.isChecked = true
            }
        }

    }

    private fun verify() {
        // verify that everything has a value
    }
}