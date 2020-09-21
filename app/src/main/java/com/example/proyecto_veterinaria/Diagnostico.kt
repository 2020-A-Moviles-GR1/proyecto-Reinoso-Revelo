package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable
import com.beust.klaxon.Converter
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import java.io.Serializable
import java.util.*

class Diagnostico(

    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var fechaAtencionDiagnostico: String?,
    var motivoConsultaDiagnostico: String?,
    var diagnosticoAtencion: String?,
    var cita: Any?

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
        parcel.readSerializable()
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
        parcel.writeString(fechaAtencionDiagnostico)
        parcel.writeString(motivoConsultaDiagnostico)
        parcel.writeString(diagnosticoAtencion)
        parcel.writeSerializable(cita as Serializable?)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Diagnostico> {

        val myConverter = object : Converter {

            override fun canConvert(cls: Class<*>) = cls == Diagnostico::class.java

            override fun toJson(value: Any): String {

                val diagnostico = value as Diagnostico
                var cita: Any?

                if (diagnostico.cita is Int) {
                    cita = diagnostico.cita
                } else if (diagnostico.cita is Cita) {
                    cita = Klaxon().toJsonString(diagnostico.cita as Cita)
                } else {
                    throw Error("error")
                }
                return """
                  { 
                    "createdAt": ${diagnostico.createdAt},
                    "updatedAt": ${diagnostico.updatedAt},
                    "id": ${diagnostico.id},
                    "fechaAtencionDiagnostico": "${diagnostico.fechaAtencionDiagnostico}",
                    "motivoConsultaDiagnostico": "${diagnostico.motivoConsultaDiagnostico}",
                    "diagnosticoAtencion": "${diagnostico.diagnosticoAtencion}",
                    "cita": ${cita}
                   }
                """.trimMargin()
            }

            override fun fromJson(jv: JsonValue): Diagnostico {

                if (jv.obj?.get("cita") is JsonObject) {

                    return Diagnostico(
                        jv.obj?.get("createdAt") as Long,
                        jv.obj?.get("updatedAt") as Long,
                        jv.objInt("id"),
                        jv.objString("fechaAtencionDiagnostico"),
                        jv.objString("motivoConsultaDiagnostico"),
                        jv.objString("diagnosticoAtencion"),
                        Klaxon().parseFromJsonObject<Cita>(jv.obj?.get("cita") as JsonObject)
                    )
                } else if (jv.obj?.get("cita") is Int) {
                    return Diagnostico(
                        jv.obj?.get("createdAt") as Long,
                        jv.obj?.get("updatedAt") as Long,
                        jv.objInt("id"),
                        jv.objString("fechaAtencionDiagnostico"),
                        jv.objString("motivoConsultaDiagnostico"),
                        jv.objString("diagnosticoAtencion"),
                        jv.objInt("cita")
                    )
                } else {
                    throw Error("error")
                }
            }
        }

        override fun createFromParcel(parcel: Parcel): Diagnostico {
            return Diagnostico(parcel)
        }

        override fun newArray(size: Int): Array<Diagnostico?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Diagnostico (id=$id," +
                " createdAt=$createdAt," +
                " updatedAt=$updatedAt," +
                " fechaAtencionDiagnostico='$fechaAtencionDiagnostico'," +
                " motivoConsultaDiagnostico='$motivoConsultaDiagnostico'," +
                " diagnosticoAtencion='$diagnosticoAtencion'," +
                " cita=$cita," +
                " fechaCreacion=$fechaCreacion," +
                " fechaActualizacion=$fechaActualizacion)"
    }
}