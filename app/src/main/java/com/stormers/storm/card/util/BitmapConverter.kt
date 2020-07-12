package com.stormers.storm.card.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream


object BitmapConverter {

    private const val TAG = "BitmapConverter"
    private const val QUALITY = 70

    // String -> Bitmap
    fun stringToBitmap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)

        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            null
        }
    }

    //Bitmap -> String
    fun bitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, baos)

        val bytes: ByteArray = baos.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    //Bitmap -> ByteArray
    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos)
        return baos.toByteArray()
    }
}