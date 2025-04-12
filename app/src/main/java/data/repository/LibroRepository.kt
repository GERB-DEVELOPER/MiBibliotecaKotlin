package data.repository

import data.db.LibroDao
import com.example.mibiblioteca.data.model.Libro
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio que maneja las operaciones de datos para los libros
 */
class LibroRepository(private val libroDao: LibroDao) {
    val allLibros: Flow<List<Libro>> = libroDao.getAllLibros()

    suspend fun getLibroById(id: Int): Libro? {
        return libroDao.getLibroById(id)
    }

    suspend fun insertLibro(libro: Libro): Long {
        return libroDao.insertLibro(libro)
    }

    suspend fun updateLibro(libro: Libro) {
        libroDao.updateLibro(libro)
    }

    suspend fun deleteLibro(libro: Libro) {
        libroDao.deleteLibro(libro)
    }

    fun searchLibros(query: String): Flow<List<Libro>> {
        return libroDao.searchLibros(query)
    }
}

