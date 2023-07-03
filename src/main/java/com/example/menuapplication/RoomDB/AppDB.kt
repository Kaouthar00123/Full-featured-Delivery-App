package com.example.menuapplication.RoomDB


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.menuapplication.DataModel.FoodPanier

import com.example.menuapplication.Doa.PanierDao


@Database(entities = [FoodPanier:: class],version = 1)
abstract class AppDB: RoomDatabase()
{
    abstract fun getFoodPanierDao(): PanierDao


    companion object
    {
        private var INSTANCE: AppDB? = null
        fun getInstance(context: Context): AppDB? {
            if (INSTANCE == null)
            {
                INSTANCE = Room.databaseBuilder(context, AppDB::class.java, "Food_app_db").allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}