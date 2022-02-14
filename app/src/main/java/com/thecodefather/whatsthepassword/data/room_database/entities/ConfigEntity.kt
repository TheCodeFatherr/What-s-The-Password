package com.thecodefather.whatsthepassword.data.room_database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thecodefather.whatsthepassword.data.dtos.ConfigDto
import com.thecodefather.whatsthepassword.internal.DateUtilities
import com.thecodefather.whatsthepassword.internal.extensions.formatToDefaultDate

@Entity(tableName = "Config")
data class ConfigEntity (
    @PrimaryKey
    var id: Int = 0,
    @ColumnInfo(name = "groupMembersMax")
    var groupMembersMax: Int = 0,
    @ColumnInfo(name = "retentionDays")
    var retentionDays: Int = 0,
    @ColumnInfo(name = "enableAds")
    var enableAds: Boolean = false,
    @ColumnInfo(name = "enableScreenshots")
    var enableScreenshots: Boolean = false,
    @ColumnInfo(name = "modificationTime")
    var modificationTime: Long = DateUtilities.getNowTimeUTC()
){
    fun toConfigDto(): ConfigDto {
        return ConfigDto(
            id = id,
            groupMembersMax = groupMembersMax,
            retentionDays = retentionDays,
            enableAds = enableAds,
            enableScreenshots = enableScreenshots,
            modificationDate = modificationTime.formatToDefaultDate()
        )
    }
}