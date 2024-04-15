package com.example.clownmania.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clownmania.R
import com.example.clownmania.data.Show
import com.example.clownmania.databinding.ItemsShownBinding
import com.example.clownmania.ui.home.showDatos.ShowFragment

class ShowAdapter(private var showList: List<Show>, private val showClick: ShowClick) :
    RecyclerView.Adapter<ShowAdapter.VHShow>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHShow {
        val binding= ItemsShownBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VHShow(binding)
    }

    override fun onBindViewHolder(holder: VHShow, position: Int) {
        val show = showList[position]
        holder.bind(show)
    }

    override fun getItemCount() = showList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setShows(shows: List<Show>) {
        this.showList = shows
        notifyDataSetChanged()
    }

    inner class VHShow(private val binding: ItemsShownBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(show: Show) {
            binding.txtNameShow.text = show.nombre
            binding.descripcionshow.text = show.descripcion
            if (show.imagenUrl.isNotEmpty()) {
                binding.imagenShow.setImageResource(R.drawable.clownmania)
            } /*else {
                Glide.with(binding.root.context)
                    .load(show.imagenUrl)
                    .into(binding.imagenShow)
            }*/

            // Configura un OnClickListener para el botón de detalles
            binding.btnDetalle.setOnClickListener {
                // Reemplaza el HomeFragment con el ShowFragment
                val showFragment = ShowFragment()
                val fragmentManager = (binding.root.context as AppCompatActivity).supportFragmentManager
                val bundle = Bundle()
                bundle.putInt("showId", show.eventoId)
                bundle.putString("showTitle", show.nombre)
                bundle.putString("showDescription", show.descripcion)
                bundle.putString("showImage", show.imagenUrl)
                bundle.putDouble("showPrice", show.precio)
                bundle.putInt("showDuration", show.duracion)
                val encargado = show.encargados.elementAt(0)
                val nombreEncargado = encargado.nombre + " " + encargado.apellido
                bundle.putString("showEncargado", nombreEncargado)
                showFragment.arguments = bundle
                fragmentManager.beginTransaction()
                    .add(R.id.layout_home, showFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}