package com.cs356.hireme

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //checkCurrentUser()

        // Image clicked
        val companyImage = findViewById<ImageButton>(R.id.company_image)
        companyImage.setOnClickListener {
            showBottomSheetDialog()
        }

        // Set up the profile button
        val profileButton = findViewById<ImageButton>(R.id.profile_button)
        profileButton.setOnClickListener{
            // Put up the Profile Fragments
            Toast.makeText(this, "Show Profile", Toast.LENGTH_SHORT).show()
        }

        // accept button
        val acceptButton = findViewById<TextView>(R.id.accept_button)
        acceptButton.setOnClickListener {
            acceptCompany()
        }

        // reject button
        val rejectButton = findViewById<TextView>(R.id.reject_button)
        rejectButton.setOnClickListener {
            rejectCompany()
        }
    }

    private fun acceptCompany() {
        getNewCompany()
    }

    private fun getNewCompany() {
        Toast.makeText(this,"Getting new Company", Toast.LENGTH_SHORT).show()
    }

    private fun rejectCompany() {
        getNewCompany()
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog)
        bottomSheetDialog.show()
    }

    private fun checkCurrentUser() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            // No user is signed in
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}