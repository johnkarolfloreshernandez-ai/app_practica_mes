package com.example.app_practica_mes.Modelo


class clsCatalogoCurso {
    suspend fun obtenerCursos(idCarrera: Int): List<dataCatalogoCursos> {
        val datos = RetrofitCliente.apiService.obtenerCursos(idCarrera)
        return datos
    }
}