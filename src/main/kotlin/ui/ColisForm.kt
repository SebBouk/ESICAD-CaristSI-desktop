package ui

import ktorm.Colis
import ktorm.entity.ColisEntity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.ktorm.database.Database
import org.ktorm.dsl.insert
import java.time.LocalDate


@Composable
fun ColisForm(database: Database, showDialog: Boolean, onDismiss: () -> Unit) {
    var longueur by remember { mutableStateOf("") }
    var largeur by remember { mutableStateOf("") }
    var hauteur by remember { mutableStateOf("") }
    var poids by remember { mutableStateOf("") }
    var dateStock by remember { mutableStateOf("") }
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = largeur,
                        onValueChange = { largeur = it },
                        label = { Text("Largeur") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = hauteur,
                        onValueChange = { hauteur = it },
                        label = { Text("Hauteur") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = poids,
                        onValueChange = { poids = it },
                        label = { Text("Poids") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            errorMessage = ""
                            try {
                                val newColis = ColisEntity {
                                    this.longueur = longueur.toInt()
                                    this.largeur = largeur.toInt()
                                    this.hauteur = hauteur.toInt()
                                    this.poids = poids.toInt()
                                    this.dateStock = LocalDate.parse(dateStock)
                                }
                                insertColis(database, newColis)
                                onDismiss()
                            } catch (e: NumberFormatException) {
                                errorMessage = "Veuillez entrer des valeurs numériques valides."
                            } catch (e: Exception) {
                                errorMessage = "Erreur : ${e.message}"
                            }
                        }) {
                            Text("Ajouter Colis")
                        }
                    }
                }
            }
        }
    }
}

fun insertColis(database: Database, colis: ColisEntity) {
    database.insert(Colis) {
        set(it.longueur, colis.longueur)
        set(it.largeur, colis.largeur)
        set(it.hauteur, colis.hauteur)
        set(it.poids, colis.poids)
        set(it.dateStock, colis.dateStock)
    }
}

