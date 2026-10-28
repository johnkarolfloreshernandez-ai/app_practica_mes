package com.example.app_practica_mes.Vista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.app_practica_mes.R
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_practica_mes.Adaptador.catalogoCursos
import com.example.app_practica_mes.Modelo.dataCarrera
import com.example.app_practica_mes.Modelo.dataCatalogoCursos
import com.example.app_practica_mes.Presentador.clsCarrerasPresentador

class MainActivity : AppCompatActivity() {

     lateinit var presenter: clsCarrerasPresentador
    private lateinit var spinnerCarrera: Spinner
    private lateinit var recycleCursos: RecyclerView
    private lateinit var adapterCatalogoCurso: catalogoCursos
    private lateinit var btnVerRegistros: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val intent = android.content.Intent(
            this,
            VerRegistrosActivity::class.java
        )




        spinnerCarrera = findViewById(R.id.spinner6)
        recycleCursos = findViewById(R.id.Rclr_View)
        btnVerRegistros = findViewById(R.id.btn_registros)

        recycleCursos.layoutManager = GridLayoutManager(this, 2)
        adapterCatalogoCurso = catalogoCursos(this, emptyList())
        recycleCursos.adapter = adapterCatalogoCurso

        presenter = clsCarrerasPresentador(this)
        configurarListeners()
        presenter.iniciar()
    }

    private fun configurarListeners() {
        spinnerCarrera.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.seleccionarCarrera(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnVerRegistros.setOnClickListener {
            Toast.makeText(this@MainActivity, "Ir a Ver Registros", Toast.LENGTH_SHORT).show()
        }
    }

    fun mostrarCarrera(carreras: List<dataCarrera>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, carreras)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCarrera.adapter = adapter
    }

    fun mostrarCursos(cursos: List<dataCatalogoCursos>) {
        adapterCatalogoCurso.actualizarLista(cursos)
    }


    fun mostrarMensajeExito(id: Int, nombre: String) {
        Toast.makeText(this, "ID: $id - Seleccionaste: $nombre", Toast.LENGTH_SHORT).show()
    }

    fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }
    fun seleccionarCurso(curso: dataCatalogoCursos) {
        val intent = Intent(this, DetalleCurso::class.java).apply {
            putExtra("CURSO_ID", curso.id)
        }
        startActivity(intent)
    }

    fun registrarCurso(curso: dataCatalogoCursos) {
        val intent = Intent(this, RegistroActivity::class.java).apply {
            putExtra("CURSO_ID", curso.id)
            putExtra("NOMBRE_CURSO", curso.nombre)
            putExtra("IMAGEN_CURSO", "http://10.248.187.59/practicaMes/" + curso.urlImagenPrincipal)
        }
        startActivity(intent)

}
}