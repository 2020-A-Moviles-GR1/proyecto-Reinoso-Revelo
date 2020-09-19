package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registro_cliente.*

class RegistroClienteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)

        btn_registrar_cliente
            .setOnClickListener{
                irInicioSesionCliente()
            }

        btn_back_registro_cliente
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

    fun irInicioSesionCliente(){
        val intentExplicito= Intent(
            this,
            LoginClienteActivity::class.java
        )
        this.startActivity(intentExplicito)
    }





}