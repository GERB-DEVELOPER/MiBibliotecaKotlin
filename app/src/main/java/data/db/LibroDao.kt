package data.db

import androidx.room.*
import com.example.mibiblioteca.data.model.Libro
import kotlinx.coroutines.flow.Flow

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