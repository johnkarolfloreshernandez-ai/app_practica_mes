
package com.example.app_practica_mes.Modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistroResponse(
    @SerialName("exito")
    val exito: Boolean = false,

    @SerialName("mensaje")
    val mensaje: String = ""
)
