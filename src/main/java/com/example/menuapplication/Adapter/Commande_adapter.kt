package com.example.menuapplication.Adapter
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.menuapplication.Entity.Commande
import com.example.menuapplication.databinding.CommandeLayoutBinding
import com.example.menuapplication.utilitaire.dialing
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Commande_adapter( val context: Context, val data:List<Commande> ):RecyclerView.Adapter<Commande_adapter.MyViewHolder>()
{
    //FragmentCommandeBinding

    class MyViewHolder(val binding: CommandeLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            CommandeLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply{
            numCmd.setText( "Num: "+( data.size -  position ).toString() )
            restauCmd.setText("Depuis Restau:"+data[position].restauName)
            totaleCmd.setText(data[position].PrixTotale.toString() + " DZA")

            //transformer la date
            val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val dateTime = LocalDateTime.parse( data[position].date_cmd, formatter)

            val date = dateTime.toLocalDate()
            val time = dateTime.toLocalTime()
            dateCmd.setText( date.toString() )
            heureCmd.setText( time.toString() )

            //------suite
            etatCmd.setText( data[position].etat )
            detailsCmd.setOnClickListener {
            }
            phoneLivreur.setOnClickListener{
                dialing(context, "tel:" + "001122334455")
            }

        }

    }

    override fun getItemCount() = data.size

}




