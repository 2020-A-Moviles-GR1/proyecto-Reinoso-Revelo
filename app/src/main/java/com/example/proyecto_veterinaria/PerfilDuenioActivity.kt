package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_perfil_duenio.*

class PerfilDuenioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_duenio)

        btn_back_perfil_duenio
            .setOnClickListener {
                irMenuDuenioMascota()
            }

        btn_editar_perfil_duenio
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