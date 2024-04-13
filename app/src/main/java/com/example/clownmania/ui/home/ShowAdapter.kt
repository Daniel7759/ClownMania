package com.example.clownmania.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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
            binding.descripcionshow.text = show.description
            if (show.image.isEmpty()) {
                binding.imagenShow.setImageResource(R.drawable.clownmania)
            } else {
                Glide.with(binding.root.context)
                    .load(show.image)
                    .into(binding.imagenShow)
            }

            // Configura un OnClickListener para el botón de detalles
        binding.btnDetalle.setOnClickListener {
    // Reemplaza el HomeFragment con el ShowFragment
    val showFragment = ShowFragment()
    val fragmentManager = (binding.root.context as AppCompatActivity).supportFragmentManager
    fragmentManager.beginTransaction()
        .replace(R.id.layout_home, showFragment)
        .addToBackStack(null)
        .commit()
}
        }
    }
}