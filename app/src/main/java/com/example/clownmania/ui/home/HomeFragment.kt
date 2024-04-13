package com.example.clownmania.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.clownmania.R
import com.example.clownmania.data.Show
import com.example.clownmania.databinding.FragmentHomeBinding
import com.example.clownmania.ui.home.showDatos.ShowFragment

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

        val shows = List(10) {
            Show(
                it,
                "Title $it",
                "Description $it",
                ""
            )
        }

        showAdapter.setShows(shows)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}