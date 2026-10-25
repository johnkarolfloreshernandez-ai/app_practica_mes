package com.example.app_practica_mes.Vista

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app_practica_mes.R
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_practica_mes.Modelo.RetrofitCliente
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetalleCurso : AppCompatActivity() {


    private var cursoId: Int = 0

    // Declaración de vistas
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
        setContentView(R.layout.activity_detalle_curso) // Asegúrate de que coincida con tu XML

        // Ajuste de insets
        val rootView = findViewById<android.view.View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recuperar el ID del curso enviado por el Intent (desde el catálogo)
        cursoId = intent.getIntExtra("CURSO_ID", 1)

        inicializarVistas()
        obtenerDetalleCurso(cursoId)
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

        btnBack.setOnClickListener { finish() }
    }

    private fun obtenerDetalleCurso(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitCliente.apiService.obtenerDetalleCurso(id)
                if (response.isSuccessful && response.body() != null) {
                    val curso = response.body()!!

                    withContext(Dispatchers.Main) {
                        // Pintar los datos en los TextViews
                        txtNombreCurso.text = curso.nombre
                        txtCodigo.text = "Código: ${curso.codigo}"
                        txtDescripcion.text = curso.descripcion
                        txtEspacio.text = curso.espacio
                        txtHoras.text = curso.totalHoras
                        txtDias.text = curso.horarioAtencion

                        // Nota: Para cargar la imagen desde la URL puedes usar librerías como Glide o Picasso:
                        // Glide.with(this@DetalleCursoActivity).load("TU_URL_BASE/${curso.urlImagenPrincipal}").into(imgCurso)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@DetalleCurso, "Error al cargar los detalles", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@DetalleCurso, "Error de red: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}