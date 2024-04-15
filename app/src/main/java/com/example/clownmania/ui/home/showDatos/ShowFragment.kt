package com.example.clownmania.ui.home.showDatos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.clownmania.R
import com.example.clownmania.data.Show
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

        val showId = arguments?.getInt("showId")
        val showTitle = arguments?.getString("showTitle")
        val showDescription = arguments?.getString("showDescription")
        val showImage = arguments?.getString("showImage")
        val showPrice = arguments?.getDouble("showPrice")
        val showDuration = arguments?.getInt("showDuration")
        val showEncargado = arguments?.getString("showEncargado")

        binding.txtShowTitle.text = showTitle
        binding.txtShowDescription.text = showDescription
        binding.ivShowImage.setImageResource(R.drawable.clownmania)
        binding.txtShowPrecio.text = "PEN "+showPrice.toString()
        binding.txtAnimadorName.text = showEncargado

        binding.btnContinuar.setOnClickListener {
            // Crea una nueva instancia de ReservaFragment
            val reservaFragment = ReservaFragment()
            val bundle = Bundle()
            bundle.putInt("showId", showId!!)
            reservaFragment.arguments = bundle
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