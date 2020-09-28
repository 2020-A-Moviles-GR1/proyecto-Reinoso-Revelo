package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Usuario (
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
    var mascotas: ArrayList<MascotaDos>? = null
):Parcelable{

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
        parcel.readArrayList(MascotaDos::class.java.classLoader) as ArrayList<MascotaDos>?
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
        parcel.writeArray(arrayOf(mascotas))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }


    override fun toString(): String {

        return  "$usuario               |" +
                "$nombre               |" +
                "$apellido"//+("citas=$cita")

    }


}