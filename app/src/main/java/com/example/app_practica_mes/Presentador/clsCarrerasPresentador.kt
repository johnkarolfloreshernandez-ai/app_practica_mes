package com.example.app_practica_mes.Presentador

import com.example.app_practica_mes.Modelo.clsCarrerasModelo
import com.example.app_practica_mes.Modelo.clsCatalogoCurso
import com.example.app_practica_mes.Modelo.dataCarrera
import com.example.app_practica_mes.Modelo.dataCatalogoCursos
import com.example.app_practica_mes.Vista.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class clsCarrerasPresentador(private val vista: MainActivity) {
    private val modeloCatalogoCursos = clsCatalogoCurso()
    private val modelo = clsCarrerasModelo()
    private var listaCarreras: List<dataCarrera> = emptyList()

    fun iniciar() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // 1. Cargar la lista de carreras en el Spinner
                listaCarreras = modelo.obtenerCarreras()
                vista.mostrarCarrera(listaCarreras)

                // 2. Cargar inmediatamente todos los cursos (id 0)
                cargarCursos(0)

            } catch (e: Exception) {
                vista.mostrarError("Error al iniciar: " + e.localizedMessage)
            }
        }
    }

    fun seleccionarCarrera(posicion: Int) {
        if (posicion >= 0 && posicion < listaCarreras.size) {
            val carrera = listaCarreras[posicion]
            cargarCursos(carrera.idCarrera)
        }
    }

    private fun cargarCursos(idcarrera: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val cursos = modeloCatalogoCursos.obtenerCursos(idcarrera)
                vista.mostrarCursos(cursos)
            } catch (e: Exception) {
                vista.mostrarError("Error al cargar los cursos: " + e.localizedMessage)
            }
        }
    }

    fun seleccionarCurso(curso: dataCatalogoCursos) {
        vista.seleccionarCurso(curso)
    }

    fun registrarCurso(curso: dataCatalogoCursos) {
        vista.registrarCurso(curso)
    }
}