package com.example.menuapplication
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.menuapplication.Entity.UserEntity
import com.example.menuapplication.Retrofit.Endpoint1
import com.example.menuapplication.databinding.FragmentSignup2Binding
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import kotlin.random.Random

class fragment_signup_2 : Fragment() {


    private var _binding: FragmentSignup2Binding? = null
    private val binding get() = _binding!!
    private val pickImage = 100
    private val REQUEST_IMAGE_CAPTURE = 0
    private var imageUri: Uri? = null

    lateinit var codeVerification:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignup2Binding.inflate(inflater, container, false)

        return binding.root
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.imageView9.setImageURI(imageUri)
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imageView11.setImageBitmap(imageBitmap)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextEmail = view.findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val editTextPassword = view.findViewById<EditText>(R.id.editTextTextPassword)
        val registerButton = view.findViewById<Button>(R.id.button)
        binding!!.imageView11.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        binding!!.imageView9.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(captureIntent, REQUEST_IMAGE_CAPTURE)
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_IMAGE_CAPTURE
                )
            }
        }

        val retour = requireActivity().findViewById<ImageView>(R.id.imageView)
        retour.setOnClickListener {
            view.findNavController().navigateUp()
        }
        registerButton.setOnClickListener { view ->
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            val name = arguments?.getString("name")
            val phoneNumber = arguments?.getString("phoneNumber")
            val address = arguments?.getString("address")

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {

                val imageFile = getImageFile()

                if (imageFile != null) {
                    // Create a request body from the image file
                    val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                }
                //*********************debut courotine
                val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                    requireActivity().runOnUiThread {
                        // binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(
                            requireActivity(),
                            "Une erreur s'est produite : ${throwable.message} est (${throwable.javaClass.simpleName})",
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.d(
                            TAG,
                            "Une erreur s'est produite : ${throwable.message} est (${throwable.javaClass.simpleName})"
                        )
                    }
                }


                val user = UserEntity(
                        Name = name,
                        email = email,
                        phoneNumber = phoneNumber,
                        address = address,
                        password = password,
                        profilePicture = imageFile?.absolutePath.toString()
                    )


                Log.d("TAG", user.toString())
                //progressBar.visibility = View.VISIBLE
                CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                    val response = Endpoint1.createEndpoint().addUser(user)
                    withContext(Dispatchers.Main)
                    {
                        //progressBar.visibility = View.INVISIBLE


                        codeVerification = generateConfirmationCode()
                        Log.d("TAG", "codeVerification =============================")
                        Log.d("TAG", codeVerification)

                        val fragmentConfirmation = fragment_confirmation().apply {
                            arguments = Bundle().apply {
                                putString("codeVerification", codeVerification)
                            }
                        }

                        //method call for email intent with these inputs as parameters
                        sendEmail("js_laouadi@esi.dz", "Verification Code", codeVerification)
                        (activity as? RegistrationActivity)?.replaceFragment2(fragmentConfirmation)
                        if (response.isSuccessful && response.body() != null) {
                            Toast.makeText(requireActivity(), "user addded", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                "Une erreur, details: ${response.body()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    //----thread Main
                }

            }
        }
    }

    fun generateConfirmationCode(): String {
        val confirmationCodeLength = 6 // Specify the length of the confirmation code
        val confirmationCodeChars = "0123456789" // Define the characters allowed in the confirmation code

        val codeBuilder = StringBuilder(confirmationCodeLength)
        val random = Random.Default

        repeat(confirmationCodeLength) {
            val randomIndex = random.nextInt(confirmationCodeChars.length)
            val randomChar = confirmationCodeChars[randomIndex]
            codeBuilder.append(randomChar)
        }

        return codeBuilder.toString()
    }




    @SuppressLint("IntentReset")
    private fun sendEmail(recipient: String, subject: String, message: String) {
        /*ACTION_SEND action to launch an email client installed on your Android device.*/
        val mIntent = Intent(Intent.ACTION_SEND)
        /*To send an email you need to specify mailto: as URI using setData() method
        and data type will be to text/plain using setType() method*/
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        // put recipient email in intent
        /* recipient is put as array because you may wanna send email to multiple emails
           so enter comma(,) separated emails, it will be stored in array*/
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        //put the Subject in the intent
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        //put the message in the intent
        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
        }

    }
        private fun getImageFile(): File? {
            return imageUri?.let { uri ->
                val filePath = getRealPathFromUri(uri)
                if (filePath != null) File(filePath) else null
            }
        }

        private fun getRealPathFromUri(uri: Uri): String? {
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = requireActivity().contentResolver.query(uri, filePathColumn, null, null, null)
            cursor?.let {
                it.moveToFirst()
                val columnIndex = it.getColumnIndex(filePathColumn[0])
                val filePath = it.getString(columnIndex)
                it.close()
                return filePath
            }
            return null
        }

}
