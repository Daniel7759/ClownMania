package com.example.clownmania.ui.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.clownmania.LoginActivity
import com.example.clownmania.MainActivity
import com.example.clownmania.R
import com.example.clownmania.UserUtils
import com.example.clownmania.data.Show
import com.example.clownmania.data.UserAuthenticate
import com.example.clownmania.data.retrofit.RetrofitInstace
import com.example.clownmania.databinding.FragmentHomeBinding
import com.example.clownmania.ui.home.showDatos.ShowFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var showAdapter: ShowAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*// Inicializa el Toolbar
        val toolbar = binding.toolbar*/
        (activity as MainActivity).updateToolbarTitle("Clownmania")

        // Habilita el menú de opciones en el Fragment
        setHasOptionsMenu(true)

        showAdapter = ShowAdapter(emptyList(), object : ShowClick {
            override fun onShowClicked(show: Show) {
                //aca esta dando error al abrir otro fragmento
                /*val showFragment = ShowFragment()
                parentFragmentManager.beginTransaction()
                    .add(R.id.layout_home, showFragment)
                    .addToBackStack(null)
                    .commit()*/

            }
        })



        binding.recyclerShown.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = showAdapter
        }

        /*val shows = List(10) {
            Show(
                it,
                "Show insano Clownmania $it",
                "Description insana del show bachata insana $it",
                ""
            )
        }*/
        lifecycleScope.launch {
            try {
                val shows: List<Show> = withContext(Dispatchers.IO){RetrofitInstace.apiservice.getShows()}
                showAdapter.setShows(shows)
                binding.recyclerShown.visibility = View.VISIBLE
            }catch (e: Exception){
                Toast.makeText(requireContext(), "Error al cargar los eventos", Toast.LENGTH_SHORT).show()
            }
        }

//        showAdapter.setShows(shows)
        //obtener los sharedPreferences en mi fragmento
        /*val sharedPreferences = requireActivity().getSharedPreferences("Credenciales", Context.MODE_PRIVATE)
        val correo = sharedPreferences.getString("email", "")
        if (correo != null && correo.isNotEmpty()) {
            autenticarUser(correo)
        }*/

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.close_session, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Maneja los clicks en los items del menú de opciones
        when (item.itemId) {
            R.id.action_settings -> {
                // Aquí puedes manejar el cierre de sesión
                FirebaseAuth.getInstance().signOut()

                // Redirige al usuario a la pantalla de inicio de sesión
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish() // Finaliza la actividad actual

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }*/

}