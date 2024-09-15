package com.plcoding.runique.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plcoding.auth.presentation.intro.IntroScreenRoot
import com.plcoding.auth.presentation.register.RegisterScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Intro
    ) {
        composable<Screens.Intro> {
            IntroScreenRoot(
                onSignInClick = { navController.navigate(route = Screens.Auth.Login) },
                onSignUpClick = { navController.navigate(route = Screens.Auth.Register) }
            )
        }
        authNavGraph(navController = navController)
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation<Screens.Auth>(
        startDestination = Screens.Auth.Register,
    ) {
        composable<Screens.Auth.Login> {
            Text(text = "Login Screen")
        }

        composable<Screens.Auth.Register> {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate(route = Screens.Auth.Login) {
                        popUpTo(route = Screens.Auth.Register) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onRegistrationSuccess = {
                    navController.navigate(route = Screens.Auth.Login)
                }
            )
        }
    }
}