package com.example.app_practica_mes.Modelo

import com.example.app_practica_mes.modelo.dataCatalogoCursos

class clsCatalogoCurso {
    suspend fun obtenerCursos(idCarrera: Int): List<dataCatalogoCursos> {
        // Pedido a la base de datos a través de Retrofit pasando el id_carrera
        val datos = RetrofitCliente.apiService.obtenerCursos(idCarrera)
        return datos
    }
}