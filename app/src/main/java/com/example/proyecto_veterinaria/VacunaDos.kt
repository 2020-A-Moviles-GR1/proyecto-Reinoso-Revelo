package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable

class VacunaDos(
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var fechaVacuna: String?,
    var tipoVacuna: String?,
    var numDosisVacuna: String?,
    var cita: CitaUno?
) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(CitaUno::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(createdAt)
        parcel.writeLong(updatedAt)
        parcel.writeInt(id)
        parcel.writeString(fechaVacuna)
        parcel.writeString(tipoVacuna)
        parcel.writeString(numDosisVacuna)
        parcel.writeParcelable(cita, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VacunaDos> {
        override fun createFromParcel(parcel: Parcel): VacunaDos {
            return VacunaDos(parcel)
        }

        override fun newArray(size: Int): Array<VacunaDos?> {
            return arrayOfNulls(size)
        }
    }
}