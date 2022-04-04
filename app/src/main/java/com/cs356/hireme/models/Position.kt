package com.cs356.hireme.models

import android.graphics.Bitmap

class Position {
    private val photo: Bitmap
        get() {
            TODO()
        }

    private val title: String
        get() {
            TODO()
        }
    private val wage: Int
        get() {
            TODO()
        }
    private val payCycle: String
        get() {
            TODO()
        }
    private val hoursPerWeek: String
        get() {
            TODO()
        }
    private val location: String
        get() {
            TODO()
        }
    private val startDate: String
        get() {
            TODO()
        }
    private val description: String
        get() {
            TODO()
        }

    override fun toString(): String {
        return "Position(title='$title', wage=$wage, payCycle='$payCycle', hoursPerWeek='$hoursPerWeek', Location='$location', StartDate='$startDate', Description='$description')"
    }

}