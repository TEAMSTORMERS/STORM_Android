package com.stormers.storm.card.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.*
import java.net.URL
import java.util.*


object BitmapConverter {

    private const val TAG = "BitmapConverter"
    private const val QUALITY = 70
    private val FORMAT = Bitmap.CompressFormat.JPEG

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

    fun bitmapToFile(bitmap: Bitmap, path: String) : File? {
        val filename = fileNameGenerator()

        val file = File(path, filename)

        try {
            file.createNewFile()

            val out = FileOutputStream(file)

            bitmap.compress(FORMAT, QUALITY, out)

            out.close()

            return file
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "FileNotFoundException : ${e.message}")

            return null
        } catch (e: IOException) {
            Log.e(TAG, "IOException : ${e.message}")

            return null
        }
    }

    private fun fileNameGenerator() : String {
        val fileName = StringBuilder()
        val calendar = GregorianCalendar()

        fileName.append(calendar.get(Calendar.YEAR).toString().substring(2))
            .append(calendar.get(Calendar.MONTH) + 1)
            .append(calendar.get(Calendar.DATE))
            .append(calendar.get(Calendar.MINUTE))
            .append(calendar.get(Calendar.SECOND))
            .append(calendar.get(Calendar.MILLISECOND))
            .append(".JPEG")

        return fileName.toString()
    }

    fun urlToBitmap(url: String) : Bitmap {
        val urls = URL(url)

        return BitmapFactory.decodeStream(urls.openStream())
    }
}
