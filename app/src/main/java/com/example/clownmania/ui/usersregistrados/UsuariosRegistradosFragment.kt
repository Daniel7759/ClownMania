package com.example.clownmania.ui.usersregistrado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clownmania.R
import com.example.clownmania.UserUtils
import com.example.clownmania.data.retrofit.RetrofitInstace
import com.example.clownmania.databinding.LayoutUsuariosRegistradosBinding
import com.example.clownmania.databinding.ListarReservasBinding
import com.example.clownmania.ui.usersregistrados.UsuarioRegistradoAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuariosRegistradosFragment : Fragment() {

    private var _binding: LayoutUsuariosRegistradosBinding? = null

    private val binding get() = _binding!!
    private lateinit var usuariosAdapter: UsuarioRegistradoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = LayoutUsuariosRegistradosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usuariosAdapter= UsuarioRegistradoAdapter(requireContext(), emptyList())

        binding.recyclerUsuariosRegistrados.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = usuariosAdapter
        }

        lifecycleScope.launch {
            try {
                val usuariosRegistrados = withContext(Dispatchers.IO){
                    RetrofitInstace.apiservice.getUsuarios()}
                usuariosAdapter.setUsuarios(usuariosRegistrados)
            }catch (e: Exception){
                Toast.makeText(context, "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}