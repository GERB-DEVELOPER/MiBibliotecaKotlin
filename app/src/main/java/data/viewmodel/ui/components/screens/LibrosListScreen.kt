package data.viewmodel.ui.components.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.BadgeDefaults.containerColor
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mibiblioteca.data.model.Libro
import data.viewmodel.ui.components.CustomTextField
import data.viewmodel.ui.components.LibroItem
import data.viewmodel.LibrosViewModel

/**
 * Pantalla que muestra la lista de libros
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrosListScreen(
    onLibroClick: (Int) -> Unit,
    onAddLibroClick: () -> Unit,
    viewModel: LibrosViewModel = viewModel()
) {
    val libros by viewModel.libros.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Mi Biblioteca") },
                actions = {
                    IconButton(onClick = { /* Toggle search */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddLibroClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir libro"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search field
            CustomTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                label = "Buscar libros",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // List of books
            if (libros.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay libros. ¡Añade uno!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(libros) { libro ->
                        LibroItem(
                            libro = libro,
                            onClick = { onLibroClick(libro.id) },
                            onDeleteClick = { viewModel.deleteLibro(libro) }
                        )
                    }
                }
            }
        }
    }
}

