
package com.example.app_practica_mes.Adaptador

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app_practica_mes.Modelo.RegistroCursoResponse
import com.example.app_practica_mes.R



class AdaptadorRegistros(
    private val listaCursos: ArrayList<RegistroCursoResponse>
) : RecyclerView.Adapter<AdaptadorRegistros.ViewHolder>() {

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val imgCurso: ImageView =
            itemView.findViewById(R.id.imgCursoRegistro)

        val txtNombre: TextView =
            itemView.findViewById(R.id.txtNombreCursoRegistro)

        val txtCodigo: TextView =
            itemView.findViewById(R.id.txtCodigoCursoRegistro)

        val txtInscritos: TextView =
            itemView.findViewById(R.id.txtInscritosCurso)

        val txtCupo: TextView =
            itemView.findViewById(R.id.txtCupoCurso)

        val barraInscritos: ProgressBar =
            itemView.findViewById(R.id.barraInscritosCurso)

        val txtPorcentaje: TextView =
            itemView.findViewById(R.id.txtPorcentajeCurso)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_registro_curso, parent, false)

        return ViewHolder(vista)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val curso = listaCursos[position]

        holder.txtNombre.text = curso.nombre
        holder.txtCodigo.text = "Código: ${curso.codigo}"

        holder.txtInscritos.text = "${curso.inscritos} inscritos"
        holder.txtCupo.text = "${curso.cupo} total"

        val porcentaje = if (curso.cupo > 0) {
            ((curso.inscritos.toDouble() / curso.cupo) * 100)
                .toInt()
                .coerceIn(0, 100)
        } else {
            0
        }

        holder.barraInscritos.max = 100
        holder.barraInscritos.progress = porcentaje

        val colorBarra = if (porcentaje >= 70) {
            Color.rgb(22, 141, 88)
        } else {
            Color.rgb(21, 84, 154)
        }

        holder.barraInscritos.progressTintList =
            ColorStateList.valueOf(colorBarra)

        // Preparar la ruta de la imagen
        val rutaImagen = curso.urlImagenPrincipal
            .replace("\\", "/")
            .trimStart('/')

        if (rutaImagen.isNotBlank()) {

            // Utilizar la misma IP de la API
            val urlImagen =
                "http://192.168.1.187/app_practica_mes_api/$rutaImagen"

            Glide.with(holder.itemView.context)
                .load(urlImagen)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .centerCrop()
                .into(holder.imgCurso)

        } else {

            Glide.with(holder.itemView.context)
                .clear(holder.imgCurso)

            holder.imgCurso.setImageResource(
                android.R.drawable.ic_menu_gallery
            )
        }
    }

    override fun getItemCount(): Int = listaCursos.size

    fun actualizarLista(
        nuevaLista: List<RegistroCursoResponse>
    ) {
        listaCursos.clear()
        listaCursos.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
