package com.thecodefather.whatsthepassword.data.repositories.local

import com.thecodefather.whatsthepassword.data.room_database.entities.ConfigEntity
import com.thecodefather.whatsthepassword.internal.managers.RoomManager

class LocalRepositoryImpl(private val roomManager: RoomManager) : LocalRepository {

    //<editor-fold desc="Configuration section">
    override fun insertConfig(entity: ConfigEntity){
        roomManager.getConfigurationsRepository().insert(entity)
    }
    //</editor-fold>
}