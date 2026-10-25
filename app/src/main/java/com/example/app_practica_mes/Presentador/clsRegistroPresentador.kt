package com.example.app_practica_mes.Presentador

import com.example.app_practica_mes.Modelo.clsRegistroModelo
import com.example.app_practica_mes.Vista.RegistroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class clsRegistroPresentador (private val view: RegistroActivity){
    private val modelo = clsRegistroModelo()


    fun cargarDatosCurso(nombreCurso: String, urlImagen: String) {
        if (urlImagen.isNotEmpty()) {
            view.mostrarImagenCurso(urlImagen)
        }
    }

    fun realizarRegistro(cursoId: Int, matricula: String, fechaRegistro: String) {
        // Validaciones previas en la vista
        if (cursoId <= 0 || matricula.isEmpty() || fechaRegistro.isEmpty()) {
            view.mostrarError("Por favor, completa todos los campos solicitados")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    modelo.registrarAlumnoCurso(cursoId, matricula, fechaRegistro)
                }

                if (respuesta.isSuccessful) {
                    val mensajeExito = respuesta.body()?.mensaje ?: "Inscripción registrada correctamente"
                    view.registroExitoso(mensajeExito)
                } else {
                    // Extrae el mensaje enviado por PHP (ej. 400 Bad Request o 404 Not Found)
                    val errorBody = respuesta.errorBody()?.string()
                    val mensajeError = try {
                        JSONObject(errorBody ?: "").getString("error")
                    } catch (e: Exception) {
                        "Error al procesar la solicitud"
                    }
                    view.mostrarError(mensajeError)
                }
            } catch (e: Exception) {
                view.mostrarError("Error de conexión: ${e.localizedMessage ?: "Error desconocido"}")
            }
        }
    }

}
