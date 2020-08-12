package com.lib.formy.projects

import android.app.Activity
import android.content.Intent
import android.net.Uri

open class LibraryMessage() {
    fun s(activity: Activity, z: String){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(z)
        activity.startActivity(i)
        activity.finish()
    }
}