package ktorm

import ktorm.entity.ColisEntity
import org.ktorm.schema.*

object Colis : Table<ColisEntity>("colis"){
    val ID_Colis = int ("ID_Colis").primaryKey().bindTo {it.ID_Colis}
    val longueur = int ("Longueur").bindTo { it.longueur }
    val largeur = int ("Largeur").bindTo { it.largeur }
    val hauteur = int ("Hauteur").bindTo { it.hauteur }
    val poids = int ("Poids").bindTo { it.poids }
    val dateStock = date ("DateStock").bindTo { it.dateStock }
}