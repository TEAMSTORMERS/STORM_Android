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

    fun getAll(projectIdx: Int): List<SavedCardEntity>? {
        return dao.getAll(projectIdx)
    }

    fun getAll(projectIdx: Int, roundIdx: Int): List<SavedCardEntity>? {
        return dao.getAll(projectIdx, roundIdx)
    }

    fun getAllScrapedCard(projectIdx: Int): List<SavedCardEntity> {
        return dao.getAllScrapedCard(projectIdx)
    }

    fun getAllScrapedCard(projectIdx: Int, roundIdx: Int): List<SavedCardEntity>? {
        return dao.getAllScrapedCard(projectIdx, roundIdx)
    }

    fun delete(projectIdx: Int, roundIdx: Int) {
        dao.deleteAll(projectIdx, roundIdx)
    }

    public override fun insert(entity: SavedCardEntity) {
        super.insert(entity)
        dao.insert(entity)
    }

    public override fun update(entity: SavedCardEntity) {
        super.update(entity)
        dao.update(entity)
    }
}