package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.runtime.*
import routing.Routes


@Composable
@Preview
fun HomeScreen(onNavigate: (Routes) -> Unit) {

    Column {
        Text("Coucou")
        Button(
            onClick = { onNavigate(Routes.CARISTE)}
        ) {
            Text("Gestion des Caristes")
        }
        Button(
            onClick = { onNavigate(Routes.COLIS)}
        ) {
            Text("Gestion des Colis")
        }
    }
}

