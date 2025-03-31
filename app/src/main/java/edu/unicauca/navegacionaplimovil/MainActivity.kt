package edu.unicauca.navegacionaplimovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import edu.unicauca.navegacionaplimovil.ui.theme.NavegacionAplimovilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegacionAplimovilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navegacion()
                }
            }
        }
    }
}

@Composable
fun PantallaBase(
    titulo: String,
    colorFondo: Color,
    textoBoton: String? = null,
    onBotonClick: (() -> Unit)? = null,
    mostrarBotonAtras: Boolean = false,
    irAtras: (() -> Unit)? = null,
    contenidoExtra: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = titulo, style = MaterialTheme.typography.headlineMedium)
        contenidoExtra?.invoke()
        Spacer(modifier = Modifier.height(16.dp))

        onBotonClick?.let {
            Button(onClick = it) { Text(text = textoBoton ?: "Continuar") }
        }
        if (mostrarBotonAtras) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { irAtras?.invoke() }) { Text(text = "Atrás") }
        }
    }
}

@Composable
fun Navegacion() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Pantallas.Inicio.route) {
        composable(Pantallas.Inicio.route) {
            PantallaBase(
                titulo = "Bienvenidos",
                colorFondo = Color(0xFFFFCCBC),
                textoBoton = "Iniciar",
                onBotonClick = { navController.navigate(Pantallas.Segunda.route) }
            )
        }
        composable(Pantallas.Segunda.route) {
            PantallaBase(
                titulo = "Segunda Pantalla",
                colorFondo = Color(0xFFBBDEFB),
                textoBoton = "Siguiente",
                onBotonClick = { navController.navigate(Pantallas.Tercera.route) },
                mostrarBotonAtras = true,
                irAtras = { navController.popBackStack() }
            )
        }
        composable(Pantallas.Tercera.route) {
            PantallaBase(
                titulo = "Tercera Pantalla",
                colorFondo = Color(0xFFC8E6C9),
                textoBoton = "Siguiente",
                onBotonClick = { navController.navigate(Pantallas.Cuarta.route) },
                mostrarBotonAtras = true,
                irAtras = { navController.popBackStack() }
            )
        }
        composable(Pantallas.Cuarta.route) {
            PantallaBase(
                titulo = "Cuarta Pantalla",
                colorFondo = Color(0xFFFFF176),
                textoBoton = "Ir al Inicio",
                onBotonClick = { navController.navigate(Pantallas.Inicio.route) },
                mostrarBotonAtras = true,
                irAtras = { navController.popBackStack() },
                contenidoExtra = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "Imagen en la Cuarta Pantalla",


                    )
                }
            )
        }
    }
}

sealed class Pantallas(val route: String) {
    object Inicio : Pantallas("inicio")
    object Segunda : Pantallas("segunda")
    object Tercera : Pantallas("tercera")
    object Cuarta : Pantallas("cuarta")
}

@Preview(showBackground = true)
@Composable
fun PreviewInicial() {
    NavegacionAplimovilTheme {
        PantallaBase(titulo = "Bienvenidos", colorFondo = Color.White)
    }
}
