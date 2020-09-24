package com.example.proyecto_veterinaria

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_perfil_de_mascota.*

class PerfilDeMascotaActivity : AppCompatActivity() {
    lateinit var mascota: MascotaDos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_de_mascota)


        mascota= intent.getParcelableExtra<MascotaDos>("mascotaA")
        Log.i("List","position ${mascota.pesoMascota}")


        tv_nombre_perfil_mascota.setText("Nombre: " + mascota.nombreMascota)
        tv_peso_perfil_mascota.setText("Peso: " + mascota.pesoMascota)
        tv_edad_perfil_mascota.setText("Edad: " + mascota.edadMascota)
        tv_raza_perfil_mascota.setText("Raza: " + mascota.razaMascota)
        tv_especie_perfil_mascota.setText("Especie: " + mascota.especieMascota)
        tv_fecha_nacimiento_perfil_mascota.setText("Fecha de nacimiento :" + mascota.fechaNacimientoMascota)

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
        intentExplicito.putExtra("mascotaA",mascota)
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
        intentExplicito.putExtra("mascotaA",mascota)
        this.startActivity(intentExplicito)
    }

    fun irVisualizarHistorial(){
        val intentExplicito= Intent(
            this,
            VisualizarHistorialActivity::class.java
        )
        intentExplicito.putExtra("mascotaA",mascota)
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