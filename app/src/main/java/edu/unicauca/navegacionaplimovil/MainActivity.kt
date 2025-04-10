package edu.unicauca.navegacionaplimovil // Paquete principal de la aplicación

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns // Para validación de email
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.unicauca.navegacionaplimovil.ui.theme.NavegacionAplimovilTheme // Asegúrate que la ruta al Theme sea correcta
import java.util.Calendar

// ==========================================================================
// --- COLORES GLOBALES ---
// ==========================================================================
val LightBlueBackground = Color(0xFFE0F7FA) // Fondo principal
val OrangeTitle = Color(0xFFFF5722)       // Títulos/énfasis
val LightGreenButton = Color(0xFF8BC34A)    // Botones específicos
val TextColor = Color.DarkGray          // Texto ingresado
val PlaceholderColor = Color.Gray         // Labels/placeholders

// ==========================================================================
// --- ACTIVIDAD PRINCIPAL ---
// ==========================================================================
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configura la UI usando Jetpack Compose
        setContent {
            NavegacionAplimovilTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Inicializa la navegación de la app
                    AppNavigator()
                }
            }
        }
    }
}

// ==========================================================================
// --- NAVEGADOR PRINCIPAL (SETUP DE RUTAS) ---
// ==========================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    // Define el grafo de navegación y la pantalla inicial
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { PantallaLogin(navController) }
        composable("crear_cuenta") { PantallaCrearCuenta(navController) }
        composable("registro_datos") { PantallaDatosPersonales(navController) }
        composable("registro_final") { PantallaRegistroFinal(navController) }
    }
}

