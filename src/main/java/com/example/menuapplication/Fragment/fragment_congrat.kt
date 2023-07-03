package com.example.menuapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class fragment_congrat : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_congrat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val getStartedButton = view.findViewById<Button>(R.id.button)
        getStartedButton.setOnClickListener {
                val intent = Intent(this@fragment_congrat.requireContext(), AuthentifActivity::class.java)
                startActivity(intent)
        }
    }
}