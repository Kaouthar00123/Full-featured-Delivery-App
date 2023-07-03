package com.example.menuapplication.Doa
import androidx.room.*
import com.example.menuapplication.DataModel.FoodPanier

@Dao
interface PanierDao {
        @Query("select * from FoodPanier")
        fun getAllFoodsPanier( ):List<FoodPanier>

        @Query("select * from FoodPanier  where FoodPanierId=:id")
        fun getFoodsPanierById(id:Long):FoodPanier

        @Query("SELECT MAX(FoodPanierId) FROM FoodPanier")
        fun getLastFoodsPanier():Long

        @Insert
        fun addFoodToPanier(FoodToPanierInfos: FoodPanier)

        @Query("UPDATE FoodPanier SET quantite = :quantite, specialInstruction = :specialInstruction WHERE FoodPanierId = :FoodPanierId")
        fun updateFoodPanier(FoodPanierId:Long, quantite:Int , specialInstruction:String?)

        @Update
        fun updateFoodPanierAllInfos(FoodPanierInfos: FoodPanier )

        @Query("DELETE FROM FoodPanier WHERE FoodPanierId = :foodPanierId")
        fun deleteFoodPanierById(foodPanierId: Long)

        @Delete
        fun deleteFoodPanier(FoodPanierInfos: FoodPanier)

        @Query("DELETE FROM FoodPanier")
        fun deleteAllFoodPanier( )
}