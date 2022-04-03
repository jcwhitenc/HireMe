package com.cs356.hireme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog


class PositionFragment : Fragment() {
    private var fragView : View? = null

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
            Toast.makeText(activity, "Show Profile", Toast.LENGTH_SHORT).show()
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
        Toast.makeText(activity, "Getting new Company", Toast.LENGTH_SHORT).show()
        // get a new position from the database and store it

        // update the views with the new model data

        // have image view shrink
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