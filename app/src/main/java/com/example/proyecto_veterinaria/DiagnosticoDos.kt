package com.example.proyecto_veterinaria

import android.os.Parcel
import android.os.Parcelable

class DiagnosticoDos(
    var createdAt: Long,
    var updatedAt: Long,
    var id: Int,
    var fechaAtencionDiagnostico: String?,
    var motivoConsultaDiagnostico: String?,
    var diagnosticoAtencion: String?,
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
        parcel.writeString(fechaAtencionDiagnostico)
        parcel.writeString(motivoConsultaDiagnostico)
        parcel.writeString(diagnosticoAtencion)
        parcel.writeParcelable(cita, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DiagnosticoDos> {
        override fun createFromParcel(parcel: Parcel): DiagnosticoDos {
            return DiagnosticoDos(parcel)
        }

        override fun newArray(size: Int): Array<DiagnosticoDos?> {
            return arrayOfNulls(size)
        }
    }
}