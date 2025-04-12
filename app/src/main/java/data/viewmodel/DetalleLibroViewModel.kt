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
 * ViewModel para gestionar el detalle de un libro
 */
class DetalleLibroViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: LibroRepository

    private val _libro = MutableStateFlow<Libro?>(null)
    val libro: StateFlow<Libro?> = _libro

    init {
        val dao = AppDatabase.getDatabase(application).libroDao()
        repository = LibroRepository(dao)
    }

    fun loadLibro(id: Int) {
        viewModelScope.launch {
            _libro.value = repository.getLibroById(id)
        }
    }
}

