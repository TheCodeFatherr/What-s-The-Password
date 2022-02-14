package com.thecodefather.whatsthepassword.internal.managers

import com.thecodefather.whatsthepassword.WhatsPasswordApplication
import com.thecodefather.whatsthepassword.data.room_database.repositories.ConfigRepository


/**
 *
 * Created by Hussein Yassine on 05, March, 2019.
 *
 */

class RoomManager {
    fun getConfigurationsRepository(): ConfigRepository {
        return ConfigRepository(WhatsPasswordApplication.database.configDao())
    }
}