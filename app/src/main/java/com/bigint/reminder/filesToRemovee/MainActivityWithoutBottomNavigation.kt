package com.bigint.reminder.filesToRemovee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bigint.reminder.ui.screens.mainBottomMenu.home.CharacterDetailsScreen
import com.bigint.reminder.ui.screens.mainBottomMenu.home.CharacterEpisodeScreen
import com.bigint.reminder.ui.theme.ReminderAppTheme
import com.bigint.reminder.ui.theme.RickPrimary
import com.bigint.network.KtorClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivityWithoutBottomNavigation : ComponentActivity() {

    @Inject
    lateinit var ktorClient: KtorClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            ReminderAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = RickPrimary
                ) {
                    NavHost(navController = navController, startDestination = "home_screen") {
                        composable(route = "home_screen") {
//                            HomeScreen(onCharacterSelected = { characterId ->
//                                navController.navigate("character_details/$characterId")
//                            })
                        }
                        composable(
                            route = "character_details/{characterId}",
                            arguments = listOf(navArgument("characterId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val characterId: Int =
                                backStackEntry.arguments?.getInt("characterId") ?: -1
                            CharacterDetailsScreen(
                                characterId
                            ) {
                                val userName = "Hassan Raza Muhammad Hanif"
                                navController.navigate("character_episodes?userName=$userName&characterId=$it")
                            }
                        }
                        composable(
                            "character_episodes?userName={userName}&characterId={characterId}",
                            arguments = listOf(navArgument("characterId") {
                                type = NavType.IntType
                            }, navArgument("userName") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val userName: String =
                                backStackEntry.arguments?.getString("userName") ?: "No Name"
                            val characterId: Int =
                                backStackEntry.arguments?.getInt("characterId") ?: -1
                            CharacterEpisodeScreen(
                                ktorClient = ktorClient,
                                characterId = characterId,
                                name = userName
                            )
                        }
                    }

                }
            }
        }
    }
}