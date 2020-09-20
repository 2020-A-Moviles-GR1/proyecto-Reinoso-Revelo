package com.example.proyecto_veterinaria

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_perfil_de_mascota.*

class PerfilDeMascotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_de_mascota)

        btn_agregar_nueva_cita
            .setOnClickListener {
                irRegistrarCita()
            }

        btn_editar_mascota
            .setOnClickListener {
                irEditarMascota()
            }


        btn_visualizar_historial_mascota
            .setOnClickListener {
                irVisualizarHistorial()
            }

        btn_eliminar_mascota_perfil
            .setOnClickListener {
                confirmacionDeEliminacion()
            }

        btn_back_perfil_mascota
            .setOnClickListener {
                irMenuDuenioMascota()
            }

    }

    fun irRegistrarCita(){
        val intentExplicito= Intent(
            this,
            RegistroCitaActivity::class.java
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


    fun irEditarMascota(){
        val intentExplicito= Intent(
            this,
            EditarMascotaActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun irVisualizarHistorial(){
        val intentExplicito= Intent(
            this,
            VisualizarHistorialActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun confirmacionDeEliminacion() {
        var vandera=false
        AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Está seguro que desea eliminar esta mascota?")
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