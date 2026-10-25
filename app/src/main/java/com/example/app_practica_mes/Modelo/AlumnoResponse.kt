package com.example.app_practica_mes.modelo.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlumnoResponse(

    @SerialName(value = "exito") val exito: Boolean = false,
    @SerialName(value = "matricula") val matricula: String? = null,
    @SerialName(value = "nombre") val nombre: String? = null,
    @SerialName(value = "correo") val correo: String? = null,
    @SerialName(value = "mensaje") val mensaje: String? = null
)
