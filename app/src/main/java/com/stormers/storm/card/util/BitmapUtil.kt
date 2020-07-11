package com.stormers.storm.card.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.StringBuilder
import java.util.*

/**
 * Bitmap 관련 Util Class
 * @Created by KIM SEONGGYU
 * @since 2020.07.11
 */
class BitmapUtil(val context: Context) {

    companion object {
        private const val QUALITY = 100
        private val FORMAT = Bitmap.CompressFormat.JPEG
    }

    private val TAG = this.javaClass.name

    //비트맵 형태의 카드를 내부 저장소에 저장
    fun scarpCard(bitmap: Bitmap) : Boolean {
        val filename = fileNameGenerator()

        val file = File(context.filesDir, filename)

        try {
            file.createNewFile()

            val out = FileOutputStream(file)

            bitmap.compress(FORMAT, QUALITY, out)

            out.close()

            return true
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "FileNotFoundException : ${e.message}")

            return false
        } catch (e: IOException) {
            Log.e(TAG, "IOException : ${e.message}")

            return false
        }
    }

    //내부 저장소에 저장된 모든 카드를 불러옴
    fun getAllScrapedCardToBitmap() : List<Bitmap>? {
        val file = File(context.filesDir.toString())
        val files : Array<File>? = file.listFiles()

        files?.let {

            return List(files.size) { i ->
                BitmapFactory.decodeFile(files[i].path)
            }
        }
        return null
    }

    //파일 이름 생성 메서드
    private fun fileNameGenerator() : String {
        val fileName = StringBuilder()
        val calendar = GregorianCalendar()

        fileName.append(calendar.get(Calendar.YEAR).toString().substring(2))
            .append(calendar.get(Calendar.MONTH) + 1)
            .append(calendar.get(Calendar.DATE))
            .append(calendar.get(Calendar.MINUTE))
            .append(calendar.get(Calendar.SECOND))
            .append(calendar.get(Calendar.MILLISECOND))

        return fileName.toString()
    }
}