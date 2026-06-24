package com.practicum.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.compose.rememberNavController
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.core.navigation.NavGraph
import com.practicum.list.feature.main.ui.screens.MainScreen
import com.practicum.list.feature.product.ui.screens.ListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            ShoppingListTheme {
                NavGraph(
                    navController,
                    mainScreen = MainScreen(),
                    listScreen = ListScreen()
                )
            }
        }
    }
}
