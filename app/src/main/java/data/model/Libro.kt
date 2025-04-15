package data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
//Modelo de la tabla con la cual interactuará los modelos para consultar y alamcenar en la DB

@Entity(tableName = "libros")
data class Libro(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val autor: String,
    val edicion: String,
    val editorial: String,
    val anoLanzamiento: Int,
    val imagen: String? = null
)



