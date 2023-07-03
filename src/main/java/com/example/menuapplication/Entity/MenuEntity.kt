package com.example.menuapplication.Entity


data class MenuEntity(
    val FoodId:Int?,
    val Name:String?,
    val Description:String,
    val prixUnitaire:Double?,
    val image:String?,
    val restauId:Int,
    var rate:Int,
    var review:Int,
)