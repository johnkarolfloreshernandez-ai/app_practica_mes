package com.example.app_practica_mes.Adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_practica_mes.Modelo.dataCatalogoCursos
import com.example.app_practica_mes.R
import com.example.app_practica_mes.Vista.MainActivity

class catalogoCursos(
    private val activity: MainActivity,
    private var listaCatalogoCursos: List<dataCatalogoCursos>
) : RecyclerView.Adapter<catalogoCursos.CursosViewHolder>() {

    class CursosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreCurso: TextView = itemView.findViewById(R.id.txtNombreCurso)
        val tvSalon: TextView = itemView.findViewById(R.id.txtSalon)
        val imagenCurso: ImageView = itemView.findViewById(R.id.imgCurso)
        val btnVerDetalle: Button = itemView.findViewById(R.id.btnDetalle)
        val btnRegistrarse: Button = itemView.findViewById(R.id.btnRegistro)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cursos, parent, false)
        return CursosViewHolder(view)
    }

    override fun onBindViewHolder(holder: CursosViewHolder, position: Int) {
        val curso = listaCatalogoCursos[position]

        holder.tvNombreCurso.text = curso.nombre
        holder.tvSalon.text = curso.espacio

        val baseUrl = "http://10.248.187.59/practicaMes/"
        val urlImagen = baseUrl + curso.urlImagenPrincipal

        Glide.with(holder.itemView.context)
            .load(urlImagen)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imagenCurso)

        holder.btnVerDetalle.setOnClickListener {
            activity.presenter.seleccionarCurso(curso)
        }

        holder.btnRegistrarse.setOnClickListener {
            activity.presenter.registrarCurso(curso)
        }
    }

    override fun getItemCount(): Int {
        return listaCatalogoCursos.size
    }

    fun actualizarLista(nuevaLista: List<dataCatalogoCursos>) {
        listaCatalogoCursos = nuevaLista
        notifyDataSetChanged()
    }
}