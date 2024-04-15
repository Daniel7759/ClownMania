package com.example.clownmania

object UserUtils {

    //guardar los datos del usuario que inicia sesion
    var userId: Int = 0
        private set
    var nombre: String = ""
        private set
    var apellido: String = ""
        private set
    var correo: String = ""
        private set
    var celular: String = ""
        private set
    var role: String = "user"
        private set


    fun setUserId(newUserId: Int) {
        userId = newUserId
    }

    fun setNombre(newNombre: String) {
        nombre = newNombre
    }

    fun setApellido(newApellido: String) {
        apellido = newApellido
    }

    fun setCorreo(newCorreo: String) {
        correo = newCorreo
    }

    fun setCelular(newCelular: String) {
        celular = newCelular
    }

    fun setRole(newRole: String) {
        role = newRole
    }
}