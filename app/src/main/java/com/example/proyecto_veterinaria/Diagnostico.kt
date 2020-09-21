package com.example.proyecto_veterinaria

import java.util.*

class Diagnostico(

    var createdAt:Long,
    var updatedAt:Long,
    var id:Int,
    var fechaAtencionDiagnostico:String,
    var motivoConsultaDiagnostico:String,
    var diagnosticoAtencion:String,
    var cita:Int

) {

    var fechaCreacion : Date
    var fechaActualizacion : Date
    init {
        fechaCreacion= Date(createdAt)
        fechaActualizacion= Date(updatedAt)
    }
}