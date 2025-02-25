package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ktorm.Caristes
import ktorm.entity.CaristesEntity
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import routing.Routes

@Composable
@Preview
fun CaristeScreen(database: Database, onNavigate:(Routes) -> Unit) {
    var caristes by remember { mutableStateOf(emptyList<CaristesEntity>()) }

    LaunchedEffect(Unit) {
        caristes = requete(database)
    }

    Column {
        Text("Gestion des Caristes")
        UserTable(caristes = caristes)
        Button(onClick = {onNavigate(Routes.HOME)}
        ){
            Text("Retour accueil")
        }
    }
}

@Composable
fun UserTable(caristes: List<CaristesEntity>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp
    ) {
        Column {
            // En-tête du tableau
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("ID", modifier = Modifier.weight(1f), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
                Text("Prénom", modifier = Modifier.weight(1f), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
                Text("Nom", modifier = Modifier.weight(1f), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
                Text("Date de naissance", modifier = Modifier.weight(1f), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
                Text("Date d'embauche", modifier = Modifier.weight(1f), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
                Text("Login", modifier = Modifier.weight(1f), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
            }

            Divider()

            // Corps du tableau
            LazyColumn {
                items(caristes) { cariste ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(cariste.id.toString(), modifier = Modifier.weight(1f))
                        Text(cariste.firstName.toString(), modifier = Modifier.weight(1f))
                        Text(cariste.lastName.toString(), modifier = Modifier.weight(1f))
                        Text(cariste.dateNaissance.toString(), modifier = Modifier.weight(1f))
                        Text(cariste.dateEmbauche.toString(), modifier = Modifier.weight(1f))
                        Text(cariste.login.toString(), modifier = Modifier.weight(1f))
                    }
                    Divider()
                }
            }
        }
    }
}



fun requete(database: Database) : List<CaristesEntity> {
    return try {
        database.sequenceOf(Caristes).toList()
    } catch (e: Exception) {
        println("Erreur lors de la récupération des données : ${e.message}")
        emptyList()
    }
}