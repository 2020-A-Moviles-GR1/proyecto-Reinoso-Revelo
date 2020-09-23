package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable

class DiagnosticoUno(
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var fechaAtencionDiagnostico: String?,
    var motivoConsultaDiagnostico: String?,
    var diagnosticoAtencion: String?,
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
        parcel.writeString(fechaAtencionDiagnostico)
        parcel.writeString(motivoConsultaDiagnostico)
        parcel.writeString(diagnosticoAtencion)
        parcel.writeInt(cita)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DiagnosticoUno> {
        override fun createFromParcel(parcel: Parcel): DiagnosticoUno {
            return DiagnosticoUno(parcel)
        }

        override fun newArray(size: Int): Array<DiagnosticoUno?> {
            return arrayOfNulls(size)
        }
    }
}