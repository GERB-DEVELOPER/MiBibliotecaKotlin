package data.viewmodel.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mibiblioteca.data.model.Libro

/**
 * Componente reutilizable que muestra un item de libro en una lista
 */
@Composable
fun LibroItem(
    libro: Libro,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    // Importa los colores directamente
    val primaryColor = com.example.mibiblioteca.ui.theme.PrimaryColor
    val secondaryColor = com.example.mibiblioteca.ui.theme.SecondaryColor
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8EAF6)  // Color directo
            //containerColor = MaterialTheme.colorScheme.surfaceVariant,
            //contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = libro.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    //color = MaterialTheme.colorScheme.primary
                    color = primaryColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Autor: ${libro.autor}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Editorial: ${libro.editorial} (${libro.anoLanzamiento})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar libro",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
