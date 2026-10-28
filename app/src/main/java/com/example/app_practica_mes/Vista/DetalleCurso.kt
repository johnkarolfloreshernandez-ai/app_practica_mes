package com.example.app_practica_mes.Vista

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.app_practica_mes.Modelo.RetrofitCliente
import com.example.app_practica_mes.R
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleCurso : AppCompatActivity() {

    private var cursoId: Int = 0


    private lateinit var btnBack: Button
    private lateinit var imgCurso: ImageView
    private lateinit var txtNombreCurso: TextView
    private lateinit var txtCodigo: TextView
    private lateinit var txtDescripcion: TextView
    private lateinit var txtEspacio: TextView
    private lateinit var txtHoras: TextView
    private lateinit var txtDias: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_curso)

        val rootView = findViewById<android.view.View>(android.R.id.content)

        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        cursoId = intent.getIntExtra("CURSO_ID", 1)
        cursoId = intent.getIntExtra("CURSO_ID", 0)

        inicializarVistas()

        if (cursoId > 0) {
            obtenerDetalleCurso(cursoId)
        } else {
            Toast.makeText(this, "ID de curso no válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inicializarVistas() {
        btnBack = findViewById(R.id.btnBack)
        imgCurso = findViewById(R.id.imgCurso)
        txtNombreCurso = findViewById(R.id.txtNombreCurso)
        txtCodigo = findViewById(R.id.txtCodigo)
        txtDescripcion = findViewById(R.id.txtDescripcion)
        txtEspacio = findViewById(R.id.txtEspacio)
        txtHoras = findViewById(R.id.txtHoras)
        txtDias = findViewById(R.id.txtDias)

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun obtenerDetalleCurso(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response =
                    RetrofitCliente.apiService.obtenerDetalleCurso(id)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val curso = response.body()

                        if (curso != null) {
                            txtNombreCurso.text = curso.nombre
                            txtCodigo.text = "Código: ${curso.codigo}"
                            txtDescripcion.text = curso.descripcion

                            // Estos tres campos deben coincidir
                            // con los nombres de tu modelo.
                            // Se completarán al revisar DetalleCursoResponse.kt.

                            Toast.makeText(
                                this@DetalleCurso,
                                "Detalles del curso cargados",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@DetalleCurso,
                                "La respuesta no contiene datos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@DetalleCurso,
                            "Error al cargar los detalles: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                val response = RetrofitCliente.apiService.obtenerDetalleCurso(id)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val curso = response.body()!!

                        txtNombreCurso.text = curso.nombre
                        txtCodigo.text = "Código: ${curso.codigo}"
                        txtDescripcion.text = curso.descripcion
                        txtEspacio.text = curso.espacio
                        txtHoras.text = curso.totalHoras
                        txtDias.text = curso.horarioAtencion

                        val baseUrl = "http://10.248.187.59/practicaMes/"
                        val urlCompleta = baseUrl + curso.urlImagenPrincipal

                        Glide.with(this@DetalleCurso)
                            .load(urlCompleta)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .error(android.R.drawable.stat_notify_error)
                            .into(imgCurso)
                    } else {
                        Toast.makeText(this@DetalleCurso, "No se pudo obtener el detalle del curso", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@DetalleCurso,
                        "Error de red: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    Toast.makeText(this@DetalleCurso, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}