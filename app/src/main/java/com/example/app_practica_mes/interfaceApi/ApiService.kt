package com.example.app_practica_mes.interfaceApi
import com.example.app_practica_mes.Modelo.DetalleCursoResponse
import com.example.app_practica_mes.Modelo.RegistroRequest
import com.example.app_practica_mes.Modelo.RegistroResponse
import retrofit2.http.Body
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
interface ApiService {

    @POST("registrar_curso.php")
    suspend fun registrarCurso(@Body request: RegistroRequest): Response<RegistroResponse>

    @GET("detalle_curso.php")
    suspend fun obtenerDetalleCurso(@Query("curso_id") cursoId: Int): Response<DetalleCursoResponse>



}