package com.example.app_practica_mes.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCarrera(
    @SerialName("id") val idCarrera: Int = 0,
    @SerialName("nombre_carrera") val carrera: String = ""
) {
    override fun toString(): String {
        return carrera
    }
}