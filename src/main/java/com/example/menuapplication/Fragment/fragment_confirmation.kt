package com.example.menuapplication
import android.util.Log
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


class fragment_confirmation : Fragment() {

   // private lateinit var codeVerification: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val codeVerification = arguments?.getString("codeVerification")

        val continueButton = view.findViewById<Button>(R.id.button)
        val codeEditText = view.findViewById<EditText>(R.id.editTextNumberDecimal)

        val retour = requireActivity().findViewById<ImageView>(R.id.imageView)
        retour.setOnClickListener {
            view?.findNavController()?.navigateUp()
        }
        continueButton.setOnClickListener {
            val enteredCode = codeEditText.text.toString()

            Log.d("TAG", "codeVerification =============================")
            if (codeVerification != null) {
                Log.d("TAG", codeVerification)
            }
            if (enteredCode.isNotEmpty() && enteredCode == codeVerification) {
                (activity as? RegistrationActivity)?.replaceFragment3()

            } else {
                // Code verification failed or the code is empty
                Toast.makeText(context, "Verification code is wrong, try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

}