package com.example.menuapplication


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.menuapplication.DataModel.Food
import com.example.menuapplication.Doa.FoodDao
import com.example.menuapplication.RoomDB.AppDB
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FoodDao_Impl2Test {

    private lateinit var foodDao: FoodDao
    val db: AppDB = Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.
        getInstrumentation().context,
        AppDB::class.java).build()

    @Before
    fun initDB() {

    }

    @After
    fun closeDb() {
        db.close()
    }


    @Test
    fun insertAndGetFood() {
        val foodInser =  Food(Nom = "Rechta", Description="Rechta algérienne avec sous blanche", prixUnitaire = 350.00, photo= "rechta.jpg")
        val id = db?.getFoodDao()?.addFood(foodInser )
        val retrievedFood = id?.let { db?.getFoodDao()?.getFoodById(it) }
        foodInser.FoodId = id
        assertEquals(foodInser ,retrievedFood )
    }

    @Test
    fun getAllFoodsTest() {
        val food1 =  Food(Nom = "Koskous", Description="Kouskous algérien avec sousrouge", prixUnitaire = 270.00, photo= "koskos.jpg")
        val id = db?.getFoodDao()?.addFood(food1 )
        food1.FoodId = id
        println("id 1 = ")
        println( id )

        val food2 = Food(Nom = "Rechta", Description="Rechta algérienne avec sous blanche", prixUnitaire = 350.00, photo= "rechta.jpg")
        val id2 = db?.getFoodDao()?.addFood(food2)
        food2.FoodId = id2
        println("id 2 = ")
        println( id2 )
        val allFoods = db?.getFoodDao()?.getAllFoodsRestau()
        if (allFoods != null) {
            assertArrayEquals(allFoods.toTypedArray(), arrayOf(food1,food2))
        }
    }

    @Test
    fun updateAndGetFoodTest() {
        val food1 =  Food(Nom = "soupe", Description="soupe algérien avec sousrouge", prixUnitaire = 270.00, photo= "koskos.jpg")
        val id = db?.getFoodDao()?.addFood(food1 )

        food1.FoodId = id
        val food1Update =  Food(Nom = "soupe2", Description="soupe2 algérien avec sousrouge", prixUnitaire = 100.00, photo= "koskos.jpg")
        food1Update.FoodId = id

        db?.getFoodDao()?.updateFood(food1Update)
        val retrievedFood = id?.let { db?.getFoodDao()?.getFoodById(it) }
        assertEquals(food1Update, retrievedFood)
    }

    @Test
    fun deleteAndGetFoodTest() {
        val food1 =  Food(Nom = "soupe", Description="soupe algérien avec sousrouge", prixUnitaire = 270.00, photo= "koskos.jpg")
        val id = db?.getFoodDao()?.addFood(food1 )

        db?.getFoodDao()?.deleteFood(food1)
        val retrievedFood = id?.let { db?.getFoodDao()?.getFoodById(it) }
        assertNull(retrievedFood)
    }

}



