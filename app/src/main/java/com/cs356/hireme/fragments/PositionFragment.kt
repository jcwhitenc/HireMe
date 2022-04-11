package com.cs356.hireme.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.cs356.hireme.R
import com.cs356.hireme.activities.*
import com.cs356.hireme.loadImage
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot


class PositionFragment() : Fragment(), Parcelable, ActivityResultCaller {
    private var fragView: View? = null
    private var positions: MutableList<DocumentSnapshot> = mutableListOf()
    private var currentPosition: Int = 0
    private var nextPositionFragment: PositionFragment? = null
    private var image: Bitmap? = null
    private var nextPositionReady: Boolean = false

    private val filtersResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) {
                Toast.makeText(this.context, "Error setting filters.", Toast.LENGTH_SHORT)
            }
            if (result.resultCode == RESULT_LOG_OUT) {
                val intent = Intent(this.context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

    private val submitApplicationResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) {
                Toast.makeText(this.context, "Error submitting application", Toast.LENGTH_SHORT)
            }
        }

    constructor(parcel: Parcel) : this() {
        currentPosition = parcel.readInt()
    }

    constructor(
        positions: MutableList<DocumentSnapshot>,
        image: Bitmap?,
        currentPosition: Int
    ) : this() {
        this.currentPosition = currentPosition
        this.image = image
        this.positions = positions
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragView = inflater.inflate(R.layout.position_fragment, container, false)

        if (fragView == null) return super.onCreateView(inflater, container, savedInstanceState)

        val companyImage = fragView?.findViewById<ImageButton>(R.id.company_image)

        val bottomSheetDialog =
            BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog)

        initializeBottomSheetDialog(bottomSheetDialog)
        companyImage?.setImageBitmap(image)

        val position = positions[currentPosition]

        // Set quick view elements
        val companyName = fragView?.findViewById<TextView>(R.id.companyName)
        val positionTitle = fragView?.findViewById<TextView>(R.id.positionTitle)
        val wageTextView = fragView?.findViewById<TextView>(R.id.wage)

        positionTitle?.text = position.get("title").toString()
        wageTextView?.text = "$" + position.get("wage").toString()

        (position.get("company") as DocumentReference).get().addOnSuccessListener { company ->
            if (company != null && company.data != null) {
                companyName!!.text = company.data!!["name"].toString()
            }
        }


        // Init the next position fragment.
        val nextPositionIndex = (currentPosition + 1) % positions.size
        val nextPosition = positions[nextPositionIndex]
        loadImage(nextPosition.get("image").toString()) { image ->
            nextPositionFragment = PositionFragment(this.positions, image, nextPositionIndex)
            nextPositionReady = true
        }

        companyImage?.setOnClickListener {
            showBottomSheetDialog(bottomSheetDialog)
        }

        // Set up the profile button
        val profileButton = fragView?.findViewById<Button>(R.id.profile_button)
        profileButton?.setOnClickListener {
            // Put up the Profile Fragments
            filtersResult.launch(Intent(this.context, ApplicantActivity::class.java))
        }

        // accept button
        val acceptButton = fragView?.findViewById<TextView>(R.id.accept_button)
        acceptButton?.setOnClickListener {
            if (nextPositionReady) {
                submitApplicationResult.launch(Intent(this.context, SubmitActivity::class.java))
                acceptPosition()
            }
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
        if (!nextPositionReady) {
            return
        }

        // have image view shrink
        requireActivity().supportFragmentManager.commit {
            setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            add(R.id.main_fragment_container, nextPositionFragment!!)
        }
    }

    private fun rejectPosition() {
        getNewPosition()
    }

    private fun initializeBottomSheetDialog(bottomSheetDialog: BottomSheetDialog) {
        // populate the dialog with the company info
        val titleTextView = bottomSheetDialog.findViewById<TextView>(R.id.position_text)
        val wageTextView = bottomSheetDialog.findViewById<TextView>(R.id.wage_text)
        val hoursTextView = bottomSheetDialog.findViewById<TextView>(R.id.hours_text)
        val locationTextView = bottomSheetDialog.findViewById<TextView>(R.id.location_text)
        val startDateTextView = bottomSheetDialog.findViewById<TextView>(R.id.start_date_text)
        val aboutTextView = bottomSheetDialog.findViewById<TextView>(R.id.about_text)

        val position = positions[currentPosition]

        // Get the Text for each textViews
        titleTextView?.text = position.get("title").toString()
        wageTextView?.text = "$" + position.get("wage").toString()
        hoursTextView?.text = position.get("hours").toString() + "hrs"
        locationTextView?.text = position.get("location").toString()
        startDateTextView?.text = position.get("startDate").toString()

        (position.get("company") as DocumentReference).get().addOnSuccessListener { company ->
            if (company != null && company.data != null) {
                val description = company.data!!["description"].toString()
                aboutTextView!!.text = description
            }
        }
    }

    private fun showBottomSheetDialog(bottomSheetDialog: BottomSheetDialog) {
        bottomSheetDialog.show()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(currentPosition)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PositionFragment> {
        override fun createFromParcel(parcel: Parcel): PositionFragment {
            return PositionFragment(parcel)
        }

        override fun newArray(size: Int): Array<PositionFragment?> {
            return arrayOfNulls(size)
        }
    }

}