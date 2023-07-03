package com.example.menuapplication.utilitaire

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

//---------------Continet des fonctions à utlisation standard

fun dialing(ctx:Context,num:String ){
    val data = Uri.parse("tel:$num")
    val intent = Intent(Intent.ACTION_DIAL, data)
    ctx.startActivity(intent)
}

fun map(ctx:Context, url: String) {

    val gmmIntentUri = Uri.parse(url)

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
// Make the Intent explicit by setting the Google Maps package
    mapIntent.setPackage("com.google.android.apps.maps")

// Attempt to start an activity that can handle the Intent
    ctx.startActivity(mapIntent)
}

fun openPage(ctx: Context, url: String, url2: String, packages:List<String>) {
    if(isAppInstalled(ctx,packages)){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        ctx.startActivity(intent)
    }else{
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url2))
        ctx.startActivity(intent)
    }
}
fun isPackageInstalled(c: Context, targetPackage: String): Boolean {
    val pm = c.packageManager
    try {
        pm.getPackageInfo(targetPackage,0)
        return true
    } catch (e: PackageManager.NameNotFoundException) {
        return false
    }
}


fun isAppInstalled(c: Context,packages:List<String>): Boolean{
    var result= false
    for (p in packages){
        if(isPackageInstalled(c,p)){
            result= true;
        }
    }
    return result
}
fun email(ctx:Context, email:String) {

    val intent = Intent(Intent.ACTION_VIEW,  Uri.parse("mailto:" + email))
    ctx.startActivity(intent)
}