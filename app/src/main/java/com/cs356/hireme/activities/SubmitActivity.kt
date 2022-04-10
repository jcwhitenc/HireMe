package com.cs356.hireme.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cs356.hireme.R

class SubmitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)

        var continueButton: Button = findViewById(R.id.continue_button)
        continueButton.setOnClickListener {
            // leave this activity
            this.finish()
        }
    }
}