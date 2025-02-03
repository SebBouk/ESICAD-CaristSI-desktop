import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.ktorm.database.Database
import org.ktorm.database.asIterable

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
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
            statement.executeQuery().asIterable().map { println("it worked : " + it.getString(1))
            }
        }
    }

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
