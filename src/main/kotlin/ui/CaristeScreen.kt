package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ktorm.Caristes
import ktorm.entity.CaristesEntity
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import routing.Routes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import org.ktorm.dsl.update
import java.time.LocalDate


@Composable
@Preview
fun CaristeScreen(database: Database, onNavigate:(Routes) -> Unit) {
    var caristesList by remember { mutableStateOf(emptyList<CaristesEntity>()) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedCariste by remember { mutableStateOf<CaristesEntity?>(null) }

    LaunchedEffect(Unit) {
        caristesList = requete(database)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 60.dp)) {
            Text("Gestion des Caristes")
            UserTable(
                caristes = caristesList,
                onEdit = { singleCariste ->
                    selectedCariste = singleCariste
                    showDialog = true
                },
                onDelete = { singleCariste ->
                    deleteCariste(database, singleCariste)
                    caristesList = requete(database)
                })
            Button(onClick = {
                selectedCariste = null
                showDialog = true
            })
            {
                Text("Ajouter un Cariste")
            }
            if (showDialog) {
                CaristeFormUpdate(
                    database = database,
                    showDialog = showDialog,
                    caristes = selectedCariste,
                    onDismiss = {
                        showDialog = false
                        caristesList = requete(database)
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Button(onClick = { onNavigate(Routes.HOME) }
            ) {
                Text("Retour accueil")
            }
        }
    }
}

@Composable
fun UserTable(caristes: List<CaristesEntity>, onEdit: (CaristesEntity) -> Unit, onDelete: (CaristesEntity) -> Unit) {
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
                        Text(cariste.ID_Cariste.toString(), modifier = Modifier.weight(1f))
                        Text(cariste.firstName.toString(), modifier = Modifier.weight(1f))
                        Text(cariste.lastName.toString(), modifier = Modifier.weight(1f))
                        Text(cariste.dateNaissance.toString(), modifier = Modifier.weight(1f))
                        Text(cariste.dateEmbauche.toString(), modifier = Modifier.weight(1f))
                        Text(cariste.login.toString(), modifier = Modifier.weight(1f))
                        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                            Button(onClick = { onEdit(cariste) }) {
                                Text("Éditer")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { onDelete(cariste) }) {
                                Text("Supprimer")
                            }
                        }
                    }
                    Divider()
                }
            }
        }
    }
}

fun deleteCariste(database: Database, caristes: CaristesEntity){
    database.delete(Caristes){
        it.ID_Cariste eq caristes.ID_Cariste
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

@Composable
fun CaristeFormUpdate(
    database: Database,
    showDialog: Boolean,
    caristes: CaristesEntity?,
    onDismiss: () -> Unit
) {
    var prenom by remember { mutableStateOf(caristes?.firstName ?: "") }
    var nom by remember { mutableStateOf(caristes?.lastName ?: "") }
    var naissance by remember { mutableStateOf(caristes?.dateNaissance?.toString() ?: "") }
    var mdp by remember { mutableStateOf(caristes?.mdp ?: "") }
    var embauche by remember { mutableStateOf(caristes?.dateEmbauche?.toString() ?: "") }
    var login by remember { mutableStateOf(caristes?.login ?: "") }
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
                        value = prenom,
                        onValueChange = { prenom = it },
                        label = { Text("Prénom") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = nom,
                        onValueChange = { nom = it },
                        label = { Text("Nom") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = naissance,
                        onValueChange = { naissance = it },
                        label = { Text("Date de Naissance (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = mdp,
                        onValueChange = { mdp = it },
                        label = { Text("MDP") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = embauche,
                        onValueChange = { embauche = it },
                        label = { Text("Date d'Embauche (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = login,
                        onValueChange = { login = it },
                        label = { Text("Login") },
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
                                val updatedCariste = CaristesEntity {
                                    this.firstName = prenom
                                    this.lastName = nom
                                    this.dateNaissance = LocalDate.parse(naissance)
                                    this.mdp = mdp
                                    this.dateEmbauche = LocalDate.parse(embauche)
                                    this.login = login
                                }
                                if (caristes == null) {
                                    insertCariste(database, updatedCariste)
                                } else {
                                    updateCariste(database, caristes.ID_Cariste, updatedCariste)
                                }
                                onDismiss()
                            } catch (e: NumberFormatException) {
                                errorMessage = "Veuillez entrer des valeurs numériques valides."
                            } catch (e: Exception) {
                                errorMessage = "Erreur : ${e.message}"
                            }
                        }) {
                            Text(if (caristes == null) "Ajouter Cariste" else "Mettre à jour Cariste")
                        }
                    }
                }
            }
        }
    }
}

fun updateCariste(database: Database, id: Int, caristes: CaristesEntity) {
    database.update(Caristes) {
        set(it.firstName, caristes.firstName)
        set(it.lastName, caristes.lastName)
        set(it.DateNaissance, caristes.dateNaissance)
        set(it.MDP, caristes.mdp)
        set(it.DateEmbauche, caristes.dateEmbauche)
        set(it.Login, caristes.login)
        where { it.ID_Cariste eq id }
    }
}