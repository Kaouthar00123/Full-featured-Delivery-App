package com.example.menuapplication.Fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menuapplication.ActivityValidation
import com.example.menuapplication.Adapter.RestaurentAdapter
import com.example.menuapplication.Adapter.cart_adapter
import com.example.menuapplication.AuthentifActivity
import com.example.menuapplication.DataModel.FoodCart
import com.example.menuapplication.Entity.SharedData
import com.example.menuapplication.R
import com.example.menuapplication.Retrofit.Endpoint1
import com.example.menuapplication.RoomDB.AppDB
import com.example.menuapplication.databinding.FragmentCartBinding
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class FragmentCart : Fragment() {

    lateinit var binding: FragmentCartBinding
    var vide:Boolean = true
    var PrixFoods = 0.0
    var PrixLivraison = 0.0
    var restauID by Delegates.notNull<Long>()
    lateinit var sharedData: SharedData


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
        binding.recyclerView.adapter = cart_adapter(loadData(), requireActivity())

        //-------Les elements d'affichage si y'a des elments dans le panier, donc hiden commande, et les prix
        if(!vide)
        {
            //*********************debut courotine
            val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireActivity(),
                        "Une erreur s'est produite : ${throwable.message} est (${throwable.javaClass.simpleName})",
                        Toast.LENGTH_SHORT
                    ).show()            }
            }
            //progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO+ exceptionHandler).launch{

                val response = Endpoint1.createEndpoint().getRestauById( restauID )
                withContext(Dispatchers.Main)
                {
                    //progressBar.visibility = View.INVISIBLE
                    if (response.isSuccessful && response.body() != null) {
                        val data = response.body()
                        if(data != null){
                            //------action  de validation
                            val valideAll = requireActivity().findViewById<Button>(R.id.valideAll)
                            valideAll.setOnClickListener{
                                //---------verfie si est ell est authentifier:
                                val pref = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

                                //---------les prix
                                PrixLivraison = data.prixLivraison
                                val LivraisonNumber = requireActivity().findViewById<TextView>(R.id.LivraisonNumber)
                                LivraisonNumber.text = PrixLivraison.toString()

                                val PrixFoodsNumber = requireActivity().findViewById<TextView>(R.id.PrixFoodsNumber)
                                PrixFoodsNumber.text = PrixFoods.toString()

                                val totale = requireActivity().findViewById<TextView>(R.id.TotaleCartNumber)
                                totale.text = (PrixFoods + PrixLivraison).toString()

                                val con = pref.getBoolean("connected", false)
                                if( con == false){
                                    Toast.makeText(context, "Il feut etre authentifier", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(context, AuthentifActivity::class.java)
                                    requireActivity().startActivity(intent)
                                }
                                else {
                                    val foodPaniers = AppDB.getInstance(requireContext())?.getFoodPanierDao()?.getAllFoodsPanier()
                                    if( (foodPaniers != null) && (foodPaniers.size > 0 ))
                                    {
                                        val idRestau = foodPaniers.get(0)?.RestauId
                                        AppDB.getInstance(requireContext())?.getFoodPanierDao()?.deleteAllFoodPanier()
                                        val intent = Intent(context, ActivityValidation::class.java)
                                        intent.putExtra("idRestau", idRestau)
                                        intent.putExtra("totale", (PrixFoods + PrixLivraison).toDouble())
                                        requireActivity().startActivity(intent)
                                    }
                                }
                            }

                        }
                    } else {
                       // Toast.makeText(requireActivity(), "Une erreur, details: ${response.body()}", Toast.LENGTH_SHORT).show()
                    }
                }
                //----thread Main
            }
            //*********************fin courotine

        }
        else
        {
            showEmptyCartMessage("Pannier est vide!")
        }
        //----------sinon afficher: pannier est vide! un petite text

        //*********************fin de ce process
    }


    fun loadData():MutableList<FoodCart> {
        val data = mutableListOf<FoodCart>()
        var FoodsPanier = AppDB.getInstance(requireActivity())?.getFoodPanierDao()?.getAllFoodsPanier()
        if ((FoodsPanier != null) && (FoodsPanier.size > 0 )) {
            vide = false
            for (food in FoodsPanier) {
                val totaleFood = (food.unitPrice * food.quantite).toString()
                var food_cart = FoodCart(
                    food.FoodPanierId,
                    food.FoodId,
                    food.imageFood,
                    food.nomFood,
                    food.unitPrice.toString(),
                    food.quantite.toString(),
                    totaleFood,
                    food.specialInstruction
                )
                PrixFoods += totaleFood.toDouble()
                data.add( food_cart);
            }
            restauID = FoodsPanier.get(0).RestauId

        }
        else{
            vide = true
        }
        return data;
    }


    private fun showEmptyCartMessage(text:String) {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recyclerView)
        val totaleCart = requireView().findViewById<TextView>(R.id.totaleCart)
        val totaleCartNumber = requireView().findViewById<TextView>(R.id.TotaleCartNumber)
        val prixFoodsNumber = requireView().findViewById<TextView>(R.id.PrixFoodsNumber)
        val prixFoods = requireView().findViewById<TextView>(R.id.PrixFoods)
        val livraison = requireView().findViewById<TextView>(R.id.Livraison)
        val livraisonNumber = requireView().findViewById<TextView>(R.id.LivraisonNumber)
        val valideAll = requireView().findViewById<Button>(R.id.valideAll)

        recyclerView.visibility = View.GONE
        totaleCart.visibility = View.GONE
        totaleCartNumber.visibility = View.GONE
        prixFoodsNumber.visibility = View.GONE
        prixFoods.visibility = View.GONE
        livraison.visibility = View.GONE
        livraisonNumber.visibility = View.GONE
        valideAll.visibility = View.GONE

        val emptyCartMessage = requireView().findViewById<TextView>(R.id.emptyCartMessage)
        emptyCartMessage.visibility = View.VISIBLE
        emptyCartMessage.text = text
    }

}