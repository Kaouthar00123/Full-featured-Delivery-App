import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.menuapplication.R


class RatingDialog : DialogFragment() {
    lateinit var comment:String
   var rating:Float = 0.0f

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Rate the App")

        val contentView =
            requireActivity().layoutInflater.inflate(R.layout.fragment_rating_dialog, null)
        builder.setView(contentView)

        // Récupérez les éléments de texte et de notation
        val commentEditText = contentView.findViewById<EditText>(R.id.editTextComment)
        val ratingBar = contentView.findViewById<RatingBar>(R.id.ratingBar)

        builder.setPositiveButton("Submit") { dialog, which ->
            comment = commentEditText.text.toString()
            rating = ratingBar.rating
        }

            return builder.create()

    }
}

