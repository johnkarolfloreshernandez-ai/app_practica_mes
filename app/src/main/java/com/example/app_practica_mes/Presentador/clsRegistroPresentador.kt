
package com.example.app_practica_mes.Presentador

import android.util.Log

import com.example.app_practica_mes.Modelo.clsRegistroModelo
import com.example.app_practica_mes.Vista.RegistroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class clsRegistroPresentador(
    private val view: RegistroActivity
) {

    private val modelo = clsRegistroModelo()

    fun cargarDatosCurso(nombreCurso: String, urlImagen: String) {
        if (urlImagen.isNotBlank()) {
        if (nombreCurso.isNotEmpty()) {
            view.mostrarNombreCurso(nombreCurso)
        }
        if (urlImagen.isNotEmpty()) {
            view.mostrarImagenCurso(urlImagen)
        }
    }

    fun realizarRegistro(
        cursoId: Int,
        matricula: String,
        fechaRegistro: String
    ) {
        if (cursoId <= 0 || matricula.isBlank() || fechaRegistro.isBlank()) {
            view.mostrarError("Por favor, completa todos los campos solicitados")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.registrarAlumnoCurso(
                        cursoId,
                        matricula,
                        fechaRegistro
                    )
                }

                if (respuesta.isSuccessful) {
                    val mensajeExito = respuesta.body()?.mensaje
                        ?: "Inscripción registrada correctamente"

                    view.registroExitoso(mensajeExito)
                } else {
                    val errorBody = respuesta.errorBody()?.string()

                    val mensajeError = try {
                        JSONObject(errorBody ?: "").optString(
                            "error",
                            "Error al procesar la solicitud"
                        )
                    } catch (e: Exception) {
                        "Error al procesar la solicitud"
                    }

                    view.mostrarError(mensajeError)
                }
            } catch (e: Exception) {
                view.mostrarError(
                    "Error de conexión: ${
                        e.localizedMessage ?: "Error desconocido"
                    }"
                )
            }
        }
    }

    fun buscarAlumnoPorMatricula(matricula: String) {
        if (matricula.isBlank()) {
            view.autocompletarCorreo("")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.obtenerAlumno(matricula)
                }

                if (respuesta.isSuccessful) {
                    val correoObtenido =
                        respuesta.body()?.correoInstitucional ?: ""

                    view.autocompletarCorreo(correoObtenido)
                } else {
                    view.autocompletarCorreo("")
                }
            } catch (e: Exception) {
                view.autocompletarCorreo("")
            }
        }
    }
}
