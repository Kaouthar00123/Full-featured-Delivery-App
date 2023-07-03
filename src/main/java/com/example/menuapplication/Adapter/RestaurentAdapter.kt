package com.example.menuapplication.Adapter

import RatingDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.menuapplication.ActivityMenu_details
import com.example.menuapplication.Entity.RestauEntity
import com.example.menuapplication.Entity.SharedData
import com.example.menuapplication.Fragment.FragmentListeRestau
import com.example.menuapplication.databinding.RestaurentLayoutBinding
import com.example.menuapplication.utilitaire.dialing
import com.example.menuapplication.utilitaire.email
import com.example.menuapplication.utilitaire.map
import com.example.menuapplication.utilitaire.openPage


class RestaurentAdapter(
    val ctx: Context, val data:List<RestauEntity>,
    val listener: FragmentListeRestau
):RecyclerView.Adapter<RestaurentAdapter.MyViewHolder>() {
    lateinit var sharedData: SharedData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RestaurentLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), ctx,  data)
    }

    override fun getItemCount() = data.size

     override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {

            nom.text = data[position].Name
            address.text = data[position].Address
            type.text= data[position].category
            rate.text= data[position].stars.toString()
            reviews.text= data[position].viewers.toString()

            Glide.with(ctx).load(  data[position].image).into(logo)
            logo.minimumWidth = 120
            logo.maxWidth = 150
            logo.minimumHeight = 150
            logo.maxHeight = 190

            email.setOnClickListener {
                email(ctx, data[position].Email)
            }
            fb.setOnClickListener{
                //getOpenFacebookIntent(ctx,data[position].fbUrl,data[position].fbWebUrl);
                openPage(ctx,"https://www.facebook.com/","https://www.facebook.com/",listOf<String>("com.facebook.katana" , "com.facebook.katana"));
            }

            fb2.setOnClickListener{
                //getOpenFacebookIntent(ctx,data[position].fbUrl,data[position].fbWebUrl);
                openPage(ctx,"http://instagram.com/","http://instagram.com/",listOf<String>("com.instagram.android" , "com.instagram.android"));
            }
            phone.setOnClickListener{
                dialing(ctx, "tel:" + data[position].Numphone)
            }
            address.setOnClickListener {
                map(ctx,data[position].MapAddress)
            }
            //passe vers activité menu
            logo.setOnClickListener {
                val intent = Intent(ctx, ActivityMenu_details::class.java)
                intent.putExtra("id_restau",data[position].RestauId)
                ctx.startActivity(intent)
            }
            val ratingDialog = RatingDialog()
            comment.setOnClickListener {
                ratingDialog.show((ctx as FragmentActivity).supportFragmentManager, "RatingDialog")
            }
        }
         holder.bind(data[position], listener)
    }


    class MyViewHolder(val binding: RestaurentLayoutBinding, val ctx: Context, val data:List<RestauEntity>) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(item: RestauEntity, listener: OnItemClickListener)
        {
            itemView.setOnClickListener {
                /*listener.onItemClick(item)
                val intent = Intent(ctx, ActivityMenu_details::class.java)
                intent.putExtra("id_restau",data[position].RestauId)
                ctx.startActivity(intent)*/
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(item: RestauEntity)
    }

}



