import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.ktorm.database.Database
import org.ktorm.database.asIterable
import androidx.compose.material.*
import ktorm.Caristes
import org.ktorm.dsl.*
import routing.Router
import routing.Routes
import ui.CaristeScreen
import ui.ColisScreen
import ui.HomeScreen
import ui.LoginScreen



@Composable
@Preview
fun App(database: Database) {
    val router = remember { Router() }

    MaterialTheme {
        Surface {
            when (router.currentRoute) {
                Routes.LOGIN -> LoginScreen({ email, password ->
                    println("Tentative de connexion avec $email et un mot de passe ${password}")
                    val query = database.from(Caristes).select().where {
                        (Caristes.Login eq email) and (Caristes.MDP eq password)
                    }
                    if (query.iterator().hasNext()) {
                        router.navigateTo(route = Routes.HOME)
                    }
                })

                Routes.HOME -> HomeScreen { route ->
                    router.navigateTo(route)
                }

                Routes.CARISTE -> CaristeScreen(database = database) {
                    route -> router.navigateTo(route)
                }

                Routes.COLIS -> ColisScreen(database = database) {
                        route -> router.navigateTo(route)
                }
            }


        }
    }
}

    fun main() = application {

        val database = Database.connect(
            url = "jdbc:mysql://localhost:3306/carist-si",
            driver = "com.mysql.jdbc.Driver",
            user = "root",
            password = null
        )

        database.useConnection { connection ->
            val sql = "SELECT 1"
            connection.prepareStatement(sql).use { statement ->
                statement.executeQuery().asIterable().map {
                    println("it worked : " + it.getString(1))
                }
            }
        }



        Window(onCloseRequest = ::exitApplication, title = "Application") {
            App(database)
        }
    }
