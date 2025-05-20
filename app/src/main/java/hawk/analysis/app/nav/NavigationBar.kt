package hawk.analysis.app.nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController, navBarVisible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible = navBarVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {
        NavigationBar {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination

            NavBarItems.BarItems.forEach { navItem ->
                NavigationBarItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = navItem.image,
                            contentDescription = navItem.title
                        )
                    },
                    label = {
                        Text(text = navItem.title)
                    }
                )
            }
        }
    }

}

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Анализ",
            image = Icons.Outlined.BarChart,
            route = Destination.HomeScreen
        ),
        BarItem(
            title = "Главная",
            image = Icons.Outlined.Home,
            route = Destination.HomeScreen
        ),
        BarItem(
            title = "Настройки",
            image = Icons.Outlined.Settings,
            route = Destination.SettingsScreen
        )
    )
}

data class BarItem(
    val title: String,
    val image: ImageVector,
    val route: Destination
)