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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ktorm.Emplacement
import ktorm.entity.EmplacementEntity
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import routing.Routes

@Composable
@Preview
fun EmplacementScreen(database: Database, onNavigate: (Routes) -> Unit){
    var emplacement by remember { mutableStateOf(emptyList<EmplacementEntity>()) }

    LaunchedEffect(Unit){
        emplacement = requeteEmplacement(database)
    }
Box(modifier = Modifier.fillMaxSize()){
    Column (modifier = Modifier.fillMaxWidth().padding(bottom = 60.dp)) {
        Text("Gestion des Emplacement")
        EmplacementTable(emplacement = emplacement)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(16.dp)
    ){
        Button(onClick = {onNavigate(Routes.HOME)}
        ){
            Text("Retour accueil")
        }
    }
    }
}

@Composable
fun EmplacementTable(emplacement: List<EmplacementEntity>){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp
    ){
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Id Stocks",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Text(
                    "Volume Max",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Text(
                    "Poids Maximum",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Text(
                    "Id Colonne",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
            }
            Divider()

            LazyColumn {
                items(emplacement) { emplacement ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(emplacement.ID_Emplacement.toString(), modifier = Modifier.weight(1f))
                        Text(emplacement.VolumeMax.toString(), modifier = Modifier.weight(1f))
                        Text(emplacement.PoidsMax.toString(), modifier = Modifier.weight(1f))
                        Text(emplacement.colonne.toString(), modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

fun requeteEmplacement(database: Database) : List<EmplacementEntity> {
    return try {
        database.sequenceOf(Emplacement).toList()
    }catch (e: Exception){
        println("Erreur lors de la récupération des données : ${e.message}")
        emptyList()
    }
}