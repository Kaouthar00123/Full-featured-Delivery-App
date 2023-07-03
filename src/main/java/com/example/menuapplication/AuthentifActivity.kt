package com.example.menuapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.edit
import com.example.menuapplication.Entity.AutentifEntity
import com.example.menuapplication.Entity.Auth
import com.example.menuapplication.Retrofit.Endpoint1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



/*******facebook******/
import com.facebook.*
import com.facebook.CallbackManager.Factory.create
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

/*****google*****/
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import java.util.*


/*************add this******/
// Inside your activity or fragment
@SuppressLint("StaticFieldLeak")
private lateinit var googleSignInClient: GoogleSignInClient
// Inside your activity or fragment
lateinit var callbackManager: CallbackManager
private val RC_GOOGLE_SIGN_IN = 9001
/*****end*****/
class AuthentifActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentif)

        //------------Set up the Google Sign-In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Create the Google Sign-In client
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set up the Google Sign-In button
        val googleSignInButton = findViewById<ImageView>(R.id.GmailIcon)
        googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }
        /********************end google*****************/

        //----------------Inscription
        val SignUp = findViewById<Button>(R.id.SignUp)
        SignUp.setOnClickListener{
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        //-----elments d'interface
        val signIn = findViewById<Button>(R.id.signIn)

        signIn.setOnClickListener {
            val pref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

            val email = findViewById<EditText>(R.id.editTextTextEmailAddress)
            val emailUser = email.text.toString()

            val pass = findViewById<EditText>(R.id.editTextTextPassword)
            val passUser = pass.text.toString()

            //---------authentif: recuper les informations et les compérer avec celle de serveur, puis si vraie on stocket iduser
            CoroutineScope(Dispatchers.IO).launch {

                val new = AutentifEntity(emailUser, passUser)
                println("email et password")
                println(emailUser)
                println(passUser)
                val response = Endpoint1.createEndpoint()
                    .authentif( new )
                withContext(Dispatchers.Main) {
                     var data: Auth? = response.body()
                    if (response.isSuccessful) {
                            val userID = data?.userID
                            println("ID de l'utilisateur : $userID")
                            pref.edit { putBoolean("connected", true) }
                            pref.edit {
                                if (userID != null) {
                                    putInt("IdUser", userID.toInt())
                                }
                            }
                        val intent = Intent( this@AuthentifActivity, ActivityValidation::class.java)
                        startActivity(intent)

                            }

                    else {
                        // Gérez les erreurs de requête ici
                        if (data != null) {
                            println("Erreur lors de la demande d'authentification : ${data.err}")
                        }
                        pref.edit{ putBoolean("connected", false) }
                        Toast.makeText(this@AuthentifActivity, "Erreur de login! Vérifiez vos infos: ${data?.err}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

    }



    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }


    private fun handleFacebookLoginSuccess(accessToken: AccessToken) {
        val token = accessToken.token
        val userId = accessToken.userId

        // You can retrieve the user's access token and user ID here
        Toast.makeText(applicationContext, "Facebook login success: Token - $token, UserID - $userId", Toast.LENGTH_SHORT).show()
    }
    // Override the onActivityResult method to handle the login result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                // Handle successful login
                val idToken = account?.idToken
                // You can retrieve the user's ID token here
            } catch (e: ApiException) {
                // Handle login error
            }
        }
    }
}