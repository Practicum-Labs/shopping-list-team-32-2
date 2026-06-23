package ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import domain.ListEntry

@Composable
fun ListScreen(list: ListEntry) {
    Text(list.name)
}