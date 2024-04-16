package com.example.clownmania.ui.usersregistrados

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clownmania.data.UserAuthenticate
import com.example.clownmania.databinding.ItemUsuariosRegistradosBinding

class UsuarioRegistradoAdapter(private val context: Context, private var userList: List<UserAuthenticate>) :
    RecyclerView.Adapter<UsuarioRegistradoAdapter.VHUser>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHUser {
        val binding = ItemUsuariosRegistradosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VHUser(binding)
    }

    override fun onBindViewHolder(holder: VHUser, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUsuarios(usuarios: List<UserAuthenticate>) {
        this.userList = usuarios
        notifyDataSetChanged()
    }

    inner class VHUser(private val binding: ItemUsuariosRegistradosBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserAuthenticate) {
            val nombreCompleto = "${user.nombre} ${user.apellido}"
            binding.tvUserName.text = nombreCompleto
            binding.tvUserPhone.text = user.celular
            binding.tvUserEmail.text = user.correo
            binding.tvUserRole.text = user.role.joinToString(", ") { it.nombreRol }
        }
    }
}
