package edu.unicauca.navegacionaplimovil


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import edu.unicauca.navegacionaplimovil.ui.theme.NavegacionAplimovilTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle


@Composable
fun PantallaBase(
    titulo: String,
    colorFondo: Color,
    textoBoton: String? = null,
    onBotonClick: (() -> Unit)? = null,
    mostrarBotonAtras: Boolean = false,
    irAtras: (() -> Unit)? = null,
    contenidoExtra: @Composable (() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    barraInferior: @Composable (() -> Unit)? = null
) {
    Scaffold(
        containerColor = colorFondo,
        bottomBar = {
            barraInferior?.invoke()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = verticalArrangement
        ) {
            if (titulo.isNotEmpty()) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontStyle = FontStyle.Italic,
                        fontFamily = FontFamily.Monospace // fuente parecida a Comic Sans
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

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
}





@Composable

fun Navegacion2() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Pantallas.Inicio.route) {
        composable(Pantallas.Inicio.route) {
            PantallaBase(
                titulo = "TECHNO APP",
                colorFondo = Color(0xFFBBDEFB),
                verticalArrangement = Arrangement.Center,
                contenidoExtra = {
                    Image(
                        painter = painterResource(id = R.drawable.logoaccountwhite),
                        contentDescription = "Imagen en primera pantalla",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { navController.navigate(Pantallas.Segunda.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.huella),
                            contentDescription = "Ícono huella",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ingresar con huella")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Texto interactivo
                    Text(
                        text = "Ingresa con correo y contraseña",
                        color = Color.Blue,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable {
                            // Aquí puedes redirigir a otra pantalla cuando la tengas lista
                            // navController.navigate(Pantallas.Login.route)
                        }
                    )
                }
            )
        }

        composable(Pantallas.Segunda.route) {
            PantallaBase(
                titulo = "TECHNO APP",
                colorFondo = Color(0xFFBBDEFB),
                verticalArrangement = Arrangement.Top,
                contenidoExtra = {
                   // BarraDeBusqueda()
                    //Aquí va tu contenido (Column con textos, LazyColumn, LazyHorizontalGrid, etc.)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.Start
                    ) {

                        //CUADRICULA DE OFERTA VERTICAL
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Ofertas Destacadas",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        val elementos = listOf(
                            "Elemento 1", "Elemento 2", "Elemento 3",
                            "Elemento 4", "Elemento 5", "Elemento 6",
                            "Elemento 7", "Elemento 8"
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .heightIn(max = 250.dp), // limita altura si hay muchos
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(elementos.size) { index ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .height(50.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text(
                                            text = elementos[index],
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                            }
                        }

                        //CUADRICULA DE OFERTA VERTICAL
                        Spacer(modifier = Modifier.height(16.dp))

                        // Título para las ofertas destacadas
                        Text(
                            text = "Ofertas Destacadas",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Grid horizontal con imagen y descuento
                        val ofertas = listOf(
                            "10% Descuento", "15% Descuento", "20% Descuento",
                            "25% Descuento", "30% Descuento", "35% Descuento",
                            "25% Descuento", "30% Descuento", "35% Descuento"
                        )

                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(3),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ) {
                            items(ofertas.size) { index ->
                                Card(
                                    modifier = Modifier.width(140.dp),
                                    shape = MaterialTheme.shapes.medium,
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp) // ajusta el nivel de sombra
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                    ) {
                                        // Aquí va el contenido: imagen, texto, etc.
                                        Image(
                                            painter = painterResource(id = R.drawable.floor),
                                            contentDescription = "Oferta ${index + 1}",
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(MaterialTheme.shapes.medium)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = ofertas[index],
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }

                            }
                        }

                    }
                },
                barraInferior = {
                    Column {
                        RanuraImagenInferior()
                        BarraNavegacionInferiorConBackForward(navController)
                    }
                }
            )
        }
        composable(Pantallas.Tercera.route) {
            PantallaBase(
                titulo = "TECHNO APP",
                colorFondo = Color(0xFFBBDEFB),

                verticalArrangement = Arrangement.Top, // ← esto soluciona el centrado
                contenidoExtra = {
                    BarraDeBusqueda()

                    val ofertas = listOf(
                        "10% Descuento", "15% Descuento", "20% Descuento","20% Descuento",
                        "10% Descuento", "15% Descuento", "20% Descuento, 20% Descuento",
                        "10% Descuento", "15% Descuento", "20% Descuento","20% Descuento",
                        "10% Descuento", "15% Descuento", "20% Descuento","20% Descuento","20% Descuento"


                    )
                    // Simular una cuadrícula de 2 filas y 2 columnas (estático)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                    ) {
                        for (fila in 0 until 4) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                for (columna in 0 until 4) {
                                    val index = fila * 4+ columna
                                    if (index < ofertas.size) {
                                        Card(
                                            modifier = Modifier.size(width = 100.dp, height = 100.dp),
                                            shape = MaterialTheme.shapes.medium,
                                            colors = CardDefaults.cardColors(containerColor = Color.White),
                                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp) // sombra visible
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(6.dp)
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.floor),
                                                    contentDescription = "Oferta ${index + 1}",
                                                    modifier = Modifier
                                                        .size(50.dp)
                                                        .clip(MaterialTheme.shapes.medium)
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = ofertas[index],
                                                    style = MaterialTheme.typography.bodySmall,
                                                    maxLines = 2
                                                )
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }

                },

                barraInferior = {
                    Column {
                        RanuraImagenInferior()
                        BarraNavegacionInferiorConBackForward(navController)
                    }
                }
            )
        }
        composable(Pantallas.Cuarta.route) {
            PantallaBase(
                titulo = "Perfil del Usuario",
                colorFondo = Color(0xFFE3F2FD), // azul muy claro

                contenidoExtra = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo_foreground), // tu imagen en drawable
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(120.dp)

                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Nombre: Jorge Erazo", style = MaterialTheme.typography.bodyLarge)
                        Text("Correo: jorge@example.com", style = MaterialTheme.typography.bodyMedium)
                        Text("Teléfono: +57 3001234567", style = MaterialTheme.typography.bodyMedium)
                    }
                },
                barraInferior = {
                    BarraNavegacionInferiorConBackForward(navController)
                }
            )
        }


    }
}

