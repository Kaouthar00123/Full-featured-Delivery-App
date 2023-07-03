package com.example.menuapplication.Entity

import android.net.Uri

data class UserEntity(
    val Name:String?,
    val email:String,
    val phoneNumber: String?,
    val address:String?,
    var password:String,
    var profilePicture: String,
)