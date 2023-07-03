package com.example.menuapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

class ActivityMenu_details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_details)

        val itemId: Int? = intent?.extras?.getInt("id_restau")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navhostMenuDetail) as NavHostFragment
        val navController = navHostFragment.navController

        val bundle = Bundle()
        bundle.putInt("id_restau", itemId ?: 0) // Passer itemId comme argument au fragment
        navController.navigate(R.id.fragmentMenuFood, bundle)
    }
}