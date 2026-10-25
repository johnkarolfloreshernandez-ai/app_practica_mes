package com.example.app_practica_mes.Vista

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app_practica_mes.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val intent = Intent(this, RegistroActivity::class.java).apply {
            putExtra("CURSO_ID", 1)
            putExtra("NOMBRE_CURSO", "PROGRAMACIÓN AVANZADA")

            // Ruta exacta tomada de tu navegador con tu IP actual:
            putExtra("IMAGEN_CURSO", "http://10.129.214.59/practicaMes/upload/cursos/principales/prog1.png")
        }

        startActivity(intent)
        finish()
    }

}
