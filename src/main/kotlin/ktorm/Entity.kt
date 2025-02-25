package ktorm.entity

import org.ktorm.entity.Entity
import java.time.LocalDate

interface CaristesEntity : Entity<CaristesEntity> {
    companion object : Entity.Factory<CaristesEntity>()

    var id: Int
    var firstName: String
    var lastName: String
    var mdp: String
    var dateNaissance: LocalDate
    var dateEmbauche: LocalDate
    var login: String
}

interface  ColisEntity : Entity<ColisEntity>{
    companion object : Entity.Factory<ColisEntity>()

    var id: Int
    var longueur: Int
    var largeur: Int
    var hauteur: Int
    var poids: Int
    var dateStock: LocalDate
}

