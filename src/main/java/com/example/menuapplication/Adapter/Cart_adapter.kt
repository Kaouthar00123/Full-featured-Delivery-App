package com.example.menuapplication.Adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.menuapplication.DataModel.FoodCart
import com.example.menuapplication.RoomDB.AppDB
import com.example.menuapplication.databinding.FoodcartLayaoutBinding

class cart_adapter(val data:MutableList<FoodCart>, val context: Context):RecyclerView.Adapter<cart_adapter.MyViewHolder>()
{

    class MyViewHolder(val binding:FoodcartLayaoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            FoodcartLayaoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply{
            name.setText( data[position].name )
            Glide.with(context).load(data[position].img).into(imgFoodCart)
            unitprice.setText( data[position].unitprice.toString() )
            quantite.setText( data[position].quantite.toString() )
            totaleprice.setText( data[position].totaleprice.toString() )

            add.setOnClickListener {
                var qu = quantite.getText().toString().toInt()
                qu = qu + 1
                quantite.setText( qu.toString() )

                var prix = unitprice.getText().toString().toFloat()
                prix = prix * (qu)
                totaleprice.setText( prix.toString() )

                data[position].quantite = qu.toString()
                data[position].totaleprice = prix.toString()

                data[position].FoodPanierId?.let { it1 ->
                    AppDB.getInstance(context)?.getFoodPanierDao()?.updateFoodPanier(
                        it1, qu, data[position].specialInstruction )
                }
            }
            mince.setOnClickListener {
                var qu = quantite.getText().toString().toInt()
                qu = qu - 1
                quantite.setText( qu.toString() )

                var prix = unitprice.getText().toString().toFloat()
                prix = prix * (qu)

                totaleprice.setText( prix.toString() )

                data[position].quantite = qu.toString()
                data[position].totaleprice = prix.toString()
                data[position].FoodPanierId?.let { it1 ->
                    AppDB.getInstance(context)?.getFoodPanierDao()?.updateFoodPanier(
                        it1, qu, data[position].specialInstruction )
                }
            }

            valider.setOnClickListener{
                    Toast.makeText(context, "tu veux changer details ?", Toast.LENGTH_SHORT).show()
            }
            delete.setOnClickListener{
                data[position].FoodPanierId?.let { it1 ->
                    AppDB.getInstance(context)?.getFoodPanierDao()?.deleteFoodPanierById(
                        it1
                    )
                }
                data.removeAt(position)
                notifyItemRemoved(position)
            }
        }

    }

    override fun getItemCount() = data.size


}




