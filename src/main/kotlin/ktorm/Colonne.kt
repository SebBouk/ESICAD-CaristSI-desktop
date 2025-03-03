package ktorm

import ktorm.entity.ColonneEntity
import org.ktorm.schema.Table
import org.ktorm.schema.int

object Colonne : Table<ColonneEntity>("colonne"){
    val ID_Colonne = int("ID_Colonne").primaryKey().bindTo { it.ID_Colonne }
    val NumeroCol = int("NumeroCol").bindTo { it.NumeroCol }
    val ID_Allee = int("ID_Allee").references(Allees) { it.allee}
}