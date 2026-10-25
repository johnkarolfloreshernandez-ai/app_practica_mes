package com.example.app_practica_mes.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataRegistro(
    @SerialName("id") val id: Int = 0,
    @SerialName("carrera_id") val carreraId: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("espacio") val espacio: String = "",
    @SerialName("url_imagen_principal") val urlImagenPrincipal: String = ""
)
