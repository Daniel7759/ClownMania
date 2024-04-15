package com.example.clownmania.ui.home.reserva

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.clownmania.R
import com.example.clownmania.UserUtils
import com.example.clownmania.data.Evento
import com.example.clownmania.data.Reserva
import com.example.clownmania.data.Usuario
import com.example.clownmania.data.retrofit.RetrofitInstace
import com.example.clownmania.databinding.FragmentReservaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class ReservaFragment : Fragment() {

    private var _binding: FragmentReservaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReservaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtxtFecha.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // your format
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.edtxtFecha.setText(sdf.format(calendar.time))
            }

            DatePickerDialog(requireContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.edtxtHora.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                val myFormat = "HH:mm" // your format
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                binding.edtxtHora.setText(sdf.format(calendar.time))
            }

            TimePickerDialog(requireContext(), timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true).show()
        }


        binding.btnReservar.setOnClickListener {
            val ciudad = binding.tiedtxtCiudad.text.toString().trim()
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val fechaString = binding.edtxtFecha.text.toString()
//        val fecha = LocalDate.parse(fechaString, formatter)
//        val formatter2 = DateTimeFormatter.ofPattern("HH:mm")
            val horaString = binding.edtxtHora.text.toString()
//        val hora = LocalTime.parse(horaString, formatter2)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val fecha = LocalDate.parse(fechaString, formatter)
            val formatter2 = DateTimeFormatter.ofPattern("HH:mm")
            val hora = LocalTime.parse(horaString, formatter2)
            val ubicacion = binding.tiedtxtUbicacion.text.toString().trim()
            val observacion = binding.tiedtxtObsevacion.text.toString().trim()
            val eventoId = arguments?.getInt("showId")
            val evento = Evento(eventoId!!)
            val userId = UserUtils.userId
            val usuario = Usuario(userId)
            Toast.makeText(requireContext(), "Reservando... "+userId+" "+eventoId, Toast.LENGTH_SHORT).show()
            val reserva = Reserva(ciudad, fechaString, horaString, ubicacion, observacion, evento, usuario)
            /*AlertDialog.Builder(requireContext())
                .setMessage(fechaString+""+horaString)
                .setPositiveButton("Si") { dialog, _ ->
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                .show()*/

            RetrofitInstace.apiservice.postReserva(reserva).enqueue(object :
                    Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            val mensaje = response.body().toString()
                            val alertDialogBuilder = AlertDialog.Builder(requireContext())
                            alertDialogBuilder
                                .setMessage(mensaje)
                                .setCancelable(true)
                                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                            val alertDialog = alertDialogBuilder.create()
                            alertDialog.show()
                        } else {
                            val mensajeError = response.errorBody()?.string()
                            val alertDialogBuilder = AlertDialog.Builder(requireContext())
                            alertDialogBuilder
                                .setMessage(mensajeError ?: "Error desconocido")
                                .setCancelable(true)
                                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                            val alertDialog = alertDialogBuilder.create()
                            alertDialog.show()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        // Manejar errores de comunicación
                        Toast.makeText(requireContext(), "Reserva registrada", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}