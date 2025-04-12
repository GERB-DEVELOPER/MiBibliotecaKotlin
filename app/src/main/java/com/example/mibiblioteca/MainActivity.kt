package com.example.mibiblioteca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mibiblioteca.ui.theme.MiBibliotecaTheme
import data.viewmodel.DetalleLibroViewModel
import data.viewmodel.EditarLibroViewModel
import data.viewmodel.LibrosViewModel
import data.viewmodel.ui.components.screens.DetalleLibroScreen
import data.viewmodel.ui.components.screens.EditarLibroScreen
import data.viewmodel.ui.components.screens.LibrosListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiBibliotecaTheme(
                darkTheme = false,  // Forzar modo claro
                dynamicColor = false  // Desactivar colores dinámicos
            ) {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "libros_list") {
        composable("libros_list") {
            val viewModel: LibrosViewModel = viewModel()
            LibrosListScreen(
                onLibroClick = { libroId ->
                    navController.navigate("libro_detalle/$libroId")
                },
                onAddLibroClick = {
                    navController.navigate("libro_editar/0")
                },
                viewModel = viewModel
            )
        }

        composable(
            route = "libro_detalle/{libroId}",
            arguments = listOf(
                navArgument("libroId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val libroId = backStackEntry.arguments?.getInt("libroId") ?: 0
            val viewModel: DetalleLibroViewModel = viewModel()

            DetalleLibroScreen(
                libroId = libroId,
                onBackClick = {
                    navController.popBackStack()
                },
                onEditClick = { id ->
                    navController.navigate("libro_editar/$id")
                },
                viewModel = viewModel
            )
        }

        composable(
            route = "libro_editar/{libroId}",
            arguments = listOf(
                navArgument("libroId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val libroId = backStackEntry.arguments?.getInt("libroId") ?: 0
            val viewModel: EditarLibroViewModel = viewModel()

            EditarLibroScreen(
                libroId = libroId,
                onBackClick = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }
    }
}