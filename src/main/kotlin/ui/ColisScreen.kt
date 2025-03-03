package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ktorm.Colis
import ktorm.entity.ColisEntity
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.update
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import routing.Routes
import java.time.LocalDate

@Composable
@Preview
fun ColisScreen(database: Database, onNavigate: (Routes) -> Unit) {
    var colisList by remember { mutableStateOf(emptyList<ColisEntity>()) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedColis by remember { mutableStateOf<ColisEntity?>(null) }

    LaunchedEffect(Unit) {
        colisList = requeteColis(database)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 60.dp)) {
            Text("Gestion des Colis")
            ColisTable(
                colis = colisList,
                onEdit = { singleColis ->
                    selectedColis = singleColis
                    showDialog = true
                },
                onDelete = { singleColis ->
                    deleteColis(database, singleColis)
                    colisList = requeteColis(database)
                })

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()){
            Button(onClick = {
            selectedColis = null
            showDialog = true
        }) {
            Text("Ajouter un Colis")
        }
            if (showDialog) {
                ColisFormUpdate(
                    database = database,
                    showDialog = showDialog,
                    colis = selectedColis,
                    onDismiss = {
                        showDialog = false
                        colisList = requeteColis(database)
                    }
                )
            }

            Button(onClick = { onNavigate(Routes.HOME) }
            ) {
                Text("Retour accueil")
            }
        }
    }
}
}

@Composable
fun ColisTable(colis: List<ColisEntity>, onEdit: (ColisEntity) -> Unit, onDelete: (ColisEntity) -> Unit) {
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
                Text(
                    "ID",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Text(
                    "Longueur",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Text(
                    "Largeur",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Text(
                    "Hauteur",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Text(
                    "Poid",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Text(
                    "Date de Stockage",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Text(
                    "Action",
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
            }

            Divider()

            LazyColumn {
                items(colis) { colis ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(colis.ID_Colis.toString(), modifier = Modifier.weight(1f))
                        Text(colis.longueur.toString(), modifier = Modifier.weight(1f))
                        Text(colis.largeur.toString(), modifier = Modifier.weight(1f))
                        Text(colis.hauteur.toString(), modifier = Modifier.weight(1f))
                        Text(colis.poids.toString(), modifier = Modifier.weight(1f))
                        Text(colis.dateStock.toString(), modifier = Modifier.weight(1f))
                        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                            Button(onClick = { onEdit(colis) }) {
                                Text("Éditer")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { onDelete(colis) }) {
                                Text("Supprimer")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun deleteColis(database: Database, colis: ColisEntity) {
    database.delete(Colis) {
        it.ID_Colis eq colis.ID_Colis
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

@Composable
fun ColisFormUpdate(
        database: Database,
        showDialog: Boolean,
        colis: ColisEntity?,
        onDismiss: () -> Unit
    ) {
        var longueur by remember { mutableStateOf(colis?.longueur?.toString() ?: "") }
        var largeur by remember { mutableStateOf(colis?.largeur?.toString() ?: "") }
        var hauteur by remember { mutableStateOf(colis?.hauteur?.toString() ?: "") }
        var poids by remember { mutableStateOf(colis?.poids?.toString() ?: "") }
        var dateStock by remember { mutableStateOf(colis?.dateStock?.toString() ?: "") }
        var errorMessage by remember { mutableStateOf("") }

        if (showDialog) {
            Dialog(onDismissRequest = onDismiss) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    elevation = 8.dp,
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            singleLine = true,
                            value = longueur,
                            onValueChange = { longueur = it },
                            label = { Text("Longueur") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            singleLine = true,
                            value = largeur,
                            onValueChange = { largeur = it },
                            label = { Text("Largeur") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            singleLine = true,
                            value = hauteur,
                            onValueChange = { hauteur = it },
                            label = { Text("Hauteur") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            singleLine = true,
                            value = poids,
                            onValueChange = { poids = it },
                            label = { Text("Poids") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            singleLine = true,
                            value = dateStock,
                            onValueChange = { dateStock = it },
                            label = { Text("Date Stock (YYYY-MM-DD)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (errorMessage.isNotEmpty()) {
                            Text(text = errorMessage, color = Color.Red)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(onClick = onDismiss) {
                                Text("Annuler")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = {
                                errorMessage = ""
                                try {
                                    val updatedColis = ColisEntity {
                                        this.longueur = longueur.toInt()
                                        this.largeur = largeur.toInt()
                                        this.hauteur = hauteur.toInt()
                                        this.poids = poids.toInt()
                                        this.dateStock = LocalDate.parse(dateStock)
                                    }
                                    if (colis == null) {
                                        insertColis(database, updatedColis)
                                    } else {
                                        updateColis(database, colis.ID_Colis, updatedColis)
                                    }
                                    onDismiss()
                                } catch (e: NumberFormatException) {
                                    errorMessage = "Veuillez entrer des valeurs numériques valides."
                                } catch (e: Exception) {
                                    errorMessage = "Erreur : ${e.message}"
                                }
                            }) {
                                Text(if (colis == null) "Ajouter Colis" else "Mettre à jour Colis")
                            }
                        }
                    }
                }
            }
        }
    }

fun updateColis(database: Database, id: Int, colis: ColisEntity) {
    database.update(Colis) {
        set(it.longueur, colis.longueur)
        set(it.largeur, colis.largeur)
        set(it.hauteur, colis.hauteur)
        set(it.poids, colis.poids)
        set(it.dateStock, colis.dateStock)
        where { it.ID_Colis eq id }
    }
}