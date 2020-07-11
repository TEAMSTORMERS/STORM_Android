package com.stormers.storm.card.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.stormers.storm.database.DatabaseManager
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.StringBuilder
import java.net.URL
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

    //내부 저장소에 저장된 모든 카드를 불러옴
    fun getAll() : List<Bitmap>? {
        val file = File(context.filesDir.toString())
        val files : Array<File>? = file.listFiles()

        files?.let {

            return List(files.size) { i ->
                BitmapFactory.decodeFile(files[i].path)
            }
        }
        return null
    }

    //파라미터로 넘겨진 파일 이름을 가진 비트맵을 가져옴
    fun getAll(fileName: List<String>?) : List<Bitmap>? {

        fileName?.let {
            return List(fileName.size) { i ->
                val path = StringBuilder()
                path.append(context.filesDir)
                    .append("/")
                    .append(fileName[i])

                BitmapFactory.decodeFile(path.toString())
            }
        }
        return null
    }

    //비트맵을 내부 저장소에 저장
    private fun saveBitmap(bitmap: Bitmap) : String? {
        val filename = fileNameGenerator()

        val file = File(context.filesDir, filename)

        try {
            file.createNewFile()

            val out = FileOutputStream(file)

            bitmap.compress(FORMAT, QUALITY, out)

            out.close()

            return filename
        } catch (e: FileNotFoundException) {
            Log.e(TAG, "FileNotFoundException : ${e.message}")

            return null
        } catch (e: IOException) {
            Log.e(TAG, "IOException : ${e.message}")

            return null
        }
    }

    //url을 비트맵화 하여 내부 저장소에 저장
    fun saveCard(url: String) : String? {
        return saveBitmap(urlToBitmap(url))
    }

    //비트맵을 내부 저장소에 저장
    fun saveCard(bitmap: Bitmap) : String? {
        return saveBitmap(bitmap)
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

    //url을 Bitmap으로 변경
    private fun urlToBitmap(url: String) : Bitmap {
        val urls = URL(url)

        return BitmapFactory.decodeStream(urls.openStream())
    }
}
