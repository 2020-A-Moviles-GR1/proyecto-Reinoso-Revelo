package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_editar_perfil_veterinario.*

class EditarPerfilVeterinarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil_veterinario)

        btn_editar_perfil_veterinario
            .setOnClickListener{
                irPantallaPrincipalVeterinario()
            }

        btn_back_edit_veterinario
            .setOnClickListener{
                irPantallaPrincipalVeterinario()
            }

    }


    fun irPantallaPrincipalVeterinario(){
        val intentExplicito= Intent(
            this,
            PantallaPrincipalVeterinarioActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

}