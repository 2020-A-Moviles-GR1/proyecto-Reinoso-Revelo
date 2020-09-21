package com.example.proyecto_veterinaria

import java.util.*

class Mascota (
    var createdAt:Long,
    var updatedAt:Long,
    var id:Int,
    var nombreMascota:String,
    var pesoMascota:String,
    var edadMascota:String,
    var razaMascota:String,
    var especieMascota:String,
    var fechaNacimientoMascota:String,
    var usuario:Int,
    var cita:ArrayList<Cita> ? = null

) {
    var fechaCreacion : Date
    var fechaActualizacion : Date
    init {
        fechaCreacion= Date(createdAt)
        fechaActualizacion= Date(updatedAt)
    }
}