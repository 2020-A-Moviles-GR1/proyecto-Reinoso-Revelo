package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable
import com.beust.klaxon.*
import java.io.Serializable
import java.util.*

class Cita(
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var fechaAtencionCita: String?,
    var HoraAtencionCita: String?,
    var fechaProximaAtencionCita: String?,
    var estadoAtencionCita: String?,
    var mascota: Any?,
    var vacuna: ArrayList<Vacuna>? = null,
    var diagnostico: ArrayList<Diagnostico>? = null
) : Parcelable {

    var fechaCreacion: Date
    var fechaActualizacion: Date

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readSerializable(),
        parcel.readSerializable() as ArrayList<Vacuna>,
        parcel.readSerializable() as ArrayList<Diagnostico>
    ) {

    }

    init {
        fechaCreacion = Date(createdAt)
        fechaActualizacion = Date(updatedAt)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(createdAt)
        parcel.writeLong(updatedAt)
        parcel.writeInt(id)
        parcel.writeString(fechaAtencionCita)
        parcel.writeString(HoraAtencionCita)
        parcel.writeString(fechaProximaAtencionCita)
        parcel.writeString(estadoAtencionCita)
        parcel.writeSerializable(mascota as Serializable?)
        parcel.writeSerializable(vacuna)
        parcel.writeSerializable(diagnostico)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cita> {


        val myConverter = object : Converter {

            override fun canConvert(cls: Class<*>) = cls == Cita::class.java

            override fun toJson(value: Any): String {

                val cita = value as Cita
                var mascota: Any?

                if (cita.mascota is Int) {
                    mascota = cita.mascota
                } else if (cita.mascota is Mascota) {
                    mascota = Klaxon().toJsonString(cita.mascota as Mascota)
                } else {
                    throw Error("error")
                }

                return """
                { 
                "createdAt": ${cita.createdAt},
                "updatedAt": ${cita.updatedAt},
                "id": ${cita.id},
                "fechaAtencionCita": "${cita.fechaAtencionCita}",
                "HoraAtencionCita": "${cita.HoraAtencionCita}",
                "fechaProximaAtencionCita": "${cita.fechaProximaAtencionCita}",
                "estadoAtencionCita": "${cita.estadoAtencionCita}",
                "mascota": "${mascota}",
                "vacuna": ${Klaxon().toJsonString(cita.vacuna as List<*>)},
                "diagnostico": ${Klaxon().toJsonString(cita.diagnostico as List<*>)} 
                }
                """.trimMargin()
            }

            override fun fromJson(jv: JsonValue): Cita {

                if (jv.obj?.get("mascota") is JsonObject) {

                    return Cita(
                        jv.obj?.get("createdAt") as Long,
                        jv.obj?.get("updatedAt") as Long,
                        jv.objInt("id"),
                        jv.objString("fechaAtencionCita"),
                        jv.objString("HoraAtencionCita"),
                        jv.objString("fechaProximaAtencionCita"),
                        jv.objString("estadoAtencionCita"),
                        Klaxon().parseFromJsonObject<Mascota>(jv.obj?.get("mascota") as JsonObject),
                        Klaxon().parseFromJsonArray<Vacuna>(jv.obj?.get("vacuna") as JsonArray<*>) as ArrayList<Vacuna>,
                        Klaxon().parseFromJsonArray<Diagnostico>(jv.obj?.get("diagnostico") as JsonArray<*>) as ArrayList<Diagnostico>
                    )
                } else if (jv.obj?.get("mascota") is Int) {
                    return Cita(
                        jv.obj?.get("createdAt") as Long,
                        jv.obj?.get("updatedAt") as Long,
                        jv.objInt("id"),
                        jv.objString("fechaAtencionCita"),
                        jv.objString("HoraAtencionCita"),
                        jv.objString("fechaProximaAtencionCita"),
                        jv.objString("estadoAtencionCita"),
                        jv.objInt("mascota"),
                        Klaxon().parseFromJsonArray<Vacuna>(jv.obj?.get("vacuna") as JsonArray<*>) as ArrayList<Vacuna>,
                        Klaxon().parseFromJsonArray<Diagnostico>(jv.obj?.get("diagnostico") as JsonArray<*>) as ArrayList<Diagnostico>
                    )
                } else {
                    throw Error("error")
                }
            }
        }

        override fun createFromParcel(parcel: Parcel): Cita {
            return Cita(parcel)
        }

        override fun newArray(size: Int): Array<Cita?> {
            return arrayOfNulls(size)
        }
    }
}