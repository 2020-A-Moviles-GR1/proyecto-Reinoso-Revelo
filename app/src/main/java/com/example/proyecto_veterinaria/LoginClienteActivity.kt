package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_cliente.*

class LoginClienteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_cliente)

        btn_back_loguin_cliente
            .setOnClickListener{
                irInicio()
            }
        btn_iniciar_secion_cliente
            .setOnClickListener{
                irMenuDuenioMascota()
            }

    }

    //continua aqui-Menu dueño mascota
    fun irInicio(){
        val intentExplicito= Intent(
            this,
            MainActivity::class.java
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

}