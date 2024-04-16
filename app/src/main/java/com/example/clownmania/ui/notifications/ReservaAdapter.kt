package com.example.clownmania.ui.notifications

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.clownmania.R
import com.example.clownmania.data.Reserva
import com.example.clownmania.data.retrofit.ReservasGet
import com.example.clownmania.databinding.ItemsReservaBinding

class ReservaAdapter(private val context: Context ,private var reservaList: List<ReservasGet>) :
    RecyclerView.Adapter<ReservaAdapter.VHReserva>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHReserva {
        val binding= ItemsReservaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VHReserva(binding)
    }

    override fun onBindViewHolder(holder: VHReserva, position: Int) {
        val reserva = reservaList[position]
        holder.bind(reserva)
    }

    override fun getItemCount() = reservaList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setReservas(reservas: List<ReservasGet>) {
        this.reservaList = reservas
        notifyDataSetChanged()
    }

    inner class VHReserva(private val binding: ItemsReservaBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reserva: ReservasGet) {
            val nombreCompleto = reserva.usuario.nombre+" "+reserva.usuario.apellido
            binding.txtReserva.text = nombreCompleto
            binding.txtFecha.text = reserva.fecha
            binding.txtHora.text = reserva.hora
            binding.txtShow.text = reserva.evento.nombre
            binding.txtUbicacion.text = "Ubicación: ${reserva.ubicacion}"


            val sharedPreference = context.getSharedPreferences("Credenciales", Context.MODE_PRIVATE)
            val role = sharedPreference.getString("role", "USER")
            if (role == "ADMIN") {
                binding.btnPagar.isVisible = false
            }
            // Configura un OnClickListener para el botón de detalles
            binding.btnPagar.setOnClickListener {
                Toast.makeText(binding.root.context, "Pago en Proceso", Toast.LENGTH_SHORT).show()
                // Reemplaza el HomeFragment con el ReservaFragment
                /*val notificationsFragment = NotificationsFragment()
                val fragmentManager = (binding.root.context as AppCompatActivity).supportFragmentManager
                fragmentManager.beginTransaction()
                    .add(R.id.layout_home, notificationsFragment)
                    .addToBackStack(null)
                    .commit()*/
            }
        }
    }
}