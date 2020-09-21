package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable
import com.beust.klaxon.Converter
import com.beust.klaxon.JsonObject
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import java.io.Serializable
import java.util.*

class Vacuna(
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var fechaVacuna: String?,
    var tipoVacuna: String?,
    var numDosisVacuna: String?,
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
        parcel.readSerializable() as Any
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
        parcel.writeString(fechaVacuna)
        parcel.writeString(tipoVacuna)
        parcel.writeString(numDosisVacuna)
        parcel.writeSerializable(cita as Serializable?)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vacuna> {

        val myConverter = object : Converter {

            override fun canConvert(cls: Class<*>) = cls == Vacuna::class.java

            override fun toJson(value: Any): String {

                val vacuna = value as Vacuna
                var cita: Any?
                if (vacuna.cita is Int) {
                    cita = vacuna.cita
                } else if (vacuna.cita is Cita) {
                    cita = Klaxon().toJsonString(vacuna.cita as Cita)
                } else {
                    throw Error("error")
                }
                return """
                  { 
                    "createdAt": ${vacuna.createdAt},
                    "updatedAt": ${vacuna.updatedAt},
                    "id": ${vacuna.id},
                    "fechaVacuna": "${vacuna.fechaVacuna}",
                    "tipoVacuna": "${vacuna.tipoVacuna}",
                    "numDosisVacuna": "${vacuna.numDosisVacuna}",
                    "cita": ${cita}
                   }
                """.trimMargin()
            }

            override fun fromJson(jv: JsonValue): Vacuna {

                if (jv.obj?.get("cita") is JsonObject) {

                    return Vacuna(
                        jv.obj?.get("createdAt") as Long,
                        jv.obj?.get("updatedAt") as Long,
                        jv.objInt("id"),
                        jv.objString("fechaVacuna"),
                        jv.objString("tipoVacuna"),
                        jv.objString("numDosisVacuna"),
                        Klaxon().parseFromJsonObject<Cita>(jv.obj?.get("cita") as JsonObject)
                    )
                } else if (jv.obj?.get("cita") is Int) {
                    return Vacuna(
                        jv.obj?.get("createdAt") as Long,
                        jv.obj?.get("updatedAt") as Long,
                        jv.objInt("id"),
                        jv.objString("fechaVacuna"),
                        jv.objString("tipoVacuna"),
                        jv.objString("numDosisVacuna"),
                        jv.objInt("cita")
                    )
                } else {
                    throw Error("error")
                }
            }
        }

        override fun createFromParcel(parcel: Parcel): Vacuna {
            return Vacuna(parcel)
        }

        override fun newArray(size: Int): Array<Vacuna?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Vacuna (id=$id," +
                " createdAt=$createdAt," +
                " updatedAt=$updatedAt," +
                " fechaVacuna='$fechaVacuna'," +
                " tipoVacuna='$tipoVacuna'," +
                " numDosisVacuna='$numDosisVacuna'," +
                " cita=$cita," +
                " fechaCreacion=$fechaCreacion," +
                " fechaActualizacion=$fechaActualizacion)"
    }

}