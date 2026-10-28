package com.example.app_practica_mes.Modelo

import com.example.app_practica_mes.modelo.dataCarrera

class clsCarrerasModelo {
    suspend fun obtenerCarreras(): List<dataCarrera> {
        // Pedido a la base de datos a través de Retrofit
        val datos = RetrofitCliente.apiService.obtenerCarreras()

        // Creamos la opción por defecto con idCarrera = 0 y la etiqueta descriptiva
        val listaFinal = mutableListOf(dataCarrera(0, "SELECCIONE LA CARRERA:"))
        listaFinal.addAll(datos)

        return listaFinal
    }
}