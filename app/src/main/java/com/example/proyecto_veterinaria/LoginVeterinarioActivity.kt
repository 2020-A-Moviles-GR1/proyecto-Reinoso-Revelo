package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_veterinario.*

class LoginVeterinarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_veterinario)

        btn_iniciar_secion_veterinario
            .setOnClickListener{
                irPantallaPrincipalVeterinario()
            }

        btn_back_loguin_veterinario
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

    fun irPantallaPrincipalVeterinario(){
        val intentExplicito= Intent(
            this,
            PantallaPrincipalVeterinarioActivity::class.java
        )
        this.startActivity(intentExplicito)
    }


}