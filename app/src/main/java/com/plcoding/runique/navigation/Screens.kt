package com.plcoding.runique.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {

    @Serializable
    data object Intro

    @Serializable
    object Home {
        @Serializable
        data object Run
    }

    @Serializable
    object Auth {
        @Serializable
        data object Login

        @Serializable
        data object Register
    }
}