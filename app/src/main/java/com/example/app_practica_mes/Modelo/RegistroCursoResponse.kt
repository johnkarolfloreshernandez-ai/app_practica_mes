package com.example.app_practica_mes.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistroCursoResponse(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("carrera_id")
    val carreraId: Int = 0,

    @SerialName("nombre")
    val nombre: String = "",

    @SerialName("codigo")
    val codigo: String = "",

    @SerialName("cupo")
    val cupo: Int = 0,

    @SerialName("inscritos")
    val inscritos: Int = 0,

    @SerialName("url_imagen_principal")
    val urlImagenPrincipal: String = "",

    @SerialName("nombre_carrera")
    val nombreCarrera: String = ""
)
