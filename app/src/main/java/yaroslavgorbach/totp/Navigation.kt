package yaroslavgorbach.totp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.coroutines.InternalCoroutinesApi

sealed class Screen(val route: String) {
    object Tokens : Screen("Tokens")
}

private sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Tokens : LeafScreen("Tokens")
}

@InternalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Tokens.route,
        modifier = modifier,
    ) {
        addExercisesTopLevel(navController)
    }
}

@InternalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
private fun NavGraphBuilder.addExercisesTopLevel(
    navController: NavController,
) {
    navigation(
        route = Screen.Tokens.route,
        startDestination = LeafScreen.Tokens.createRoute(Screen.Tokens),
    ) {
        addTokens(navController, Screen.Tokens)
    }
}

@ExperimentalMaterialApi
private fun NavGraphBuilder.addTokens(
    navController: NavController,
    root: Screen,
) {
    composable(LeafScreen.Tokens.createRoute(root)) {

    }
}
