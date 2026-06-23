package com.practicum.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.practicum.list.core.theme.ShoppingListTheme
import domain.ListEntry
import ui.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val lists = listOf(
            ListEntry("Первый список"),
            ListEntry("Первый список"),
            ListEntry("Первый список"),
            ListEntry("Первый список"),
            ListEntry("Первый список"),
        )

        setContent {
            NavGraph(
                //  gson = gson,
                lists = lists
            )
        }
    }
}
