package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable
import com.beust.klaxon.Converter
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonValue
import com.beust.klaxon.Klaxon
import java.util.*

class Usuario(
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var cedula: String?,
    var nombre: String?,
    var apellido: String?,
    var telefono: String?,
    var correo: String?,
    var experiencia: String?,
    var contrasenia: String?,
    var usuario: String?,
    var rol: String?,
    var mascotas: ArrayList<Mascota>? = null

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
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readSerializable() as ArrayList<Mascota>
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
        parcel.writeString(cedula)
        parcel.writeString(nombre)
        parcel.writeString(apellido)
        parcel.writeString(telefono)
        parcel.writeString(correo)
        parcel.writeString(experiencia)
        parcel.writeString(contrasenia)
        parcel.writeString(usuario)
        parcel.writeString(rol)
        parcel.writeSerializable(mascotas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {

        val myConverter = object : Converter {
            override fun canConvert(cls: Class<*>) = cls == Usuario::class.java

            override fun toJson(value: Any): String {
                val usuario = value as Usuario

                return """
                  {
                    "createdAt": ${usuario.createdAt},
                    "updatedAt": ${usuario.updatedAt},
                    "id": ${usuario.id}, 
                    "cedula": "${usuario.cedula}",
                    "nombre": "${usuario.nombre}",
                    "apellido": "${usuario.apellido}",
                    "telefono": "${usuario.telefono}",
                    "correo": "${usuario.correo}",
                    "experiencia": "${usuario.experiencia}",
                    "contrasenia": "${usuario.contrasenia}",
                    "usuario": "${usuario.usuario}",
                    "rol": "${usuario.rol}", 
                    "mascota": ${Klaxon().toJsonString(usuario.mascotas as List<*>)}
                   }
                    }
                """.trimMargin()
            }

            override fun fromJson(jv: JsonValue): Usuario {

                return Usuario(
                    jv.obj?.get("createdAt") as Long,
                    jv.obj?.get("updatedAt") as Long,
                    jv.objInt("id"),
                    jv.objString("cedula"),
                    jv.objString("nombre"),
                    jv.objString("apellido"),
                    jv.objString("telefono"),
                    jv.objString("correo"),
                    jv.objString("experiencia"),
                    jv.objString("contrasenia"),
                    jv.objString("usuario"),
                    jv.objString("rol"),
                    Klaxon().parseFromJsonArray<Mascota>(jv.obj?.get("mascota") as JsonArray<*>) as ArrayList<Mascota>
                )
            }
        }

        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {

        if (mascotas != null) {
            return "Usuario (id=$id," +
                    " createdAt=$createdAt," +
                    " updatedAt=$updatedAt," +
                    " cedula='$cedula'," +
                    " nombre='$nombre'," +
                    " correo='$correo'" + ("mascotas=$mascotas")
        } else {
        }
        return "Usuario (id=$id," +
                " createdAt=$createdAt," +
                " updatedAt=$updatedAt," +
                " cedula='$cedula'," +
                " nombre='$nombre'," +
                " correo='$correo'," +
                " fechaCreacion=$fechaCreacion," +
                " fechaActualizacion=$fechaActualizacion)" + ("")
    }

}