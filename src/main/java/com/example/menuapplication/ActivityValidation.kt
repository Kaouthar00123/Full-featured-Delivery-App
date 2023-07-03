package com.example.menuapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.menuapplication.Entity.Auth
import com.example.menuapplication.Entity.CommandeEntity
import com.example.menuapplication.Retrofit.Endpoint1
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityValidation : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validation)

        //-----elments d'interface
        val Annuler = findViewById<Button>(R.id.Annuler)
        Annuler.setOnClickListener {
            Toast.makeText(this,"Votre Commande est annulé", Toast.LENGTH_SHORT).show()
        }


        //-------recuperation des informations
        //----------validation finale
        val validation = findViewById<Button>(R.id.validation)
        validation.setOnClickListener {

            //-----call post commande et deleteAll les elments de panier
            CoroutineScope(Dispatchers.IO).launch {
                var adress = findViewById<EditText>(R.id.editTextTextPostalAddress).text.toString()
                var note = findViewById<TextInputEditText>(R.id.textInputEditText).text.toString()
                var idRestau = 0
                var totale = 0.0

                idRestau = intent.getIntExtra("idRestau",1)
                totale = intent.getDoubleExtra("totale",0.0)


                println("infos:")
                println(idRestau)
                println(totale)
                println(adress)

                val cmd = CommandeEntity( adress, idRestau, totale,  note  )
                val pref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val id  = pref.getInt("IdUser", 1)

                val response = Endpoint1.createEndpoint().postCmd(id.toLong(), cmd)
                withContext(Dispatchers.Main) {
                    var data: Auth? = response.body()
                        Toast.makeText(this@ActivityValidation ,"Votre Commande est confirmé", Toast.LENGTH_SHORT).show()
                println("data")
                    println(data)
                    if(data != null){
                        //-----et basculer vers pages ordres
                        val intent = Intent( this@ActivityValidation , pages::class.java)
                        intent.putExtra("fragment_destination","fragment3")
                        startActivity(intent)
                    }

                    else {
                        // Gérez les erreurs de requête ici
                        if (data != null) {

                            println("Erreur lors de la demande d'authentification : ${data.err}")
                        }
                        Toast.makeText(this@ActivityValidation , "Erreur! Vérifiez vos infos: ${data?.err}", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }


    }
}