package com.example.menuapplication.DataModel

data class FoodCart(

    var FoodPanierId:Long? = null,
    var FoodId:Long,
    var img : String,
    var name : String,
    var unitprice : String,
    var quantite : String,
    var totaleprice : String,
    var specialInstruction: String?
)
