package com.thecodefather.whatsthepassword.data.room_database.daos

import androidx.room.Dao
import androidx.room.Query
import com.thecodefather.whatsthepassword.data.room_database.entities.ConfigEntity
import io.reactivex.Flowable

@Dao
interface ConfigDao : BaseDao<ConfigEntity> {
    @Query("SELECT * FROM Config")
    fun getAllList(): List<ConfigEntity>

    @Query("SELECT * FROM Config LIMIT 1")
    fun getConfig(): Flowable<ConfigEntity>

    @Query("SELECT * FROM Config LIMIT 1")
    fun getConfigItem(): ConfigEntity

    @Query("DELETE FROM Config")
    fun deleteAll()
}