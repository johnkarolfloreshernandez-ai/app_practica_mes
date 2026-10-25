package com.example.app_practica_mes.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistroRequest(
    @SerialName("curso_id") val cursoId: Int = 0,
    @SerialName("matricula") val matricula: String = "",
    @SerialName("fecha_registro") val fechaRegistro: String = ""
)
