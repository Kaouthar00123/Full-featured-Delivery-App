package com.example.menuapplication.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.menuapplication.Adapter.menu_adapter
import com.example.menuapplication.Entity.MenuEntity
import com.example.menuapplication.R
import com.example.menuapplication.Retrofit.Endpoint1
import com.example.menuapplication.databinding.FragmentMenuFoodBinding
import kotlinx.coroutines.*


class FragmentMenuFood : Fragment() ,  menu_adapter.OnItemClickListener {

    lateinit var binding: FragmentMenuFoodBinding
    private var restauId: Int = 0 // Déclaration de la variable restauId


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = arguments
        if (bundle != null) {
            println("bundele not null")
            restauId = bundle.getInt("id_restau", 1)
            println( restauId )
            // Utilisez l'ID comme vous le souhaitez dans votre fragment
        }
        binding = FragmentMenuFoodBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

        //--------Contenu de titre de cette page
        val title = requireActivity().findViewById<TextView>(R.id.title)
        title.text = "Menu"

        //--------Action when clique sur retour button
        val retour = requireActivity().findViewById<ImageView>(R.id.retour)
        retour.setOnClickListener {
            requireActivity().finish()
        }


    }


    fun loadData() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                // binding.progressBar.visibility = View.INVISIBLE
                //Toast.makeText(requireActivity(), "Une erreur s'est produite : ${throwable.message} est (${throwable.javaClass.simpleName})", Toast.LENGTH_SHORT).show()
            }

        }
        val progressBar = requireActivity().findViewById<ProgressBar>(R.id.progressBareMenue)
        progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = Endpoint1.createEndpoint().getAllMenu(restauId)

            withContext(Dispatchers.Main)
            {
                progressBar.visibility = View.INVISIBLE
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()
                    if (data != null) {
                        if(data.size == 0 )
                        {
                            val emptyCartMessage = requireView().findViewById<TextView>(R.id.emptyCartMessageMenue)
                            emptyCartMessage.visibility = View.VISIBLE
                            emptyCartMessage.text = "Pas de Menue pour ce restau!"
                        }
                        else {
                            binding.recyclerView.layoutManager =
                                GridLayoutManager(requireActivity(), 2)
                            binding.recyclerView.adapter =
                                menu_adapter(data, requireActivity(), this@FragmentMenuFood)
                        }
                    }

                } else {
                    //Toast.makeText(requireActivity(),  "Une erreur s'est  ${response.body()}", Toast.LENGTH_SHORT).show()
                }
            }
            //----thread Main
        }


    }
    override fun onItemClick(item: MenuEntity) {
        Toast.makeText(requireActivity(), "Vous avez cliqué sur ${item.Name}", Toast.LENGTH_SHORT).show()
        println("clique avec succes")
        view?.findNavController()?.navigate(R.id.action_fragmentMenuFood_to_fragmentDetailFood2)
    }
}