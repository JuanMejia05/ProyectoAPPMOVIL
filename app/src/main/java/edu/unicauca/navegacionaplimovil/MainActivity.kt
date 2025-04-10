package edu.unicauca.navegacionaplimovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight // Import necesario para Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp             // Import necesario para sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.unicauca.navegacionaplimovil.ui.theme.NavegacionAplimovilTheme

sealed class Pantallas(val route: String) {
    object Inicio : Pantallas("inicio")
    object Segunda : Pantallas("segunda")
}

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
fun Navegacion() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Pantallas.Inicio.route) {
        composable(Pantallas.Inicio.route) {
            PantallaBase(
                titulo = "",
                colorFondo = Color(0xFFBBDEFB),
                textoBoton = "Iniciar",
                onBotonClick = { navController.navigate(Pantallas.Segunda.route) },
                contenidoExtra = {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.logoapp),
                            contentDescription = "Imagen en la Pantalla de Inicio",
                            modifier = Modifier.size(250.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "TU TIENDA ELECTRÓNICA DE CONFIANZA",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                shadow = Shadow(
                                    color = Color.Gray,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 3f
                                ),
                                fontSize = 24.sp
                            ),
                            color = Color(0xFFFFA500),
                            modifier = Modifier.padding(horizontal = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                },
                isFirstScreen = true
            )
        }
        composable(Pantallas.Segunda.route) {
            PantallaBase(
                titulo = "",
                colorFondo = Color(0xFFBBDEFB),
                mostrarBotonAtras = true,
                irAtras = { navController.popBackStack() },
                contenidoSuperior = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Novedades",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                shadow = Shadow(
                                    color = Color.Gray,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 3f
                                ),
                                fontSize = 30.sp
                            ),
                            color = Color(0xFFFFA500),
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.iniciars_removebg_preview),
                                contentDescription = "Imagen Izquierda",
                                modifier = Modifier
                                    .size(width = 48.dp, height = 36.dp)
                                    .padding(end = 4.dp),
                                contentScale = ContentScale.Fit
                            )
                            var texto by rememberSaveable { mutableStateOf("") }
                            OutlinedTextField(
                                value = texto,
                                onValueChange = { texto = it },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                                label = { Text("Buscar") },
                                modifier = Modifier.offset(y = 0.dp),
                            )
                            Image(
                                painter = painterResource(id = R.drawable.menu),
                                contentDescription = "Imagen Derecha",
                                modifier = Modifier
                                    .height(50.dp)
                                    .size(width = 48.dp, height = 36.dp)
                                    .padding(start = 4.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(250.dp))
                },

                imagenesInferiores = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = -100.dp)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // --- Texto añadido ---
                        Text(
                            text = "NUEVOS MATERIALES",
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.herramientas2_removebg_preview),
                            contentDescription = "Imagen Herramientas",
                            modifier = Modifier
                                .height(180.dp)
                                .border(
                                    width = 4.dp,
                                    color = Color(0xFFFFA500),
                                    shape = RectangleShape
                                ),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            )
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
    isFirstScreen: Boolean = false,
    imagenesInferiores: @Composable (() -> Unit)? = null,
    contenidoSuperior: @Composable (() -> Unit)? = null
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
                text = "TECHNOAPP",
                style = MaterialTheme.typography.headlineMedium.copy(
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(2f, 2f),
                        blurRadius = 3f
                    ),
                    fontSize = 36.sp
                ),
                color = Color(0xFFFFA500),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                textAlign = TextAlign.Center
            )
            contenidoSuperior?.invoke()
            Spacer(modifier = Modifier.height(8.dp))
            contenidoExtra?.invoke()
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                contenidoExtra?.invoke() // Llama al lambda con Logo y Texto
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            imagenesInferiores?.invoke() // Llama al lambda con la Column (Text + Image) o nada

            // Botones
            onBotonClick?.let {
                Button(onClick = it, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                    Text(text = textoBoton ?: "Continuar", color = Color.White, fontSize = 18.sp)
                }
            }
            if (mostrarBotonAtras) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { irAtras?.invoke() }, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                    Text(text = "Atrás", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InicioPreview() {
    NavegacionAplimovilTheme {
        PantallaBase(
            titulo = "",
            colorFondo = Color(0xFFBBDEFB),
            textoBoton = "Iniciar",
            onBotonClick = { },
            contenidoExtra = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.logoapp),
                        contentDescription = "Imagen en la Pantalla de Inicio",
                        modifier = Modifier.size(250.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "TU TIENDA ELECTRÓNICA DE CONFIANZA",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            shadow = Shadow(
                                color = Color.Gray,
                                offset = Offset(2f, 2f),
                                blurRadius = 3f
                            ),
                            fontSize = 24.sp
                        ),
                        color = Color(0xFFFFA500),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            },
            isFirstScreen = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SegundaPantallaPreview() {
    NavegacionAplimovilTheme {
        PantallaBase(
            titulo = "",
            colorFondo = Color(0xFFBBDEFB),
            textoBoton = "Continuar (Preview)",
            onBotonClick = { },
            mostrarBotonAtras = true,
            irAtras = { },
            contenidoSuperior = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Novedades",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            shadow = Shadow(
                                color = Color.Gray,
                                offset = Offset(2f, 2f),
                                blurRadius = 3f
                            ),
                            fontSize = 30.sp
                        ),
                        color = Color(0xFFFFA500),
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.iniciars_removebg_preview),
                            contentDescription = "Imagen Izquierda",
                            modifier = Modifier
                                .size(width = 48.dp, height = 36.dp)
                                .padding(end = 4.dp),
                            contentScale = ContentScale.Fit
                        )
                        var texto by rememberSaveable { mutableStateOf("") }
                        OutlinedTextField(
                            value = texto,
                            onValueChange = { texto = it },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            label = { Text("Buscar") },
                            modifier = Modifier.offset(y = 0.dp),
                        )
                        Image(
                            painter = painterResource(id = R.drawable.menu),
                            contentDescription = "Imagen Derecha",
                            modifier = Modifier
                                .height(50.dp)
                                .size(width = 48.dp, height = 36.dp)
                                .padding(start = 4.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
                Spacer(modifier = Modifier.height(250.dp))
            },
            imagenesInferiores = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -100.dp)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "NUEVOS MATERIALES",
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.herramientas2_removebg_preview),
                        contentDescription = "Imagen Herramientas con borde (Preview)",
                        modifier = Modifier
                            .height(180.dp)
                            .border(
                                width = 4.dp,
                                color = Color(0xFFFFA500),
                                shape = RectangleShape
                            ),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        )
    }
}