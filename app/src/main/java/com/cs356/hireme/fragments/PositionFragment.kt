package com.cs356.hireme.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.cs356.hireme.R
import com.cs356.hireme.activities.ApplicantActivity
import com.cs356.hireme.loadImage
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class PositionFragment : Fragment() {
    private var fragView: View? = null
    private var positions: MutableList<DocumentSnapshot> = mutableListOf();
    private var currentPosition: Int = 0;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = Firebase.firestore;

        fragView = inflater.inflate(R.layout.position_fragment, container, false)

        if (fragView == null) return super.onCreateView(inflater, container, savedInstanceState)

        val companyImage = fragView?.findViewById<ImageButton>(R.id.company_image)

        db.collection("positions").get().addOnSuccessListener { data ->
            if(data != null) {
                positions = data.documents;
                val position = positions[currentPosition];
                val url = position.data!!["image"].toString();
                loadImage(url, companyImage);
            }
        }

        companyImage?.setOnClickListener {
            showBottomSheetDialog()
        }

        // Set up the profile button
        val profileButton = fragView?.findViewById<ImageButton>(R.id.profile_button)
        profileButton?.setOnClickListener {
            // Put up the Profile Fragments
            val intent = Intent(requireContext(), ApplicantActivity::class.java)
            startActivity(intent)
        }

        // accept button
        val acceptButton = fragView?.findViewById<TextView>(R.id.accept_button)
        acceptButton?.setOnClickListener {
            acceptPosition()
        }

        // reject button
        val rejectButton = fragView?.findViewById<TextView>(R.id.reject_button)
        rejectButton?.setOnClickListener {
            rejectPosition()
        }

        return fragView
    }

    private fun acceptPosition() {
        getNewPosition()
    }

    private fun getNewPosition() {
        Toast.makeText(activity, "Gathering position", Toast.LENGTH_SHORT).show()
        // update the views with the new model data

        // have image view shrink
        requireActivity().supportFragmentManager.commit {
            setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            add(R.id.main_fragment_container, PositionFragment())
        }
    }

    private fun rejectPosition() {
        getNewPosition()
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog)
        // populate the dialog with the company info
        val titleTextView = bottomSheetDialog.findViewById<TextView>(R.id.position_text)
        val wageTextView = bottomSheetDialog.findViewById<TextView>(R.id.wage_text)
        val hoursTextView = bottomSheetDialog.findViewById<TextView>(R.id.hours_text)
        val locationTextView = bottomSheetDialog.findViewById<TextView>(R.id.location_text)
        val startDateTextView = bottomSheetDialog.findViewById<TextView>(R.id.start_date_text)
        val aboutTextView = bottomSheetDialog.findViewById<TextView>(R.id.about_text)

        val position = positions[currentPosition];

        // Get the Text for each textViews
        titleTextView!!.text = position.data!!["title"].toString()
        wageTextView!!.text = "$" + position.data!!["wage"].toString()
        hoursTextView!!.text = position.data!!["hours"].toString() + "hrs"
        locationTextView!!.text = position.data!!["location"].toString()
        startDateTextView!!.text = position.data!!["startDate"].toString()

        val db = Firebase.firestore;

        (position.data!!["company"] as DocumentReference).get().addOnSuccessListener { company ->
            if(company != null && company.data != null) {
                val description = company.data!!["description"].toString()
                aboutTextView!!.text = description;
            }
        }

        bottomSheetDialog.show()
    }

}