sealed class Pantallas(val route: String, val titulo: String, val icono: ImageVector) {
    object Inicio : Pantallas("inicio", "Salir", Icons.Filled.AccountBox)
    object Segunda : Pantallas("segunda", "Ofertas", Icons.Filled.CheckCircle)
    object Tercera : Pantallas("tercera", "Menú", Icons.Filled.Menu)
    object Cuarta : Pantallas("cuarta", "Perfil", Icons.Filled.Person)
}

@Preview(showBackground = true)
@Composable
fun PreviewInicial() {
    NavegacionAplimovilTheme {
        PantallaBase(titulo = "Bienvenidos", colorFondo = Color.White)
    }
}
@Composable
fun BarraDeBusqueda(
    modifier: Modifier = Modifier
){
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        placeholder = {
            Text(text= stringResource(R.string.search_txt))
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}
//BARRA NAVEGACION INFERIOR
@Composable
fun BarraNavegacionInferiorConBackForward(navController: NavHostController) {
    val items = listOf(
        Pantallas.Inicio,
        Pantallas.Segunda,
        Pantallas.Tercera,
        Pantallas.Cuarta
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    NavigationBar {
        // Pantallas principales
        items.forEach { pantalla ->
            NavigationBarItem(
                icon = { Icon(pantalla.icono, contentDescription = pantalla.titulo) },
                label = { Text(pantalla.titulo) },
                selected = rutaActual == pantalla.route,
                onClick = {
                    navController.navigate(pantalla.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true

                        if (navController.previousBackStackEntry != null) {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }


    }
}
@Composable
fun RanuraImagenInferior() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp) // Puedes ajustar la altura según necesites
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.floor),
            contentDescription = "Imagen inferior",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
        )
    }
}

