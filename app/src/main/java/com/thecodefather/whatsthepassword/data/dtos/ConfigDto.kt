package com.thecodefather.whatsthepassword.data.dtos

import org.joda.time.LocalDateTime


data class ConfigDto (
    var id: Int = 0,
    var groupMembersMax: Int = 1,
    var retentionDays: Int = 1,
    var enableAds: Boolean = false,
    var enableScreenshots: Boolean = false,
    var modificationDate: LocalDateTime = LocalDateTime.now()
)