package data.viewmodel.ui.components.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.viewmodel.EditarLibroViewModel
import data.viewmodel.ui.components.CustomTextField

/**
 * Pantalla para editar o crear un libro.
 *
 * Muestra campos para:
 * - Título
 * - Autor
 * - Editorial
 * - Edición
 * - Año de lanzamiento
 *
 * Valida los datos introducidos y, al guardar, notifica al ViewModel para persistir
 * los cambios o la creación de un nuevo libro. También permite regresar a la pantalla anterior.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarLibroScreen(
    libroId: Int = 0,
    onBackClick: () -> Unit,
    viewModel: EditarLibroViewModel
) {
    // Load libro if editing existing one
    LaunchedEffect(libroId) {
        if (libroId != 0) {
            viewModel.loadLibro(libroId)
        }
    }

    val titulo by viewModel.titulo.collectAsState()
    val autor by viewModel.autor.collectAsState()
    val edicion by viewModel.edicion.collectAsState()
    val editorial by viewModel.editorial.collectAsState()
    val anoLanzamiento by viewModel.anoLanzamiento.collectAsState()
    val isSaved by viewModel.isSaved.collectAsState()

    // Handle navigation after save
    LaunchedEffect(isSaved) {
        if (isSaved) {
            viewModel.resetSaveState()
            onBackClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (libroId == 0) "Nuevo libro" else "Editar libro"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.saveLibro() }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Guardar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            CustomTextField(
                value = titulo,
                onValueChange = { viewModel.titulo.value = it },
                label = "Título",
                isError = titulo.isEmpty(),
                errorMessage = "El título es obligatorio"
            )

            CustomTextField(
                value = autor,
                onValueChange = { viewModel.autor.value = it },
                label = "Autor",
                isError = autor.isEmpty(),
                errorMessage = "El autor es obligatorio"
            )

            CustomTextField(
                value = editorial,
                onValueChange = { viewModel.editorial.value = it },
                label = "Editorial"
            )

            CustomTextField(
                value = edicion,
                onValueChange = { viewModel.edicion.value = it },
                label = "Edición"
            )

            CustomTextField(
                value = anoLanzamiento,
                onValueChange = { viewModel.anoLanzamiento.value = it },
                label = "Año de lanzamiento",
                isError = anoLanzamiento.isNotEmpty() && anoLanzamiento.toIntOrNull() == null,
                errorMessage = "Debe ser un número"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.saveLibro() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Guardar libro")
            }
        }
    }
}
