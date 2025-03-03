package ktorm.entity

import org.ktorm.dsl.AssignmentsBuilder
import org.ktorm.entity.Entity
import java.time.LocalDate

interface CaristesEntity : Entity<CaristesEntity> {
    companion object : Entity.Factory<CaristesEntity>()

    var ID_Cariste: Int
    var firstName: String
    var lastName: String
    var mdp: String
    var dateNaissance: LocalDate
    var dateEmbauche: LocalDate
    var login: String
}

interface  ColisEntity : Entity<ColisEntity>{
    companion object : Entity.Factory<ColisEntity>()

    var ID_Colis: Int
    var longueur: Int
    var largeur: Int
    var hauteur: Int
    var poids: Int
    var dateStock: LocalDate
}

interface EmplacementEntity : Entity<EmplacementEntity> {
    companion object : Entity.Factory<EmplacementEntity>()

    var ID_Emplacement : Int
    var VolumeMax : Int
    var PoidsMax : Int
    var ID_Colonne : Int
    var colonne: ColonneEntity
}

interface ColonneEntity : Entity<ColonneEntity>{
    companion object : Entity.Factory<ColonneEntity>()

    var ID_Colonne : Int
    var NumeroCol : Int
    var ID_Allee : Int
    var allee: AlleeEntity
}

interface AlleeEntity : Entity<AlleeEntity>{
    companion object : Entity.Factory<AlleeEntity>()

    var ID_Allee : Int
    var Numero : Int
}

interface PlaceEntity : Entity<PlaceEntity>{
    companion object : Entity.Factory<PlaceEntity>()

    var ID_Cariste : Int
    var ID_Colis : Int
    var ID_Emplacement : Int
    var DateDepose : LocalDate
    var cariste: CaristesEntity
    var colis: ColisEntity
    var emplacement: EmplacementEntity
}
