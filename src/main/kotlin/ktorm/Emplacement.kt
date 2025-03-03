package ktorm

import ktorm.entity.EmplacementEntity
import org.ktorm.schema.Table
import org.ktorm.schema.int

object Emplacement : Table<EmplacementEntity>("emplacement") {
    val ID_Emplacement = int("Id_Emplacement").primaryKey().bindTo { it.ID_Emplacement }
    val VolumeMax = int("VolumeMax").bindTo { it.VolumeMax }
    val PoidsMax = int("PoidsMax").bindTo { it.PoidsMax }
    val ID_Colonne = int("ID_Colonne").references(Colonne) { it.colonne }
}
