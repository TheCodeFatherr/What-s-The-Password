package com.thecodefather.whatsthepassword.data.dtos

import com.thecodefather.whatsthepassword.R
import com.thecodefather.whatsthepassword.internal.extensions.getStringResource

data class WelcomeScreenDto (
    var id: Int = 0,
    var imageId: Int = 0,
    var screenTitle: String = "",
    var screenText: String = "",
    var isLast: Boolean = false
){

    companion object {
        fun defaultScreens(): List<WelcomeScreenDto> {
            return listOf(
                WelcomeScreenDto(
                    id = 1,
                    imageId = R.mipmap.ic_launcher,
                    screenTitle = getStringResource(R.string.app_name),
                    screenText = getStringResource(R.string.screen_one)
                ),
                WelcomeScreenDto(
                    id = 2,
                    imageId = R.mipmap.ic_launcher,
                    screenTitle = getStringResource(R.string.how_it_works),
                    screenText = getStringResource(R.string.screen_two)
                ),
                WelcomeScreenDto(
                    id = 3,
                    imageId = R.mipmap.ic_launcher,
                    screenTitle = getStringResource(R.string.keep_the_pin_safe),
                    screenText = getStringResource(R.string.screen_three),
                    isLast = true
                )
            )
        }
    }
}