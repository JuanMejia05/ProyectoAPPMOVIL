package edu.unicauca.navegacionaplimovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    contenidoExtra: @Composable (() -> Unit)? = null,
    isFirstScreen: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (isFirstScreen) Arrangement.Center else Arrangement.SpaceBetween
    ) {
        if (!isFirstScreen) {
            Text(
                text = "TECHOAPP",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }
        if (!isFirstScreen) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
                contenidoExtra?.invoke()
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                contenidoExtra?.invoke()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Tu tienda electrónica de confianza",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            onBotonClick?.let {
                Button(onClick = it, modifier = Modifier.fillMaxWidth()) {
                    Text(text = textoBoton ?: "Continuar", color = Color.White)
                }
            }
            if (mostrarBotonAtras) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { irAtras?.invoke() }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Atrás", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun Navegacion() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Pantallas.Inicio.route) {
        composable(Pantallas.Inicio.route) {
            PantallaBase(
                titulo = "", // Sin titulo
                colorFondo = Color(0xFFBBDEFB),
                textoBoton = "Iniciar",
                onBotonClick = { navController.navigate(Pantallas.Segunda.route) },
                contenidoExtra = {
                    Image(
                        painter = painterResource(id = R.drawable.logoapp),
                        contentDescription = "Imagen en la Pantalla de Inicio",
                    )
                },
                isFirstScreen = true
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
                colorFondo = Color(0xFFBBDEFB),
                textoBoton = "Ir al Inicio",
                onBotonClick = { navController.navigate(Pantallas.Inicio.route) },
                mostrarBotonAtras = true,
                irAtras = { navController.popBackStack() }
            )
        }
    }
}

sealed class Pantallas(val route: String) {
    object Inicio : Pantallas("inicio")
    object Segunda : Pantallas("segunda")
    object Tercera : Pantallas("tercera")
}

@Preview(showBackground = true)
@Composable
fun PreviewInicial() {
    NavegacionAplimovilTheme {
        PantallaBase(titulo = "Bienvenidos", colorFondo = Color.White)
    }
}
