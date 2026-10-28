
package com.example.app_practica_mes.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlumnoResponse(
    @SerialName("matricula")
    val matricula: String = "",

    @SerialName("nombre_completo")
    val nombreCompleto: String = "",

    @SerialName("correo_institucional")
    val correoInstitucional: String = ""
)
