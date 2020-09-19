package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro_cliente.*
import kotlinx.android.synthetic.main.activity_registro_veterinario.*

class RegistroVeterinarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_veterinario)

        btn_registrar_veterinario
            .setOnClickListener{
                irInicioSesionVeterinario()
            }

        btn_back_registro_veterinario
            .setOnClickListener{
                irInicio()
            }
    }

    fun irInicio(){
        val intentExplicito= Intent(
            this,
            MainActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun irInicioSesionVeterinario(){
        val intentExplicito= Intent(
            this,
            LoginVeterinarioActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

}