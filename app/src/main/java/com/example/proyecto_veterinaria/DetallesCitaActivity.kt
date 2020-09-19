package com.example.proyecto_veterinaria

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_detalles_cita.*

class DetallesCitaActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_cita)

        edt_fecha_cita
            .setOnClickListener {
            showDatePickerDialog()
        }

        btn_guardar_detalle_cita
            .setOnClickListener{
                confirmacionGuardado()
            }
        back_detalle_cita
            .setOnClickListener{
                irPantallaPrincipalVeterinario()
            }


    }

    fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            edt_fecha_cita.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }


    fun confirmacionGuardado() {
        var vandera=false
        AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Está seguro que desea guardar la información ingresada?")
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón OK pulsado**
                    val intentExplicito= Intent(
                        this,
                        PantallaPrincipalVeterinarioActivity::class.java
                    )
                    this.startActivity(intentExplicito)
                    //this.startActivity(intent)
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón cancel pulsado**
                    vandera=false
                    //Log.i("List","position $vandera")
                })
            .show()
    }

    fun irPantallaPrincipalVeterinario(){
        val intentExplicito= Intent(
            this,
            PantallaPrincipalVeterinarioActivity::class.java
        )
        this.startActivity(intentExplicito)
    }


}