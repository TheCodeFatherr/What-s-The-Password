package com.thecodefather.whatsthepassword.data.dtos

data class WelcomeScreenDto (
    var id: Int = 0,
    var imageId: Int = 0,
    var screenTitle: String = "",
    var screenText: String = "",
    var isLast: Boolean = false
){

    companion object {
        fun defaultScreens(): List<WelcomeScreenDto> {
            return listOf()
        }
    }
}