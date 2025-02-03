package ktorm

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar


object Users : Table<Nothing>("user") {
    val id = int("id").primaryKey()
    val firstName = varchar("first_name")
    val lastName = varchar("last_name")
}