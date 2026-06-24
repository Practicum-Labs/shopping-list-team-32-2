package com.practicum.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.core.navigation.MainNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            ShoppingListTheme {
                MainNavGraph(
                    navController,
                    mainScreenNavigation(),
                    listScreenNavigation()
                )
            }
        }
    }
}
