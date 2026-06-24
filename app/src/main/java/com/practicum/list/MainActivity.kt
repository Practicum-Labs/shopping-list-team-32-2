package com.practicum.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.practicum.list.core.theme.ShoppingListTheme
import com.practicum.list.core.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = navHostController()
            ShoppingListTheme {
                NavGraph(
                    navController,
                    lists = lists
                )
            }
        }
    }
}
