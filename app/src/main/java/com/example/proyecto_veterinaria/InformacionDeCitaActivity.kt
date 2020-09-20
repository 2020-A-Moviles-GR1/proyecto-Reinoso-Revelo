package com.example.proyecto_veterinaria

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_informacion_de_cita.*

class InformacionDeCitaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_de_cita)

        btn_editar_cita
            .setOnClickListener {
                irEditarCita()
            }
        btn_eliminar_cita
            .setOnClickListener {
                confirmacionEliminacion()
            }
        btn_back_info_cita
            .setOnClickListener {
                irMenuDuenioMascota()
            }

    }


    fun irEditarCita(){
        val intentExplicito= Intent(
            this,
            EditarCitaActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun irMenuDuenioMascota(){
        val intentExplicito= Intent(
            this,
            MenuDuenioMascotaActivity::class.java
        )
        this.startActivity(intentExplicito)
    }


    fun confirmacionEliminacion() {
        var vandera=false
        AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Está seguro que desea eliminar esta cita?")
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón OK pulsado**
                    val intentExplicito= Intent(
                        this,
                        MenuDuenioMascotaActivity::class.java
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



}