package ktorm

import ktorm.entity.CaristesEntity
import org.ktorm.schema.*

object Caristes : Table<CaristesEntity>("caristes") {
    val ID_Cariste = int("ID_Cariste").primaryKey().bindTo { it.ID_Cariste }
    val firstName = varchar("Prenom").bindTo { it.firstName }
    val lastName = varchar("Nom").bindTo { it.lastName }
    val MDP = varchar("MDP").bindTo { it.mdp }
    val DateNaissance = date("Naissance").bindTo { it.dateNaissance }
    val DateEmbauche = date("Embauche").bindTo { it.dateEmbauche }
    val Login = varchar("Login").bindTo { it.login }
}
