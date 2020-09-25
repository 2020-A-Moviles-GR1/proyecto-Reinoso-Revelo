package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable

class VacunaUno(
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var fechaVacuna: String?,
    var tipoVacuna: String?,
    var numDosisVacuna: String?,
    var cita: Int
) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(createdAt)
        parcel.writeLong(updatedAt)
        parcel.writeInt(id)
        parcel.writeString(fechaVacuna)
        parcel.writeString(tipoVacuna)
        parcel.writeString(numDosisVacuna)
        parcel.writeInt(cita)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VacunaUno> {
        override fun createFromParcel(parcel: Parcel): VacunaUno {
            return VacunaUno(parcel)
        }

        override fun newArray(size: Int): Array<VacunaUno?> {
            return arrayOfNulls(size)
        }
    }
}