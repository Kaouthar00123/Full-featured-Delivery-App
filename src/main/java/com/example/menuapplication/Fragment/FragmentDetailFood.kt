package com.example.menuapplication.Fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.menuapplication.DataModel.FoodPanier
import com.example.menuapplication.Entity.SharedData
import com.example.menuapplication.R
import com.example.menuapplication.RoomDB.AppDB
import com.example.menuapplication.pages

class FragmentDetailFood : Fragment() {
    lateinit var sharedData: SharedData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        //---------------Reception d'informations
        sharedData = SharedData.getInstance()
        return inflater.inflate(R.layout.fragment_detail_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = requireActivity().findViewById<TextView>(R.id.title)
        title.text = "Details"

        val retour = requireActivity().findViewById<ImageView>(R.id.retour)
        retour.setOnClickListener {
            view?.findNavController()?.navigateUp()
        }

        val add = requireActivity().findViewById<Button>(R.id.add4)
        val mince = requireActivity().findViewById<Button>(R.id.mince3)
        val quantite  = requireActivity().findViewById<TextView>(R.id.quantite3)
        val price  = requireActivity().findViewById<TextView>(R.id.price2)
        val totale  = requireActivity().findViewById<TextView>(R.id.totale)
        val addCart = requireActivity().findViewById<Button>(R.id.button)
        val name = requireActivity().findViewById<TextView>(R.id.nom)
        val imge = requireActivity().findViewById<ImageView>(R.id.imgFood)
        val des = requireActivity().findViewById<TextView>(R.id.textView4)
        val specialInst = requireActivity().findViewById<EditText>(R.id.editTextTextPersonName)

        //-----------load data
        name.setText(sharedData.FoodInformation?.Name ?: "Food")
        des.setText(sharedData.FoodInformation?.Description ?: "Food")

        price.setText(sharedData.FoodInformation?.prixUnitaire.toString() ?: "Food")
        println(sharedData.FoodInformation?.image)
        Glide.with(requireContext()).load(sharedData.FoodInformation?.image).apply(RequestOptions().placeholder(R.drawable.rechta2).error(R.drawable.rechta))
            .override(350, 190 )
            .into(imge)

        var qu = quantite.getText().toString().toInt();
        var prix = price.getText().toString().toFloat();
        prix = prix * (qu);
        totale.setText( (qu *prix ).toString() )

        add.setOnClickListener {
            var qu = quantite.getText().toString().toInt();
            qu = qu + 1;
            quantite.setText( qu.toString() )

            var prix = price.getText().toString().toFloat();
            prix = prix * (qu);
            totale.setText( prix.toString() )
        }

        mince.setOnClickListener {
            var qu = quantite.getText().toString().toInt();
            qu = qu - 1;
            quantite.setText( qu.toString() )

            var prix = price.getText().toString().toFloat();
            prix = prix * (qu);
            totale.setText( prix.toString() )
        }

        addCart.setOnClickListener {

            val panierFood = sharedData.FoodInformation?.FoodId?.let { it1 ->
                sharedData.FoodInformation!!.prixUnitaire?.let { it2 ->
                    FoodPanier(FoodPanierId = null,
                        it1.toLong(), sharedData.FoodInformation!!.restauId.toLong(),
                        sharedData.FoodInformation!!.Name.toString(), sharedData.FoodInformation!!.image.toString(),
                        it2, quantite.getText().toString().toInt(),specialInst.getText().toString())
                }
            }

            if (panierFood != null) {
                //avant qu'il ajoute il verfie si commendes apprtien au meme restau
                //get infos de first elment inserer
                val foods = AppDB.getInstance(requireContext())?.getFoodPanierDao()?.getAllFoodsPanier()
                val restauID:Long
                if (foods != null) {
                    if(foods.size > 0 ) {
                        restauID  = foods.get(0)?.RestauId?.toLong()!!

                        //comprer IDrestau des elment recupere et elment exitant
                        if(sharedData.FoodInformation?.restauId?.toLong()  ==  restauID)
                        {
                            println("cas meme restau")
                            AppDB.getInstance(requireContext())?.getFoodPanierDao()?.addFoodToPanier(panierFood)
                            println( AppDB.getInstance(requireContext())?.getFoodPanierDao()?.getLastFoodsPanier() )
                            Toast.makeText(requireActivity(), "Food succesfuly added to your Cart", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            println("cas restau differt")
                            Toast.makeText(requireActivity(), "Food not added to your Cart, cause you ordred from differnt restaurant, check you first order", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        AppDB.getInstance(requireContext())?.getFoodPanierDao()?.addFoodToPanier(panierFood)
                        println( AppDB.getInstance(requireContext())?.getFoodPanierDao()?.getLastFoodsPanier() )
                        Toast.makeText(requireActivity(), "Food succesfuly added to your Cart", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    AppDB.getInstance(requireContext())?.getFoodPanierDao()?.addFoodToPanier(panierFood)
                    println( AppDB.getInstance(requireContext())?.getFoodPanierDao()?.getLastFoodsPanier() )
                    Toast.makeText(requireActivity(), "Food succesfuly added to your Cart", Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(requireActivity(), pages::class.java)
                intent.putExtra("fragment_destination","fragment2")
                requireActivity().startActivity(intent)
            }


        }

    }

}