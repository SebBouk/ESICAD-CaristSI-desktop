package ktorm

import ktorm.entity.*
import org.ktorm.schema.*

object Place : Table<PlaceEntity>("place"){
    val ID_Cariste = int ("ID_Cariste").primaryKey().references(Caristes) {it.cariste}
    val ID_Colis = int ("ID_Colis").primaryKey().references(Colis) { it.colis }
    val ID_Emplacement = int ("ID_Emplacement").primaryKey().references(Emplacement) { it.emplacement }
    val DateDepose = date ("DateDepose").bindTo { it.DateDepose }
}