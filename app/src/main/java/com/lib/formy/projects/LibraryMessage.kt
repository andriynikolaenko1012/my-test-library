package com.lib.formy.projects

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.google.gson.Gson
import java.net.URL
import java.util.concurrent.Executors

class LibraryMessage(private var firstActivity: Activity, private var secondActivity: Activity, private var content: String?) {
    fun init(s: String){
        if(InternetConnection.checkConnection(firstActivity)){
            if (content != null){
                Executors.newSingleThreadExecutor().execute{
                    val json = URL("${Utils().decodeString(s)}?$content").readText()
                    val convertedObject = Gson().fromJson(json, MyResponse::class.java)
                    if (convertedObject.data != null){
                        s(convertedObject.data!!)
                    } else {
                        finish()
                    }
                }
            } else {
                finish()
            }
        } else {
            finish()
        }
    }

    private fun finish(){
        firstActivity.startActivity(Intent(firstActivity, secondActivity::class.java))
        firstActivity.finish()
    }

    private fun s(z: String){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(Utils().decodeString(z))
        firstActivity.startActivity(i)
        firstActivity.finish()
    }
}