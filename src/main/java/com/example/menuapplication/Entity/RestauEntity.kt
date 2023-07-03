package com.example.menuapplication.Entity

data class RestauEntity(
    val RestauId:Int,
    val Name:String,
    val Address:String,
    val MapAddress:String,
    val category:String,
    val Email:String,
    var stars:Double,
    var viewers:Int,
    val Numphone:String,
    val fblink:String,
    val instalink:String,
    val image:String,
    val prixLivraison:Double
)
