package data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import data.db.AppDatabase
import com.example.mibiblioteca.data.model.Libro
import data.repository.LibroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para editar o crear un libro
 */
class EditarLibroViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: LibroRepository

    private val _libro = MutableStateFlow<Libro?>(null)
    val libro: StateFlow<Libro?> = _libro

    // Estados para cada campo
    val titulo = MutableStateFlow("")
    val autor = MutableStateFlow("")
    val edicion = MutableStateFlow("")
    val editorial = MutableStateFlow("")
    val anoLanzamiento = MutableStateFlow("")

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved

    init {
        val dao = AppDatabase.getDatabase(application).libroDao()
        repository = LibroRepository(dao)
    }

    fun loadLibro(id: Int) {
        viewModelScope.launch {
            val libroFromDb = repository.getLibroById(id)
            _libro.value = libroFromDb

            libroFromDb?.let {
                titulo.value = it.titulo
                autor.value = it.autor
                edicion.value = it.edicion
                editorial.value = it.editorial
                anoLanzamiento.value = it.anoLanzamiento.toString()
            }
        }
    }

    fun saveLibro() {
        val año = anoLanzamiento.value.toIntOrNull() ?: 0

        val libroToSave = libro.value?.copy(
            titulo = titulo.value,
            autor = autor.value,
            edicion = edicion.value,
            editorial = editorial.value,
            anoLanzamiento = año
        ) ?: Libro(
            titulo = titulo.value,
            autor = autor.value,
            edicion = edicion.value,
            editorial = editorial.value,
            anoLanzamiento = año
        )

        viewModelScope.launch {
            if (libroToSave.id == 0) {
                repository.insertLibro(libroToSave)
            } else {
                repository.updateLibro(libroToSave)
            }
            _isSaved.value = true
        }
    }

    fun resetSaveState() {
        _isSaved.value = false
    }
}