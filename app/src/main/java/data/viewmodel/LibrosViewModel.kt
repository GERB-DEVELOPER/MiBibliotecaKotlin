package data.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import data.db.AppDatabase
import data.model.Libro
import data.repository.LibroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar la lista de libros
 */
class LibrosViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: LibroRepository

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        val dao = AppDatabase.getDatabase(application).libroDao()
        repository = LibroRepository(dao)

        viewModelScope.launch {
            repository.allLibros.collect { librosList ->
                _libros.value = librosList
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                repository.searchLibros(query).collect { results ->
                    _libros.value = results
                }
            } else {
                repository.allLibros.collect { allBooks ->
                    _libros.value = allBooks
                }
            }
        }
    }

    fun deleteLibro(libro: Libro) {
        viewModelScope.launch {
            repository.deleteLibro(libro)
        }
    }

    fun addLibro(libro: Libro) {
        viewModelScope.launch {
            repository.insertLibro(libro)
        }
    }
}

