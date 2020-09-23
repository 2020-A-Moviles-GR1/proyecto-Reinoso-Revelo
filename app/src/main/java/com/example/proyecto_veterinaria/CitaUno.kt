package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

class CitaUno (
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var fechaAtencionCita: String?,
    var HoraAtencionCita: String?,
    var fechaProximaAtencionCita: String?,
    var estadoAtencionCita: String?,
    var mascota: Int,
    var vacuna: ArrayList<Vacuna>? = null,
    var diagnostico: ArrayList<Diagnostico>? = null

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readArrayList(Vacuna::class.java.classLoader) as ArrayList<Vacuna>?,
        parcel.readArrayList(Diagnostico::class.java.classLoader) as ArrayList<Diagnostico>?

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(createdAt)
        parcel.writeLong(updatedAt)
        parcel.writeInt(id)
        parcel.writeString(fechaAtencionCita)
        parcel.writeString(HoraAtencionCita)
        parcel.writeString(fechaProximaAtencionCita)
        parcel.writeString(estadoAtencionCita)
        parcel.writeInt(mascota)
        parcel.writeArray(arrayOf(vacuna))
        parcel.writeArray(arrayOf(diagnostico))
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CitaUno> {
        override fun createFromParcel(parcel: Parcel): CitaUno {
            return CitaUno(parcel)
        }

        override fun newArray(size: Int): Array<CitaUno?> {
            return arrayOfNulls(size)
        }
    }
}