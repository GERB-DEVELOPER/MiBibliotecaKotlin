package data.viewmodel.ui.components.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mibiblioteca.data.model.Libro
import data.viewmodel.DetalleLibroViewModel

/**
 * Pantalla que muestra el detalle de un libro
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLibroScreen(
    libroId: Int,
    onBackClick: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: DetalleLibroViewModel
) {
    LaunchedEffect(libroId) {
        viewModel.loadLibro(libroId)
    }

    val libro by viewModel.libro.collectAsState()

    Scaffold(
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
                }
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
                    text = libroData.titulo,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                InfoRow(label = "Autor", value = libroData.autor)
                InfoRow(label = "Editorial", value = libroData.editorial)
                InfoRow(label = "Edición", value = libroData.edicion)
                InfoRow(label = "Año", value = libroData.anoLanzamiento.toString())
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(100.dp)
        )
        Text(text = value)
    }
}

