package com.stormers.storm.card.repository

import com.stormers.storm.base.BaseRepository
import com.stormers.storm.card.dao.SavedCardDao
import com.stormers.storm.card.model.SavedCardEntity
import com.stormers.storm.ui.GlobalApplication

class SavedCardRepository : BaseRepository<SavedCardEntity>() {

    private val dao: SavedCardDao by lazy { GlobalApplication.databaseManager.savedCardDao() }

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