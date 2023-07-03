package com.example.menuapplication.Retrofit
import com.example.menuapplication.Entity.*
import com.example.menuapplication.url

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface Endpoint1 {

    //-------------------Les ends points

    //--------Restau
    @GET("food_app_esi/restaus/")
    suspend fun getAllResaturents(): Response<List<RestauEntity>>

    @GET("food_app_esi/menu/{restauId}")
    suspend fun getAllMenu(@Path("restauId")  restauId:Int): Response<List<MenuEntity>>

    @GET("food_app_esi/restau/{restauId}")
    suspend fun getRestauById(@Path("restauId") restauId: Long): Response<RestauEntity>


    //--------Les commandes
    @GET("food_app_esi/commandes/{IdUser}")
    suspend fun getCmdsByIdUser( @Path("IdUser")  IdUser:Long ): Response<List<Commande>>

    @GET("food_app_esi/commande/{IdCmd}")
    suspend fun getADetailsCmd(@Path("IdCmd")  IdCmd:Long): Response<List<MenuEntity>>

    @POST("food_app_esi/commande/{IdUser}")
    suspend fun postCmd( @Path("IdUser")  IdUser:Long, @Body commandeBody: CommandeEntity): Response<Auth>

    @PUT("food_app_esi/commande/{IdCmd}")
    suspend fun updateCmd(@Path("IdCmd")  IdCmd:Long, @Body etat: String): Response<String>

    @DELETE("food_app_esi/commande/{IdCmd}")
    suspend fun deleteCmd(@Path("IdCmd")  IdCmd:Long): Response<String>


    //------------------User
    @GET("food_app_esi/user/{IdUser}")
    suspend fun getUserById(@Path("IdUser")  IdUser:Long): Response<UserEntity>


    @POST("food_app_esi/user/}")
    suspend fun postUser( @Body UserBody: UserEntity ): Response<Long>

    @PUT("food_app_esi/user/setToken/{IdUser}")
    suspend fun setUserToken(@Path("IdUser")  IdUser:Long, @Body token: String): Response<String>


    @POST("food_app_esi/user/authentif")
    suspend fun authentif( @Body UserBody: AutentifEntity ): Response<Auth>

    @GET("food_app_esi/user/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<UserEntity>

    @POST("food_app_esi/user")
    suspend fun addUser(@Body user: UserEntity?): Response<UserEntity>



    //-----------Instance Singleton
    companion object {
        @Volatile
        var endpoint: Endpoint1? = null
        fun createEndpoint(): Endpoint1 {
            if(endpoint ==null) {
                synchronized(this) {
                    endpoint = Retrofit.Builder().baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create()).build()
                        .create(Endpoint1::class.java)
                }
            }
            return endpoint!!
        }
    }
}