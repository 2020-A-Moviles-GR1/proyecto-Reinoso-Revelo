package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable
import com.beust.klaxon.*
import java.io.Serializable
import java.util.*

class Mascota(
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var nombreMascota: String?,
    var pesoMascota: String?,
    var edadMascota: String?,
    var razaMascota: String?,
    var especieMascota: String?,
    var fechaNacimientoMascota: String?,
    var usuario: Any?,
    var cita: ArrayList<Cita>? = null

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
        parcel.readString(),
        parcel.readString(),
        parcel.readSerializable() as Any,
        parcel.readSerializable() as ArrayList<Cita>
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
        parcel.writeString(nombreMascota)
        parcel.writeString(pesoMascota)
        parcel.writeString(edadMascota)
        parcel.writeString(razaMascota)
        parcel.writeString(especieMascota)
        parcel.writeString(fechaNacimientoMascota)
        //parcel.writeSerializable(usuario as Serializable?)
        parcel.writeValue(usuario)
        parcel.writeArray(arrayOf(cita))
        //parcel.writeSerializable(cita)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mascota> {


        val myConverter = object : Converter {

            override fun canConvert(cls: Class<*>) = cls == Mascota::class.java

            override fun toJson(value: Any): String {

                val mascota = value as Mascota
                var usuario: Any?

                if (mascota.usuario is Int) {
                    usuario = mascota.usuario
                } else if (mascota.usuario is Usuario) {
                    usuario = Klaxon().toJsonString(mascota.usuario as Usuario)
                } else {
                    throw Error("error")
                }

                return """
                  {  
                    "createdAt": ${mascota.createdAt},
                    "updatedAt": ${mascota.updatedAt},
                    "id": ${mascota.id},
                    "nombreMascota": "${mascota.nombreMascota}",
                    "pesoMascota": "${mascota.pesoMascota}",
                    "edadMascota": "${mascota.edadMascota}",
                    "razaMascota": "${mascota.razaMascota}",
                    "especieMascota": "${mascota.especieMascota}",
                    "fechaNacimientoMascota": "${mascota.fechaNacimientoMascota}",  
                    "usuario": ${usuario},
                    "cita": ${Klaxon().toJsonString(mascota.cita as List<*>)}
                   }
                """.trimMargin()
            }

            override fun fromJson(jv: JsonValue): Mascota {

                if (jv.obj?.get("usuario") is JsonObject) {

                    return Mascota(
                        jv.obj?.get("createdAt") as Long,
                        jv.obj?.get("updatedAt") as Long,
                        jv.objInt("id"),
                        jv.objString("nombreMascota"),
                        jv.objString("pesoMascota"),
                        jv.objString("edadMascota"),
                        jv.objString("razaMascota"),
                        jv.objString("especieMascota"),
                        jv.objString("fechaNacimientoMascota"),
                        Klaxon().parseFromJsonObject<Usuario>(jv.obj?.get("usuario") as JsonObject),
                        Klaxon().parseFromJsonArray<Cita>(jv.obj?.get("cita") as JsonArray<*>) as ArrayList<Cita>
                        //null

                    )
                } else if (jv.obj?.get("usuario") is Int) {
                    return Mascota(
                        jv.obj?.get("createdAt") as Long,
                        jv.obj?.get("updatedAt") as Long,
                        jv.objInt("id"),
                        jv.objString("nombreMascota"),
                        jv.objString("pesoMascota"),
                        jv.objString("edadMascota"),
                        jv.objString("razaMascota"),
                        jv.objString("especieMascota"),
                        jv.objString("fechaNacimientoMascota"),
                        jv.objInt("usuario"),
                        //null
                        Klaxon().parseFromJsonArray<Cita>(jv.obj?.get("cita") as JsonArray<*>) as ArrayList<Cita>
                    )
                } else {
                    throw Error("error")
                }

            }

        }

        override fun createFromParcel(parcel: Parcel): Mascota {
            return Mascota(parcel)
        }

        override fun newArray(size: Int): Array<Mascota?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {

        if (cita != null) {
            return  "$nombreMascota               |" +
                    "$especieMascota               |" +
                    "$razaMascota"
                    /*" |$razaMascota" + ("mascotas=$cita")*/
        } else {
        }
        return  " '$nombreMascota'     " +
                " |'$especieMascota'     " +
                " |'$razaMascota'"+("")
    }

}