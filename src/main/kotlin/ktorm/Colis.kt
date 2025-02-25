package ktorm

import ktorm.entity.ColisEntity
import org.ktorm.schema.*

object Colis : Table<ColisEntity>("colis"){
    val id = int ("ID").primaryKey().bindTo {it.id}
    val longueur = int ("Longueur").bindTo { it.longueur }
    val largeur = int ("Largeur").bindTo { it.largeur }
    val hauteur = int ("Hauteur").bindTo { it.hauteur }
    val poids = int ("Poids").bindTo { it.poids }
    val dateStock = date ("DateStock").bindTo { it.dateStock }
}