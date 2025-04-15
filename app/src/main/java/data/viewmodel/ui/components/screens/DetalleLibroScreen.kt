package data.viewmodel.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import data.viewmodel.DetalleLibroViewModel

/**
  Pantalla que muestra la informacion de un libro especifico e i ncluye una barra superior con opciones para regresar o editar el libro,
 y presenta los datos del libro en un diseño estructurado

 la funcion: InfoRow se usa para mostrar una propiedad del libro.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLibroScreen(
    libroId: Int,
    onBackClick: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: DetalleLibroViewModel
) {
    // Carga el libro según el ID
    LaunchedEffect(libroId) {
        viewModel.loadLibro(libroId)
    }

    val libro by viewModel.libro.collectAsState()

    Scaffold(
        // Fondo de pantalla según el tema
        containerColor = MaterialTheme.colorScheme.background,
        // Barra superior con colores del tema
        topBar = {
            TopAppBar(
                title = { Text(text = libro?.titulo ?: "Detalle del libro") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver atrás"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { libro?.id?.let { onEditClick(it) } }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar libro"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        libro?.let { libroData ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Vista General Del Libro",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))
                InfoRow(label = "Libro", value = libroData.titulo)
                InfoRow(label = "Autor", value = libroData.autor)
                InfoRow(label = "Editorial", value = libroData.editorial)
                InfoRow(label = "Edición", value = libroData.edicion)
                InfoRow(label = "Año", value = libroData.anoLanzamiento.toString())
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.small,
        tonalElevation = 1.dp,
        color = MaterialTheme.colorScheme.surfaceVariant,            // fondo ligero
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant   // texto
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

