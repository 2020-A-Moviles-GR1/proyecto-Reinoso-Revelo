package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val urlPrincipal = "http://192.168.0.103:1337"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cargarSipinerSesion()
        cargarSipinerRegistr()

        btn_inicio_secion_admin
            .setOnClickListener{

                val spinerIngreso = findViewById(R.id.sp_inicio_sesion) as Spinner
                val text = spinerIngreso.selectedItem.toString()
                if(text=="Veterinario"){
                    irLoguinVeterinario()
                }else if(text=="Cliente"){
                    obtenerUsuarios()
                    irLoguinCliente()
                }else if(text=="Administrador"){
                    irLoguinAdmin()
                }
            }

        btn_registro_admin
            .setOnClickListener{
                val spinerRegistro = findViewById(R.id.sp_registro) as Spinner
                val text = spinerRegistro.selectedItem.toString()
                if(text=="Veterinario"){
                    irRegistrarVeterinario()
                }else if(text=="Cliente"){
                    irRegistrarCliente()
                }
            }

    }


    fun cargarSipinerSesion(){
        val spinner: Spinner = findViewById(R.id.sp_inicio_sesion)
        ArrayAdapter.createFromResource(
            this,
            R.array.itemsInicio,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun cargarSipinerRegistr(){
        val spinner: Spinner = findViewById(R.id.sp_registro)
        ArrayAdapter.createFromResource(
            this,
            R.array.itemsRegistro,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun irLoguinAdmin(){
        val intentExplicito= Intent(
            this,
            LoginAdminActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun irLoguinVeterinario(){
        val intentExplicito= Intent(
            this,
            LoginVeterinarioActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun irLoguinCliente(){
        val intentExplicito= Intent(
            this,
            LoginClienteActivity::class.java
        )
        this.startActivity(intentExplicito)
    }



    fun irRegistrarVeterinario(){
        val intentExplicito= Intent(
            this,
            RegistroVeterinarioActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun irRegistrarCliente(){
        val intentExplicito= Intent(
            this,
            RegistroClienteActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun obtenerUsuarios() {

        val url = urlPrincipal + "/usuario"
        url
            .httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.get()
                        // Log.i("http-klaxon", "Data: ${data}")

                        val usuarios= Klaxon()
                            .parseArray<Usuario>(data)

                        if(usuarios!=null){
                            usuarios.forEach{
                                Log.i("http-klaxon", "Nombre: ${it.nombre}  apellido ${it.apellido}")
                            }
                        }
                    }

                    is Result.Failure -> {
                        val ex = result.getException()
                        Log.i("http-klaxon", "Error: ${ex.message}")
                    }
                }
            }
    }




}