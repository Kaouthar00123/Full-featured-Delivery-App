package com.example.menuapplication.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menuapplication.ActivityMenu_details
import com.example.menuapplication.Adapter.RestaurentAdapter
import com.example.menuapplication.Entity.RestauEntity
import com.example.menuapplication.R
import com.example.menuapplication.Retrofit.Endpoint1
import com.example.menuapplication.databinding.FragmentListeRestauBinding
import kotlinx.coroutines.*


class FragmentListeRestau : Fragment() ,  RestaurentAdapter.OnItemClickListener{

    lateinit var binding: FragmentListeRestauBinding
    //lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = FragmentListeRestauBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }
    fun loadData() {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {
                // binding.progressBar.visibility = View.INVISIBLE
                // Toast.makeText(requireActivity(), "Une erreur s'est produite : ${throwable.message} est (${throwable.javaClass.simpleName})", Toast.LENGTH_SHORT).show()            }
            }
        }
            val progressBar = requireActivity().findViewById<ProgressBar>(R.id.progressBareRestau)
            progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = Endpoint1.createEndpoint().getAllResaturents()
                withContext(Dispatchers.Main)
                {
                    progressBar.visibility = View.INVISIBLE

                    if (response.isSuccessful && response.body() != null) {
                        val data = response.body()
                        if (data != null) {
                            binding.recyclerView.layoutManager =
                                LinearLayoutManager(requireActivity())
                            binding.recyclerView.adapter =
                                RestaurentAdapter(requireActivity(), data, this@FragmentListeRestau)
                        }
                    } else {
                        //Toast.makeText(requireActivity(), "Une erreur s'est  ${response.body()}", Toast.LENGTH_SHORT).show()
                    }
                }
                //----thread Main
            }


        }


        //------action when clique sur un restaurant
        override fun onItemClick(item: RestauEntity) {
            val intent = Intent(requireActivity(), ActivityMenu_details::class.java)
            intent.putExtra("id_restau", item.Name)
            startActivity(intent)
        }


}
