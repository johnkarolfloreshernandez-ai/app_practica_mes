package com.example.app_practica_mes.modelo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class dataCatalogoCursos(
    @SerialName("id") val id: Int = 0,
    @SerialName("carrera_id") val carreraId: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("codigo") val codigo: String = "",
    @SerialName("descripcion") val descripcion: String = "",
    @SerialName("espacio") val espacio: String = "",
    @SerialName("total_horas") val totalHoras: String = "",
    @SerialName("horario_atencion") val horarioAtencion: String = "",
    @SerialName("url_imagen_principal") val urlImagenPrincipal: String = "",
    @SerialName("cupo") val cupo: Int = 0
)