// ==========================================================================
// --- PANTALLA DE LOGIN ---
// ==========================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLogin(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Validación de email (formato válido o vacío)
    val isEmailValid = remember(email) {
        Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isBlank()
    }
    // Validación de contraseña (mínimo 8 caracteres o vacío)
    val isPasswordValid = remember(password) {
        password.length >= 8 || password.isBlank()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlueBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text("TECHNO APP", color = OrangeTitle, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        // Campo Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico o número de") },
            isError = !isEmailValid && email.isNotBlank(), // Muestra error si es inválido y no vacío
            supportingText = {
                if (!isEmailValid && email.isNotBlank()) {
                    Text("Correo no válido", color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth().background(Color.White),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(color = TextColor), // Aplica color al texto ingresado
            colors = TextFieldDefaults.outlinedTextFieldColors( // Colores personalizados del campo
                focusedBorderColor = OrangeTitle,
                unfocusedBorderColor = PlaceholderColor,
                focusedLabelColor = OrangeTitle,
                unfocusedLabelColor = PlaceholderColor
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = { // Ícono para mostrar/ocultar contraseña
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(imageVector = image, contentDescription = "Toggle password visibility", modifier = Modifier.clickable { passwordVisible = !passwordVisible })
            },
            isError = !isPasswordValid && password.isNotBlank(), // Muestra error si es inválida y no vacía
            supportingText = {
                if (!isPasswordValid && password.isNotBlank()) {
                    Text("La contraseña debe tener al menos 8 caracteres", color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth().background(Color.White),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(color = TextColor),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = OrangeTitle,
                unfocusedBorderColor = PlaceholderColor,
                focusedLabelColor = OrangeTitle,
                unfocusedLabelColor = PlaceholderColor
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Iniciar Sesión
        Button(
            onClick = {
                // Lógica de inicio de sesión (simulada con navegación)
                if (isEmailValid && email.isNotBlank() && isPasswordValid && password.isNotBlank()) {
                    navController.navigate("registro_datos") // Navega si es válido
                } else {
                    println("Error: Credenciales inválidas o campos vacíos.") // Placeholder error handling
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("¿Has olvidado la contraseña?", color = TextColor)
        Spacer(modifier = Modifier.weight(1f)) // Empuja el botón de crear cuenta hacia abajo

        // Botón Crear Cuenta
        Button(
            onClick = { navController.navigate("crear_cuenta") },
            colors = ButtonDefaults.buttonColors(containerColor = LightGreenButton),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear una cuenta")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaLogin() {
    NavegacionAplimovilTheme { PantallaLogin(navController = rememberNavController()) }
}

// ==========================================================================
// --- PANTALLA DE CREAR CUENTA ---
// ==========================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrearCuenta(navController: NavHostController) {
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Verifica si las contraseñas coinciden (o si la confirmación está vacía)
    val passwordsMatch = remember(password, confirmPassword) {
        password == confirmPassword || confirmPassword.isBlank()
    }
    // Verifica si el formulario es válido para habilitar el botón
    val isFormValid = remember(emailOrPhone, password, confirmPassword, passwordsMatch) {
        emailOrPhone.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() && passwordsMatch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlueBackground)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("TECHNO APP", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = OrangeTitle, modifier = Modifier.padding(bottom = 40.dp))

        // Campo Email o Teléfono
        OutlinedTextField(
            value = emailOrPhone,
            onValueChange = { emailOrPhone = it },
            label = { Text("Correo electrónico o número de") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).background(Color.White),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            textStyle = TextStyle(color = TextColor),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = OrangeTitle,
                unfocusedBorderColor = PlaceholderColor,
                focusedLabelColor = OrangeTitle,
                unfocusedLabelColor = PlaceholderColor
            )
        )

        // Campo Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).background(Color.White),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = { // Ícono visibilidad
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(imageVector = image, contentDescription = "Toggle password visibility", modifier = Modifier.clickable { passwordVisible = !passwordVisible })
            },
            isError = !passwordsMatch && confirmPassword.isNotEmpty(), // Marca error si no coinciden y confirmación no está vacía
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            textStyle = TextStyle(color = TextColor),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = OrangeTitle,
                unfocusedBorderColor = PlaceholderColor,
                focusedLabelColor = OrangeTitle,
                unfocusedLabelColor = PlaceholderColor,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
            )
        )

        // Campo Confirmar Contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp).background(Color.White),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = { // Ícono visibilidad
                val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                Icon(imageVector = image, contentDescription = "Toggle password visibility", modifier = Modifier.clickable { confirmPasswordVisible = !confirmPasswordVisible })
            },
            isError = !passwordsMatch && confirmPassword.isNotEmpty(), // Error si no coinciden y no está vacío
            supportingText = { // Mensaje de error si no coinciden
                if (!passwordsMatch && confirmPassword.isNotEmpty()) {
                    Text("Las contraseñas no coinciden", color = MaterialTheme.colorScheme.error)
                }
            },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            textStyle = TextStyle(color = TextColor),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = OrangeTitle,
                unfocusedBorderColor = PlaceholderColor,
                focusedLabelColor = OrangeTitle,
                unfocusedLabelColor = PlaceholderColor,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error,
                errorSupportingTextColor = MaterialTheme.colorScheme.error
            )
        )

        // Texto Legal
        Text(
            text = "Al hacer clic en Registrarte, aceptas las Condiciones, la Política de privacidad y la Política de cookies. Es posible que te enviemos notificaciones por SMS, que podrás desactivar cuando quieras.",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = TextColor,
            modifier = Modifier.padding(bottom = 32.dp)
        )


        // Botón Registrarse
        Button(
            onClick = {
                if (isFormValid) {
                    navController.navigate("registro_datos") // Navega a la siguiente pantalla de registro
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(50), // Botón tipo cápsula
            colors = ButtonDefaults.buttonColors(containerColor = LightGreenButton),
            enabled = isFormValid // Habilitado solo si el formulario es válido
        ) {
            Text("Registrarte", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaCrearCuenta() {
    NavegacionAplimovilTheme { PantallaCrearCuenta(navController = rememberNavController()) }
}

// ==========================================================================
// --- PANTALLA DE DATOS PERSONALES ---
// ==========================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDatosPersonales(navController: NavHostController) {
    val context = LocalContext.current // Contexto para DatePickerDialog
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var generoSeleccionado by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // Para desplegable de género

    // Estados de error para validación
    var nombreError by remember { mutableStateOf(false) }
    var apellidosError by remember { mutableStateOf(false) }
    var fechaError by remember { mutableStateOf(false) }
    var generoError by remember { mutableStateOf(false) }

    // Valida que todos los campos obligatorios estén llenos
    fun validarCampos(): Boolean {
        nombreError = nombre.isBlank()
        apellidosError = apellidos.isBlank()
        fechaError = fechaNacimiento.isBlank()
        generoError = generoSeleccionado.isBlank()
        return !nombreError && !apellidosError && !fechaError && !generoError
    }

    Column(
        modifier = Modifier.fillMaxSize().background(LightBlueBackground).padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Botón para volver a la pantalla anterior
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("TECHNO APP", color = OrangeTitle, style = MaterialTheme.typography.titleLarge)
        Text("Crea una cuenta", color = Color.Black, style = MaterialTheme.typography.titleMedium)
        Text("Es fácil y rápido.", color = Color.Black, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(16.dp))

        // Campo Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it; nombreError = it.isBlank() },
            label = { Text("Nombre") },
            isError = nombreError,
            modifier = Modifier.fillMaxWidth().background(Color.White),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(color = TextColor),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = OrangeTitle,
                unfocusedBorderColor = PlaceholderColor,
                focusedLabelColor = OrangeTitle,
                unfocusedLabelColor = PlaceholderColor,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
            )
        )
        if (nombreError) { Text("El nombre es obligatorio", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.Start)) }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Apellidos
        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it; apellidosError = it.isBlank() },
            label = { Text("Apellidos") },
            isError = apellidosError,
            modifier = Modifier.fillMaxWidth().background(Color.White),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(color = TextColor),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = OrangeTitle,
                unfocusedBorderColor = PlaceholderColor,
                focusedLabelColor = OrangeTitle,
                unfocusedLabelColor = PlaceholderColor,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
            )
        )
        if (apellidosError) { Text("Los apellidos son obligatorios", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.Start)) }

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Fecha Nacimiento (con selector)
        OutlinedTextField(
            value = fechaNacimiento,
            onValueChange = {}, // No editable manualmente
            readOnly = true,
            label = { Text("Fecha de nacimiento*") },
            isError = fechaError,
            trailingIcon = { // Abre el DatePickerDialog
                Icon(Icons.Filled.DateRange, contentDescription = "Seleccionar fecha", modifier = Modifier.clickable {
                    val c = Calendar.getInstance()
                    DatePickerDialog(context, { _, y, m, d ->
                        fechaNacimiento = "$d/${m + 1}/$y" // Actualiza estado
                        fechaError = fechaNacimiento.isBlank()
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
                        .apply { datePicker.maxDate = c.timeInMillis } // Limita fecha máxima
                        .show()
                })
            },
            modifier = Modifier.fillMaxWidth().background(Color.White),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(color = TextColor),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = OrangeTitle,
                unfocusedBorderColor = PlaceholderColor,
                focusedLabelColor = OrangeTitle,
                unfocusedLabelColor = PlaceholderColor,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
                // No se necesita disabledTextColor aquí si textStyle ya lo maneja
            )
        )
        if (fechaError) { Text("La fecha de nacimiento es obligatoria", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.Start)) }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Género*", modifier = Modifier.align(Alignment.Start), color = if (generoError) MaterialTheme.colorScheme.error else Color.Black)

        // Desplegable Género
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField( // Campo que muestra la selección
                value = generoSeleccionado.ifEmpty { "Seleccione" }, // Placeholder si está vacío
                onValueChange = {},
                readOnly = true,
                isError = generoError,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor() // Necesario para el menú
                    .fillMaxWidth()
                    .background(Color.White),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                // Color del texto dinámico: gris si es placeholder, oscuro si hay selección
                textStyle = TextStyle(color = if (generoSeleccionado.isEmpty()) PlaceholderColor else TextColor),
                // Colores completos aquí, asegurando que la llamada a la función sea válida
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = OrangeTitle,
                    unfocusedBorderColor = if(generoError) MaterialTheme.colorScheme.error else PlaceholderColor, // Borde cambia con error
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    // El color del texto cuando está inactivo (readOnly) lo define textStyle
                    // Ajustamos los colores de borde/icono/label para el estado inactivo si es necesario
                    disabledBorderColor = if(generoError) MaterialTheme.colorScheme.error else PlaceholderColor,
                    disabledLabelColor = PlaceholderColor, // Puede que no se vea mucho si hay valor
                    disabledTrailingIconColor = PlaceholderColor
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Opciones del menú
                listOf("Hombre", "Mujer", "Prefiero no decirlo").forEach { genero ->
                    DropdownMenuItem(
                        text = { Text(genero) },
                        onClick = {
                            generoSeleccionado = genero // Actualiza selección
                            generoError = false         // Borra error
                            expanded = false            // Cierra menú
                        }
                    )
                }
            }
        }
        if (generoError) { Text("Debe seleccionar un género", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.align(Alignment.Start)) }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Siguiente
        Button(
            onClick = {
                if (validarCampos()) { // Valida antes de navegar
                    navController.navigate("registro_final")
                } else {
                    println("Faltan campos por llenar o son inválidos.")
                }
            },
            modifier = Modifier.fillMaxWidth().height(45.dp)
        ) {
            Text("Siguiente")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaDatosPersonales() {
    NavegacionAplimovilTheme { PantallaDatosPersonales(navController = rememberNavController()) }
}

// ==========================================================================
// --- PANTALLA DE REGISTRO FINALIZADO ---
// ==========================================================================
@Composable
fun PantallaRegistroFinal(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().background(LightBlueBackground).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Centra contenido
    ) {
        Text("¡Registro completado!", style = MaterialTheme.typography.headlineMedium, color = Color(0xFF4CAF50)) // Verde éxito
        Spacer(modifier = Modifier.height(16.dp))
        Text("Gracias por registrarte en TECHNO APP.", color = Color.Black)
        Spacer(modifier = Modifier.height(32.dp))

        // Botón Volver al Inicio
        Button(onClick = {
            // Navega a login y limpia el backstack para evitar volver al flujo de registro
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
        }) {
            Text("Volver al inicio")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaRegistroFinal() {
    NavegacionAplimovilTheme { PantallaRegistroFinal(navController = rememberNavController()) }
}