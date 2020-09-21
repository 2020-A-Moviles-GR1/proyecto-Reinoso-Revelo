package com.example.proyecto_veterinaria

import java.util.*

class Vacuna (
    var createdAt:Long,
    var updatedAt:Long,
    var id:Int,
    var fechaVacuna:String,
    var tipoVacuna:String,
    var numDosisVacuna:String,
    var cita:Int
){

    var fechaCreacion : Date
    var fechaActualizacion : Date
    init {
        fechaCreacion= Date(createdAt)
        fechaActualizacion= Date(updatedAt)
    }
}