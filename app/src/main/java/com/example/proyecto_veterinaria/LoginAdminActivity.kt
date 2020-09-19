package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_admin.*

class LoginAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)
        btn_iniciar_secion_admin
            .setOnClickListener{
                irPantallaAdmin()
            }

        btn_back_loguin_admin
            .setOnClickListener{
                irInicio()
            }

    }

    fun irPantallaAdmin(){
        val intentExplicito= Intent(
            this,
            PantallaAdministradorActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun irInicio(){
        val intentExplicito= Intent(
            this,
            MainActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

}