package com.example.menuapplication.Adapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.menuapplication.Entity.MenuEntity
import com.example.menuapplication.Entity.SharedData
import com.example.menuapplication.R
import com.example.menuapplication.databinding.MenufoodLayaoutBinding

class menu_adapter(val data:List<MenuEntity>, val context: Context, val listener: OnItemClickListener):RecyclerView.Adapter<menu_adapter.MyViewHolder>() {

    class MyViewHolder(val binding:MenufoodLayaoutBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(item: MenuEntity, listener: OnItemClickListener) {
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: MenuEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(MenufoodLayaoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.binding.apply{
            name.text= data[position].Name
            Glide.with(context).load(  data[position].image).into(img)
            price.text= data[position].prixUnitaire.toString()

            holder.bind(data[position], listener)

            holder.itemView.setOnClickListener {
                val sharedData = SharedData.getInstance()
                sharedData.FoodInformation = data[position]
                Toast.makeText(context, "Vous avez cliqué sur ${name.text}", Toast.LENGTH_SHORT).show()
                it.findNavController().navigate( R.id.action_fragmentMenuFood_to_fragmentDetailFood2 )
            }
        }
    }
}




