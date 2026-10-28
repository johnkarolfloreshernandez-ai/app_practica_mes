
package com.example.app_practica_mes.Vista

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_practica_mes.Adaptador.AdaptadorRegistros
import com.example.app_practica_mes.Modelo.RegistroCursoResponse
import com.example.app_practica_mes.Presentador.clsVerRegistrosPresentador
import com.example.app_practica_mes.R

class VerRegistrosActivity : AppCompatActivity() {

    private lateinit var presentador: clsVerRegistrosPresentador
    private lateinit var adaptador: AdaptadorRegistros

    private lateinit var spCarreras: Spinner
    private lateinit var txtTotal: TextView
    private lateinit var txtMensaje: TextView
    private lateinit var progreso: ProgressBar
    private lateinit var rvCursos: RecyclerView
    private lateinit var btnRegresar: Button
    private lateinit var btnCatalogo: Button

    private val listaCursos = ArrayList<RegistroCursoResponse>()
    private val listaFiltrada = ArrayList<RegistroCursoResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_registros)

        // Inicializar componentes
        spCarreras = findViewById(R.id.spCarrerasRegistros)
        txtTotal = findViewById(R.id.txtTotalRegistros)
        txtMensaje = findViewById(R.id.txtMensajeRegistros)
        progreso = findViewById(R.id.progresoRegistros)
        rvCursos = findViewById(R.id.rvCursosRegistros)

        btnRegresar = findViewById(R.id.btnRegresarRegistros)
        btnCatalogo = findViewById(R.id.btnVolverCatalogoRegistros)

        // Configurar RecyclerView
        adaptador = AdaptadorRegistros(ArrayList())

        rvCursos.layoutManager = LinearLayoutManager(this)
        rvCursos.adapter = adaptador

        // Inicializar presentador
        presentador = clsVerRegistrosPresentador(this)

        // Botones de navegación
        btnRegresar.setOnClickListener {
            finish()
        }

        btnCatalogo.setOnClickListener {
            finish()
        }

        // Filtro por carrera
        spCarreras.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    filtrarCursos()
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {
                    // No hay selección
                }
            }

        // Cargar los cursos
        mostrarCargando(true)
        presentador.cargarRegistros()
    }

    fun mostrarCargando(mostrar: Boolean) {
        progreso.visibility = if (mostrar) {
            View.VISIBLE
        } else {
            View.GONE
        }

        if (mostrar) {
            txtMensaje.visibility = View.GONE
        }
    }

    fun mostrarCursos(cursos: List<RegistroCursoResponse>) {

        mostrarCargando(false)

        listaCursos.clear()
        listaCursos.addAll(cursos)

        // Crear las opciones del filtro
        val carreras = ArrayList<String>()
        carreras.add("Todas las carreras")

        for (curso in listaCursos) {
            val nombreCarrera = curso.nombreCarrera.trim()

            if (
                nombreCarrera.isNotBlank() &&
                !carreras.contains(nombreCarrera)
            ) {
                carreras.add(nombreCarrera)
            }
        }

        // Configurar Spinner
        val adaptadorCarreras = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            carreras
        )

        adaptadorCarreras.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spCarreras.adapter = adaptadorCarreras

        // Mostrar todos los cursos al inicio
        filtrarCursos()
    }

    private fun filtrarCursos() {

        listaFiltrada.clear()

        val posicionSeleccionada = spCarreras.selectedItemPosition

        if (
            spCarreras.selectedItem == null ||
            posicionSeleccionada == 0
        ) {
            listaFiltrada.addAll(listaCursos)
        } else {

            val carreraSeleccionada =
                spCarreras.selectedItem.toString()

            for (curso in listaCursos) {
                if (
                    curso.nombreCarrera.trim() ==
                    carreraSeleccionada
                ) {
                    listaFiltrada.add(curso)
                }
            }
        }

        // Actualizar lista
        adaptador.actualizarLista(ArrayList(listaFiltrada))

        // Calcular el total de inscritos
        val totalInscritos = listaFiltrada.sumOf {
            it.inscritos
        }

        txtTotal.text = "Total: $totalInscritos"

        // Mostrar u ocultar mensaje
        if (listaFiltrada.isEmpty()) {

            txtMensaje.text = if (listaCursos.isEmpty()) {
                "No hay cursos registrados en el servidor."
            } else {
                "No hay cursos para la carrera seleccionada."
            }

            txtMensaje.visibility = View.VISIBLE

        } else {
            txtMensaje.visibility = View.GONE
        }
    }

    fun mostrarError(mensaje: String) {

        mostrarCargando(false)

        txtMensaje.text = mensaje
        txtMensaje.visibility = View.VISIBLE

        txtTotal.text = "Total: 0"

        listaCursos.clear()
        listaFiltrada.clear()

        adaptador.actualizarLista(emptyList())
    }
}
