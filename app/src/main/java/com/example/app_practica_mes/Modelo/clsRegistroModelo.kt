package com.example.app_practica_mes.Modelo

import retrofit2.Response

class clsRegistroModelo {

    suspend fun registrarAlumnoCurso(cursoId: Int,matricula: String, fechaRegistro: String): Response<RegistroResponse> {
        val request = RegistroRequest(cursoId = cursoId, matricula = matricula, fechaRegistro = fechaRegistro)
        return RetrofitCliente.apiService.registrarCurso(request)
    }
}