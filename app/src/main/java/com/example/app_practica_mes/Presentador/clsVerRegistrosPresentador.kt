
package com.example.app_practica_mes.Presentador

import android.util.Log
import com.example.app_practica_mes.Modelo.clsRegistroModelo
import com.example.app_practica_mes.Vista.VerRegistrosActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class clsVerRegistrosPresentador(
    private val vista: VerRegistrosActivity
) {

    private val modelo = clsRegistroModelo()

    fun cargarRegistros() {
        vista.mostrarCargando(true)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val cursos = withContext(Dispatchers.IO) {
                    modelo.obtenerRegistros()
                }

                Log.d(
                    "VER_REGISTROS",
                    "Cursos recibidos: ${cursos.size}"
                )

                vista.mostrarCursos(cursos)

            } catch (e: Exception) {
                Log.e(
                    "VER_REGISTROS",
                    "Error al cargar cursos",
                    e
                )

                vista.mostrarError(
                    "No se pudieron cargar los cursos: ${e.message}"
                )
            } finally {
                vista.mostrarCargando(false)
            }
        }
    }
}
