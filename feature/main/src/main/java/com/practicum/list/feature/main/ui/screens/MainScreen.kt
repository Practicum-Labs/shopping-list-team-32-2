package com.practicum.list.feature.main.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.practicum.list.core.common.domain.ShoppingList

@Composable
fun MainScreen(
    itemsList: List<ShoppingList>,
    onItemTap: (id: Long) -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(bottom = 0),
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            Text("Мэйн скрин")

            itemsList.forEach { item ->
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { onItemTap(item.id) },
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(item.iconResId),
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Айтем ${item.name}")
                    }
                }
            }
        }
    }
}
