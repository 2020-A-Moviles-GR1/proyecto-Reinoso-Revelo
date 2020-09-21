package com.example.proyecto_veterinaria

import java.util.*

class Usuario (
    var createdAt:Long,
    var updatedAt:Long,
    var id:Int,
    var cedula: String,
    var nombre: String,
    var apellido: String,
    var telefono: String,
    var correo: String,
    var experiencia: String,
    var contrasenia: String,
    var usuario: String,
    var rol: String,
    var mascotas :ArrayList<Mascota> ? = null
){

    var fechaCreacion : Date
    var fechaActualizacion : Date
    init {
        fechaCreacion= Date(createdAt)
        fechaActualizacion= Date(updatedAt)
    }


}