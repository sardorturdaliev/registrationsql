package com.example.registretionhome.model

import android.graphics.Bitmap

data class UserData(
    val id: Int,
    val bitmap: Bitmap,
    val fullname: String,
    val telnumber: String,
    val davlat: String,
    val address: String,
    val paroluser: String
) {
}