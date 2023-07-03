package com.example.menuapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.menuapplication.databinding.ActivityPagesBinding


class pages : AppCompatActivity() {
    lateinit var navController: NavController
    private lateinit var binding: ActivityPagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPagesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //----------------Affectation de navahost
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.navBottom,navController)

        //---------------------verfier l'intent
        val fragmentType = intent.getStringExtra("fragment_destination")

       if (fragmentType == "fragment2") {
            println("cas fragmentType == fragment2")
            navController.navigate(R.id.fragmentCart)
        }
        if (fragmentType == "fragment3") {
            println("cas fragmentType == fragment3")
            navController.navigate(R.id.fragmentCommande)
        }

        //----------------menu d'activity principale
        binding.navBottom.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    navController.navigate(R.id.fragmentListeRestau)
                    true
                }
                R.id.cart -> {
                    navController.navigate(R.id.fragmentCart)
                    true
                }
                R.id.orders -> {
                    navController.navigate(R.id.fragmentCommande)
                    true
                }
                else -> false
            }
        }
    }


}