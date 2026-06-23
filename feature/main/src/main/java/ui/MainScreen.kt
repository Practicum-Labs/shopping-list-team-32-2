package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import domain.ListEntry

@Composable
fun MainScreen(
    navController: NavController,
    lists: List<ListEntry>,
    // gson: Gson
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        for (list in lists) {
            Text(
                text = list.name,
                modifier = Modifier
                    .clickable {
                        val listJson = ""//gson.toJson(list)
                        navController.navigate("listDetails/$listJson")
                    }
            )
        }
    }

}