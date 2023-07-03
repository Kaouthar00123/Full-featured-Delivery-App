package com.example.menuapplication.Fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menuapplication.Adapter.Commande_adapter
import com.example.menuapplication.R
import com.example.menuapplication.Retrofit.Endpoint1
import com.example.menuapplication.databinding.FragmentCommandeBinding
import kotlinx.coroutines.*

class FragmentCommande : Fragment() {

    lateinit var binding: FragmentCommandeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommandeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }


    fun loadData()
    {
        // recuper IDuser
        val pref = requireContext().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        val IdUser = pref.getInt("IdUser", 0)
        if( IdUser == 0 )
        {
            println("user pas authenif")
            val emptyCartMessage = requireView().findViewById<TextView>(R.id.emptyCartMessageCmd)
            emptyCartMessage.visibility = View.VISIBLE
            emptyCartMessage.text = "Pas de commandes!"
        }
        else {
            println("user authentif")
            val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                requireActivity().runOnUiThread {
                    // binding.progressBar.visibility = View.INVISIBLE
                    //Toast.makeText(requireActivity(), "Une erreur s'est produite : ${throwable.message} est (${throwable.javaClass.simpleName})", Toast.LENGTH_SHORT).show()
                }
            }
            val progressBar = requireActivity().findViewById<ProgressBar>(R.id.progressBareCmd)
            progressBar.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = Endpoint1.createEndpoint().getCmdsByIdUser( IdUser.toLong() )
                withContext(Dispatchers.Main)
                {
                    progressBar.visibility = View.INVISIBLE
                    if (response.isSuccessful && response.body() != null) {
                        val data = response.body()
                        if (data != null) {
                            binding.recyclerView.layoutManager =
                                LinearLayoutManager(requireActivity())
                            binding.recyclerView.adapter = Commande_adapter(requireActivity(), data)
                        }

                    } else {
                        //Toast.makeText( requireActivity(), "Une erreur s'est  ${response.body()}", Toast.LENGTH_SHORT ).show()
                    }
                }
                //----thread Main
            }
        }
    }

    private fun showEmptyCartMessage(text:String) {
        val emptyCartMessage = requireView().findViewById<TextView>(R.id.emptyCartMessageCmd)
        emptyCartMessage.visibility = View.VISIBLE
        emptyCartMessage.text = text
    }

}