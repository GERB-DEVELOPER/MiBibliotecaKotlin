package data.db

import androidx.room.*
import data.model.Libro
import kotlinx.coroutines.flow.Flow


/**
Esta interfaz sirve para acceder a la tabla Libros de la DB SQLite
y Define las operaciones que se pueden realizar.
 */

@Dao
interface LibroDao {
    @Query("SELECT * FROM libros ORDER BY titulo ASC")
    fun getAllLibros(): Flow<List<Libro>>

    @Query("SELECT * FROM libros WHERE id = :id")
    suspend fun getLibroById(id: Int): Libro?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLibro(libro: Libro): Long

    @Update
    suspend fun updateLibro(libro: Libro)

    @Delete
    suspend fun deleteLibro(libro: Libro)

    @Query("SELECT * FROM libros WHERE titulo LIKE '%' || :query || '%' OR autor LIKE '%' || :query || '%'")
    fun searchLibros(query: String): Flow<List<Libro>>
}