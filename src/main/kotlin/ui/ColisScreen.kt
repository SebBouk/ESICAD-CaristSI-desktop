package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import ktorm.Colis
import ktorm.entity.ColisEntity
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import routing.Routes

@Composable
@Preview
fun ColisScreen(database: Database, onNavigate: (Routes) -> Unit){
    var colis by remember { mutableStateOf(emptyList<ColisEntity>()) }

    LaunchedEffect(Unit){
        colis = requeteColis(database)
    }

    Column {
        Text("Gestion des Colis")
        ColisTable(colis = colis)
        Button(onClick = {onNavigate(Routes.HOME)}
        ){
            Text("Retour accueil")
        }
    }
}

@Composable
fun ColisTable(colis: List<ColisEntity>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("ID", modifier = Modifier.weight(1f), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
                Text("Poid", modifier = Modifier.weight(1f), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
            }

            Divider()

            LazyColumn {
                items(colis){ colis ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(colis.id.toString(), modifier = Modifier.weight(1f))
                        Text(colis.poids.toString(), modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

fun requeteColis(database: Database) : List<ColisEntity> {
    return try {
        database.sequenceOf(Colis).toList()
    }catch (e: Exception){
        println("Erreur lors de la récupération des données : ${e.message}")
        emptyList()
    }
}