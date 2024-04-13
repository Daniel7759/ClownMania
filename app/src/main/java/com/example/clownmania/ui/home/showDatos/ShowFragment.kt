package com.example.clownmania.ui.home.showDatos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.clownmania.R
import com.example.clownmania.data.Show
import com.example.clownmania.databinding.FragmentHomeBinding
import com.example.clownmania.databinding.FragmentShowBinding
import com.example.clownmania.ui.home.ShowAdapter
import com.example.clownmania.ui.home.reserva.ReservaFragment


class ShowFragment : Fragment() {

    private var _binding:  FragmentShowBinding? = null

    private val binding get() = _binding!!

override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    _binding = FragmentShowBinding.inflate(inflater, container, false)
    return binding.root
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinuar.setOnClickListener {
            // Crea una nueva instancia de ReservaFragment
            val reservaFragment = ReservaFragment()
            val fragmentManager = (binding.root.context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction()
                .add(R.id.layout_home, reservaFragment) // Reemplaza ShowFragment con ReservaFragment
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}