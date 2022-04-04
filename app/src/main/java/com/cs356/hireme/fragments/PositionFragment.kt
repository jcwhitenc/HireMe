package com.cs356.hireme.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.cs356.hireme.R
import com.cs356.hireme.activities.ApplicantActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


class PositionFragment : Fragment() {
    private var fragView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragView = inflater.inflate(R.layout.position_fragment, container, false)

        if (fragView == null) return super.onCreateView(inflater, container, savedInstanceState)

        val companyImage = fragView?.findViewById<ImageButton>(R.id.company_image)
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
        // Toast.makeText(activity, "Getting new Company", Toast.LENGTH_SHORT).show()
        // get a new position from the database and store it

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
//        val titleTextView = bottomSheetDialog.findViewById<TextView>(R.id.position_text)
//        val wageTextView = bottomSheetDialog.findViewById<TextView>(R.id.wage_text)
//        val hoursTextView = bottomSheetDialog.findViewById<TextView>(R.id.hours_text)
//        val locationTextView = bottomSheetDialog.findViewById<TextView>(R.id.location_text)
//        val startDateTextView = bottomSheetDialog.findViewById<TextView>(R.id.start_date_text)

        // Get the Text for each textViews


        bottomSheetDialog.show()
    }

}