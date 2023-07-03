package com.example.menuapplication


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.menuapplication.DataModel.Food
import com.example.menuapplication.DataModel.FoodPanier
import com.example.menuapplication.Doa.FoodDao
import com.example.menuapplication.Doa.PanierDao
import com.example.menuapplication.RoomDB.AppDB
import org.junit.After
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.runner.RunWith
import kotlin.properties.Delegates

@RunWith(AndroidJUnit4::class)
class PanierDao_Impl2Test {


    private lateinit var panierDao: PanierDao
    private lateinit var db: AppDB
    private lateinit var foodDao: FoodDao
    private var id by Delegates.notNull<Long>()

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDB::class.java
        ).build()
        panierDao = db.getFoodPanierDao()
        foodDao = db.getFoodDao()

        val foodInser =  Food(Nom = "Rechta", Description="Rechta algérienne avec sous blanche", prixUnitaire = 350.00, photo= "rechta.jpg")
         id = foodDao.addFood(foodInser )
    }

    @After
    fun Close() {
        db.close()
    }

    @Test
    fun addFoodToPanierTest() {
        val panierFood = FoodPanier(FoodId = id, quantite = 2, specialInstruction = "inst")
        val nvId = panierDao.addFoodToPanier(panierFood)
        panierFood.FoodPanierId = nvId
        val retrievedFood = panierDao.getFoodsPanierById(nvId)
        assertEquals(panierFood, retrievedFood)
    }

    @Test
    fun updateFoodPanierAllInfos() {

        val panierFood = FoodPanier(FoodId = id, quantite = 2, specialInstruction = "inst")
        val nvId = panierDao.addFoodToPanier(panierFood)
        panierFood.FoodPanierId = nvId

        panierFood.quantite = 3
        panierDao.updateFoodPanierAllInfos(panierFood)

        val retrievedFood = panierDao.getFoodsPanierById(nvId)
        assertEquals(panierFood, retrievedFood)
    }

    @Test
    fun deleteFoodPanierById() {
        val panierFood = FoodPanier(FoodId = id, quantite = 2, specialInstruction = "inst")
        val nvId = panierDao.addFoodToPanier(panierFood)
        panierFood.FoodPanierId = nvId

        panierDao.deleteFoodPanierById(nvId)

        var liste = panierDao.getAllFoodsPanier()
        var bol = liste.contains( panierFood )
        assertEquals(bol, false)
    }

    @Test
    fun deleteAllFoodPanier() {
        val panierFood = FoodPanier(FoodId = id, quantite = 2, specialInstruction = "inst")
        val nvId = panierDao.addFoodToPanier(panierFood)
        panierFood.FoodPanierId = nvId

        panierDao.deleteAllFoodPanier( )

        var liste = panierDao.getAllFoodsPanier()
        assertEquals( liste.size, 0)
    }

}