package com.example.menuapplication.Entity

import com.google.gson.annotations.SerializedName

data class CommandeEntity(
    val addressUser: String,
    val idRestau: Int,
    val prixTotale: Double,
    val remarques: String
)
