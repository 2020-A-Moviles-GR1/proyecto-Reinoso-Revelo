package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro_cliente.*
import kotlinx.android.synthetic.main.activity_registro_mascota.*

class RegistroMascotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_mascota)
        btn_back_registro_mascota
            .setOnClickListener {
                irMenuDuenioMascota()
            }
        btn_registrar_mascota
            .setOnClickListener {
                irMenuDuenioMascota()
            }

    }


    fun irMenuDuenioMascota(){
        val intentExplicito= Intent(
            this,
            MenuDuenioMascotaActivity::class.java
        )
        this.startActivity(intentExplicito)
    }
}