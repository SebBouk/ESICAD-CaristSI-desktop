package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import routing.Routes


@Composable
@Preview
fun HomeScreen(onNavigate: (Routes) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(), // Occupe tout l'espace disponible
        contentAlignment = Alignment.Center // Centre le contenu au milieu de l'écran
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Centre les éléments horizontalement
            verticalArrangement = Arrangement.Center // Centre les éléments verticalement si nécessaire
        ) {
            Text("Accueil")
            Button(
                onClick = { onNavigate(Routes.CARISTE) }
            ) {
                Text("Gestion des Caristes")
            }
            Button(
                onClick = { onNavigate(Routes.COLIS) }
            ) {
                Text("Gestion des Colis")
            }
//            Button(
//                onClick = { onNavigate(Routes.EMPLACEMENT) }
//            ) {
//                Text("Gestion des Emplacement")
//            }
            Button(
                onClick = { onNavigate(Routes.ALLEE) }
            ) {
                Text("Gestion des Allées")
            }
            Button(
                onClick = { onNavigate(Routes.LOGIN) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red, //Couleur fond du bouton
                    contentColor = Color.White //Couleur de l'écriture
                )
            ) {
                Text("Deconnexion")
            }
        }
    }
}

