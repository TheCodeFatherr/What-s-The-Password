package com.thecodefather.whatsthepassword.data.repositories.local

import com.thecodefather.whatsthepassword.data.room_database.entities.ConfigEntity


interface LocalRepository {

    //<editor-fold desc="Configuration section">
    fun insertConfig(entity: ConfigEntity)
    //</editor-fold>
}