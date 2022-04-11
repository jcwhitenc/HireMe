package com.cs356.hireme.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.cs356.hireme.R
import com.cs356.hireme.fragments.PositionFragment
import com.cs356.hireme.loadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private val loginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT)
        }
        else {
            System.out.println("Logged in!")
            showPositions()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkCurrentUser()
    }

    private fun showPositions() {
        val db = Firebase.firestore

        db.collection("positions").get().addOnSuccessListener { data ->
            if(data != null) {
                var positions = data.documents
                val position = positions[0]
                val url = position.data!!["image"].toString()
                loadImage(url) { image ->
                    var positionFragment = PositionFragment(positions, image, 0)

                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.main_fragment_container, positionFragment).commit()
                }
            }
        }
    }

    private fun checkCurrentUser() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            // No user is signed in
            val intent = Intent(this, LoginActivity::class.java)
            loginResult.launch(intent)
        }
        else {
            showPositions()
        }
    }
}