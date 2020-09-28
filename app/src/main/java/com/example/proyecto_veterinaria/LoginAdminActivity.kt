package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_login_admin.*
import kotlinx.android.synthetic.main.activity_login_veterinario.*

class LoginAdminActivity : AppCompatActivity() {
    val urlPrincipal = "http://192.168.0.115:1337"
    lateinit var listaUsuarios:ArrayList<Usuario>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)
        listaUsuarios=arrayListOf()

        btn_iniciar_secion_admin
                .setOnClickListener{
                    LoginUsuario(edt_usuario_admin.getText().toString(),edt_contrasenia_admin.getText().toString())
                }

        btn_back_loguin_admin
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

    fun irPantallaPrincipalAdmin(){
        val intentExplicito= Intent(
                this,
                PantallaAdministradorActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun consultaDeUsuarios(usuario: String,contrasenia: String){
        val url = urlPrincipal + "/usuario?usuario="+usuario+"&contrasenia="+contrasenia
        url
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            val data = result.get()
                            Log.i("http-klaxon", "Data: ${data}")
                            val usuarios= Klaxon()
                                    .parseArray<Usuario>(data)
                            if(usuarios!=null){
                                usuarios.forEach{
                                    Log.i("http-klaxon", "Nombre: ${it.usuario}  Contraseña ${it.contrasenia}")
                                    listaUsuarios.add(it)
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

    fun LoginUsuario(usuario:String,contrasenia:String){
        consultaDeUsuarios(usuario,contrasenia)
        Handler(Looper.getMainLooper()).postDelayed({
            //Do something after 1000ms
            if(listaUsuarios.isEmpty()){
                mensajeDeError()
            }else{
                if(listaUsuarios[0].usuario==usuario&&listaUsuarios[0].contrasenia==contrasenia){
                    irPantallaPrincipalAdmin()
                    mensajeIngreso()
                }else{
                    mensajeDeError()
                }
            }
        }, 1000)
    }

    fun mensajeDeError(){
        val text = "Usuario o contraseña incorrecto"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

    fun mensajeIngreso(){
        val text = "BIENVENIDO"
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }
}
