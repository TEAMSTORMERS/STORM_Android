package com.stormers.storm.card.repository

import android.content.Context
import android.graphics.Bitmap
import com.stormers.storm.base.BaseRepository
import com.stormers.storm.card.dao.SavedCardDao
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.card.util.BitmapConverter
import com.stormers.storm.util.DatabaseManager
import java.lang.Exception

class SavedCardRepository(val context: Context) : BaseRepository<SavedCardEntity>() {

    private val dao: SavedCardDao by lazy { DatabaseManager.getInstance(context).savedCardDao() }

    fun getAllAsBitmap(projectIdx: Int): List<Bitmap>? {
        return getBitmapArray(dao.getAll(projectIdx))
    }

    fun getAllAsBitmap(projectIdx: Int, roundIdx: Int): List<Bitmap>? {
        return getBitmapArray(dao.getAll(projectIdx, roundIdx))
    }

    fun getAllScarpedCardAsBitmap(projectIdx: Int): List<Bitmap>? {
        return getBitmapArray(dao.getAllScrapedCard(projectIdx))
    }

    fun getAllScrapedCardAsBitmap(projectIdx: Int, roundIdx: Int): List<Bitmap>? {
        return getBitmapArray(dao.getAllScrapedCard(projectIdx, roundIdx))
    }

    private fun getBitmapArray(strArray: List<String>): List<Bitmap>? {
        return List(strArray.size) { i ->
            try {
                BitmapConverter.stringToBitmap(strArray[i])!!
            } catch (e: Exception) {
                return null
            }
        }
    }
}