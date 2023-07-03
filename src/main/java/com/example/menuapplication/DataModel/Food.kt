package com.example.menuapplication.DataModel
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "FoodPanier")
data class FoodPanier(
    @PrimaryKey(autoGenerate = true)
    var FoodPanierId:Long? = null,

    var FoodId:Long,
    var RestauId:Long,

    var nomFood: String,
    var imageFood:String,
    var unitPrice:Double,

    var quantite:Int = 1,
    var specialInstruction:String
)