package ktorm

import ktorm.entity.AlleeEntity
import org.ktorm.schema.Table
import org.ktorm.schema.int

object Allees : Table<AlleeEntity>("allee"){
    val ID_Allee = int ("ID_Allee").primaryKey().bindTo { it.ID_Allee }
    val Numero = int ("Numero").bindTo { it.Numero }
}