package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class MascotaDos (
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var nombreMascota: String?,
    var pesoMascota: String?,
    var edadMascota: String?,
    var razaMascota: String?,
    var especieMascota: String?,
    var fechaNacimientoMascota: String?,
    var usuario: Usuario?,
    var cita: ArrayList<CitaUno>? = null
) : Parcelable {
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
        parcel.readParcelable(Usuario::class.java.classLoader),
        parcel.readArrayList(CitaUno::class.java.classLoader) as ArrayList<CitaUno>?
    ) {
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
        parcel.writeParcelable(usuario, flags)
        parcel.writeArray(arrayOf(cita))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MascotaDos> {
        override fun createFromParcel(parcel: Parcel): MascotaDos {
            return MascotaDos(parcel)
        }

        override fun newArray(size: Int): Array<MascotaDos?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {

        return  "$nombreMascota               |" +
                "$especieMascota               |" +
                "$razaMascota"//+("citas=$cita")

    }

}