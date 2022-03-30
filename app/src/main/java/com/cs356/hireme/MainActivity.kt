package com.cs356.hireme

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.cs356.hireme.main.CompanyFragment
import com.cs356.hireme.main.MainFragment
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), View.OnTouchListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val mainFrag = MainFragment.newInstance()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, mainFrag)
                .commitNow()
        }

        checkCurrentUser()

        val frags = listOf(CompanyFragment.newInstance())

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top)
        fragmentTransaction.replace(R.id.container, frags[0])
        fragmentTransaction.commit()

        //close Button
        messageView = findViewById(R.id.message)
        messageView?.setOnTouchListener(this)
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (view) {
            messageView -> {
                when (motionEvent.getAction()) {
                    MotionEvent.ACTION_DOWN -> {
                        Toast.makeText(this, "Should the companies Info now.",
                            Toast.LENGTH_LONG).show()
                    }
                    MotionEvent.ACTION_UP -> {
                        view.performClick()
                    }
                }
            }
        }
        return true
    }

    private fun checkCurrentUser() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
        } else {
            // No user is signed in
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}