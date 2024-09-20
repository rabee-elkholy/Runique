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
import com.plcoding.auth.presentation.login.LoginScreenRoot
import com.plcoding.auth.presentation.register.RegisterScreenRoot

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
        homeNavGraph(navController = navController)
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
            LoginScreenRoot(
                onSignUpClick = {
                    navController.navigate(route = Screens.Auth.Register) {
                        popUpTo(route = Screens.Auth.Login) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onLoginSuccess = {
                    navController.navigate(route = Screens.Home.Run) {
                        popUpTo(route = Screens.Auth) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Screens.Auth.Register> {
            RegisterScreenRoot(
                onLoginClick = {
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

private fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {
    navigation<Screens.Home>(
        startDestination = Screens.Home.Run,
    ) {
        composable<Screens.Home.Run> {
            Text(text = "Run")
        }
    }
}