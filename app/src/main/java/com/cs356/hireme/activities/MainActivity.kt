package com.cs356.hireme.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cs356.hireme.R
import com.cs356.hireme.fragments.PositionFragment
import com.cs356.hireme.loadImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkCurrentUser()

        val db = Firebase.firestore;

        db.collection("positions").get().addOnSuccessListener { data ->
            if(data != null) {
                var positions = data.documents;
                val position = positions[0];
                val url = position.data!!["image"].toString()
                System.out.println(url)
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
            startActivity(intent)
        }
    }
}