package com.thecodefather.whatsthepassword.data.room_database.repositories

import com.thecodefather.whatsthepassword.data.room_database.daos.ConfigDao
import com.thecodefather.whatsthepassword.data.room_database.entities.ConfigEntity
import io.reactivex.Flowable

class ConfigRepository (private val configDao: ConfigDao) {

    fun getConfig(): Flowable<ConfigEntity> {
        return configDao.getConfig()
    }

    fun insert(entity: ConfigEntity) {
        configDao.insertItem(entity)
    }

    fun insertList(list: List<ConfigEntity>) {
        configDao.insertItems(list)
    }

    fun update(entity: ConfigEntity) {
        configDao.updateItem(entity)
    }

    fun delete(entity: ConfigEntity){
        configDao.deleteItem(entity)
    }
}