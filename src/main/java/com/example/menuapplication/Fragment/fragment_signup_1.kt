package com.example.menuapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.findNavController


class fragment_signup_1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retour = requireActivity().findViewById<ImageView>(R.id.imageView)
        retour.setOnClickListener {
            view.findNavController().navigateUp()
        }


        // Find views by their respective IDs
        val Next = requireActivity().findViewById<Button>(R.id.Next)
        val nameEditText = requireActivity().findViewById<EditText>(R.id.editTextTextPersonName6)
        val phoneNumberEditText = requireActivity().findViewById<EditText>(R.id.editTextPhone)
        val addressEditText = requireActivity().findViewById<EditText>(R.id.editTextTextPersonName4)
        Next.setOnClickListener {view ->
            val name = nameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val address = addressEditText.text.toString()

            val fragmentSignup2 = fragment_signup_2().apply {
                arguments = Bundle().apply {
                    putString("name", name)
                    putString("phoneNumber", phoneNumber)
                    putString("address", address)
                }
            }

            if (address.isEmpty() || name.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                (activity as? RegistrationActivity)?.replaceFragment(fragmentSignup2)

            }
        }
    }
}