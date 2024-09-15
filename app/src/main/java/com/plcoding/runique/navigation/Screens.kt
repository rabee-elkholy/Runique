package com.plcoding.runique.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {

    @Serializable
    data object Intro

    @Serializable
    object App {
        @Serializable
        data class Home(
            val name: String = "default",
        )
    }

    @Serializable
    object Auth {
        @Serializable
        data object Login

        @Serializable
        data object Register
    }
}