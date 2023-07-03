package com.example.menuapplication.Entity


data class Commande(
    val IdCmd: Long,
    val IdLivreur: Long,
    val restauName: String,
    val Adress_user: String,
    val PrixTotale: Double,

    val date_cmd: String,
    val etat: String,

    val remarques: String
)
