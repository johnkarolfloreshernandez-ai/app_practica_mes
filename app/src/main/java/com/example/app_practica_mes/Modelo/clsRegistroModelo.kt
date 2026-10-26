package com.example.app_practica_mes.Modelo

import com.example.app_practica_mes.modelo.AlumnoResponse
import retrofit2.Response

class clsRegistroModelo {

    suspend fun registrarAlumnoCurso(cursoId: Int,matricula: String, fechaRegistro: String): Response<RegistroResponse> {
        val request = RegistroRequest(cursoId = cursoId, matricula = matricula, fechaRegistro = fechaRegistro)
        return RetrofitCliente.apiService.registrarCurso(request)
    }
    suspend fun obtenerAlumno(matricula: String): Response<AlumnoResponse> {
        return RetrofitCliente.apiService.obtenerAlumnoPorMatricula(matricula)
    }

}