package com.lib.formy.projects

import android.util.Base64
import java.io.UnsupportedEncodingException

class Utils() {
    fun decodeString(encoded: String): String? {
        val dataDec: ByteArray = Base64.decode(encoded, Base64.DEFAULT)
        var decodedString = ""
        try {
            decodedString = String(dataDec, charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } finally {
            return decodedString
        }
    }
}