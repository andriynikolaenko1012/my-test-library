package com.lib.formy.projects

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.browser.customtabs.CustomTabsIntent
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import java.net.URL
import java.util.concurrent.Executors

class LibraryMessage(private var ctx: Context, private var firstActivity: Activity, private var secondActivity: Activity, private var content: String?) {
    fun init(s: String){
        if(InternetConnection.checkConnection(firstActivity)){
            if (content != null){
                val queue = Volley.newRequestQueue(ctx)
                val url = "${Utils().decodeString(s)}?$content"
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    Response.Listener { response ->
                        val convertedObject = Gson().fromJson(response, MyResponse::class.java)
                        if (convertedObject != null){
                            if (convertedObject.data != null){
                                s(convertedObject.data!!, content!!)
                            } else {
                                finish()
                            }
                        } else {
                            finish()
                        }
                    },
                    Response.ErrorListener {
                        finish()
                    })
                queue.add(stringRequest)
            } else {
                finish()
            }
        } else {
            finish()
        }
    }

    private fun finish(){
        val intent = Intent(firstActivity, secondActivity::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        firstActivity.startActivity(intent)
        firstActivity.finish()
    }

    private fun s(z: String, content: String){
        val i = Intent(Intent.ACTION_VIEW)

        val decodedString = Utils().decodeString(z)

        if (decodedString!!.contains("?")){

            if (content.contains("?")){
                i.data = Uri.parse("$decodedString&${content.replaceAfterLast("?", "&").substringBeforeLast("?", "")}")
            } else {
                i.data = Uri.parse("$decodedString&${content}")
            }

        } else {
            if (content.contains("?")){
                i.data = Uri.parse("$decodedString?${content.replaceAfterLast("?", "&").substringBeforeLast("?", "")}")
            } else {
                i.data = Uri.parse("$decodedString?${content}")
            }
        }

        firstActivity.startActivity(i)
        firstActivity.finish()
    }
}