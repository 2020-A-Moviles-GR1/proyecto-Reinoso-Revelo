package com.example.proyecto_veterinaria

import java.util.*

class Cita (
        var createdAt:Long,
        var updatedAt:Long,
        var id:Int,
        var fechaAtencionCita:String,
        var HoraAtencionCita:String,
        var fechaProximaAtencionCita:String,
        var estadoAtencionCita:String,
        var mascota:Int,
        var vacuna : ArrayList<Vacuna>? = null,
        var diagnostico :ArrayList<Diagnostico> ? = null
) {

        var fechaCreacion : Date
        var fechaActualizacion : Date
        init {
                fechaCreacion= Date(createdAt)
                fechaActualizacion= Date(updatedAt)
        }
}