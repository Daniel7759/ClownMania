package com.example.clownmania.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clownmania.MainActivity
import com.example.clownmania.UserUtils
import com.example.clownmania.data.retrofit.ReservasGet
import com.example.clownmania.data.retrofit.RetrofitInstace
import com.example.clownmania.databinding.ListarNotificacionesBinding
import com.example.clownmania.databinding.ListarReservasBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationsFragment : Fragment() {

    private var _binding: ListarReservasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var reservaAdapter: ReservaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = ListarReservasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (UserUtils.role=="ADMIN"){
            (activity as MainActivity).updateToolbarTitle("Reservas de Usuarios")
        }else{
            (activity as MainActivity).updateToolbarTitle("Mis Reservas")
        }

        reservaAdapter = ReservaAdapter(requireContext(),emptyList())

        binding.recyclerReservas.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reservaAdapter
        }

        /*val reserva1 = ReservasGet(1, "Cochabamba", "2021-10-10", "10:00", "Casa de la Cultura", "Reserva de Payaso")
        val reserva2 = ReservasGet(2, "Cochabamba", "2021-10-10", "10:00", "Casa de la Cultura", "Reserva de Payaso")
        val reserva3 = ReservasGet(3, "Cochabamba", "2021-10-10", "10:00", "Casa de la Cultura", "Reserva de Payaso")
        reservaAdapter.setReservas(listOf(reserva1, reserva2, reserva3))*/

        lifecycleScope.launch {
            try {
                val reservas = withContext(Dispatchers.IO){RetrofitInstace.apiservice.getReservasByUserId(UserUtils.userId)}
                reservaAdapter.setReservas(reservas)
            }catch (e: Exception){
                Toast.makeText(context, "Error al cargar reservas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}