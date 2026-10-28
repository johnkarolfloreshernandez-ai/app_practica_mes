
package com.example.app_practica_mes.interfaceApi

import com.example.app_practica_mes.Modelo.AlumnoResponse
import com.example.app_practica_mes.Modelo.DetalleCursoResponse
import com.example.app_practica_mes.Modelo.RegistroCursoResponse
import com.example.app_practica_mes.Modelo.RegistroRequest
import com.example.app_practica_mes.Modelo.RegistroResponse
import com.example.app_practica_mes.Modelo.dataCarrera
import com.example.app_practica_mes.Modelo.dataCatalogoCursos
import com.example.app_practica_mes.modelo.AlumnoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("obtener_alumno.php")
    suspend fun obtenerAlumnoPorMatricula(
        @Query("matricula") matricula: String
    ): Response<AlumnoResponse>

    @POST("registrar_curso.php")
    suspend fun registrarCurso(
        @Body request: RegistroRequest
    ): Response<RegistroResponse>

    @GET("detalle_curso.php")
    suspend fun obtenerDetalleCurso(
        @Query("curso_id") cursoId: Int
    ): Response<DetalleCursoResponse>
    suspend fun obtenerDetalleCurso(@Query("curso_id") cursoId: Int): Response<DetalleCursoResponse>

    @GET("obtener_carreras.php")
    suspend fun obtenerCarreras(): List<dataCarrera>

    @GET("obtener_cursos.php")
    suspend fun obtenerCursos(@Query("id_carrera") carreraId: Int): List<dataCatalogoCursos>

    @GET("ver_registros.php")
    suspend fun obtenerRegistros(): List<RegistroCursoResponse>
}
