package com.example.app_practica_mes.Vista

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.app_practica_mes.Presentador.clsRegistroPresentador
import com.example.app_practica_mes.R

class RegistroActivity : AppCompatActivity() {
    private lateinit var presenter: clsRegistroPresentador

    // Vistas asociadas al XML
    private lateinit var btnBack: ImageView
    private lateinit var imgCurso: ImageView
    private lateinit var txtNombreCurso: TextView
    private lateinit var txtMatricula: EditText
    private lateinit var txtFechaRegistro: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var btnConfirmar: Button
    private lateinit var btnCancelar: Button

    // Variable para almacenar el ID del curso recibido
    private var cursoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        // Ajuste de insets utilizando la vista raíz
        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Enlace de vistas con IDs del XML
        btnBack = findViewById(R.id.btnBack)
        imgCurso = findViewById(R.id.imgCurso)
        txtNombreCurso = findViewById(R.id.txtNombreCurso)
        txtMatricula = findViewById(R.id.txtMatricula)
        txtFechaRegistro = findViewById(R.id.txtFechaRegistro)
        txtCorreo = findViewById(R.id.txtCorreo)
        btnConfirmar = findViewById(R.id.btnConfirmar)
        btnCancelar = findViewById(R.id.btnCancelar)

        // Inicializar Presentador
        presenter = clsRegistroPresentador(this)

        cursoId = intent.getIntExtra("CURSO_ID", 0)
        val nombreCurso = intent.getStringExtra("NOMBRE_CURSO") ?: "CURSO SELECCIONADO"
        val urlImagen = intent.getStringExtra("IMAGEN_CURSO") ?: ""

        txtNombreCurso.text = nombreCurso
        presenter.cargarDatosCurso(nombreCurso, urlImagen)

        // Listeners
        btnConfirmar.setOnClickListener(this::ejecutarRegistroCurso)
        btnCancelar.setOnClickListener { finish() }
        btnBack.setOnClickListener { finish() }
        txtFechaRegistro.setOnClickListener { mostrarDatePicker() }
    }

    fun ejecutarRegistroCurso(v: View) {
        val matricula = txtMatricula.text.toString().trim()
        val fechaRegistro = txtFechaRegistro.text.toString().trim()

        presenter.realizarRegistro(cursoId, matricula, fechaRegistro)
    }

    fun registroExitoso(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        finish()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    fun mostrarImagenCurso(urlImagen: String) {
        Glide.with(this)
            .load(urlImagen)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(imgCurso)
    }
    private fun mostrarDatePicker() {
        val calendario = java.util.Calendar.getInstance()
        val anio = calendario.get(java.util.Calendar.YEAR)
        val mes = calendario.get(java.util.Calendar.MONTH)
        val dia = calendario.get(java.util.Calendar.DAY_OF_MONTH)

        val datePicker = android.app.DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                // Formato YYYY-MM-DD adecuado para columnas DATE en MySQL
                val fechaSeleccionada = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
                txtFechaRegistro.setText(fechaSeleccionada)
            },
            anio,
            mes,
            dia
        )
        datePicker.show()
    }
}