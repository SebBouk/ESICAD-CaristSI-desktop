package ui

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
import ktorm.Caristes
import ktorm.entity.CaristesEntity
import org.ktorm.database.Database
import org.ktorm.dsl.insert

import java.time.LocalDate


@Composable
fun CaristeForm(database: Database, showDialog: Boolean, onDismiss: () -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var mdp by remember { mutableStateOf("") }
    var dateNaissance by remember { mutableStateOf("") }
    var dateEmbauche by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }

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
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("Prénom") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Nom") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = mdp,
                        onValueChange = { mdp = it },
                        label = { Text("Mot de passe") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = dateNaissance,
                        onValueChange = { dateNaissance = it },
                        label = { Text("Date de naissance (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = dateEmbauche,
                        onValueChange = { dateEmbauche = it },
                        label = { Text("Date d'embauche (YYYY-MM-DD)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        singleLine = true,
                        value = login,
                        onValueChange = { login = it },
                        label = { Text("Login") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = onDismiss) {
                            Text("Annuler")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            val newCariste = CaristesEntity {
                                this.firstName = firstName
                                this.lastName = lastName
                                this.mdp = mdp
                                this.dateNaissance = LocalDate.parse(dateNaissance)
                                this.dateEmbauche = LocalDate.parse(dateEmbauche)
                                this.login = login
                            }
                            insertCariste(database, newCariste)
                            onDismiss()
                        }) {
                            Text("Ajouter Cariste")
                        }
                    }
                }
            }
        }
    }
}

fun insertCariste(database: Database, cariste: CaristesEntity) {
    database.insert(Caristes) {
        set(it.firstName, cariste.firstName)
        set(it.lastName, cariste.lastName)
        set(it.MDP, cariste.mdp)
        set(it.DateNaissance, cariste.dateNaissance)
        set(it.DateEmbauche, cariste.dateEmbauche)
        set(it.Login, cariste.login)
    }
}

