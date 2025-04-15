package data.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import data.db.AppDatabase
import data.model.Libro
import data.repository.LibroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
  Este ViewModel es el responsable de gestionar la obtencion de datos de un libro especifico
 y exponerlos en la interfaz de DetalleLibroScreen

 Accede a la DataBase mediante la clase LibroRepository que se encarga de realizar las operaciones
 del CRUD.

 y el AndroidViewModel es la que se encarga de darme el permiso de acceder a la DB

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

