package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_registro_cliente.*
import kotlinx.android.synthetic.main.activity_registro_veterinario.*

class RegistroVeterinarioActivity : AppCompatActivity() {
    val urlPrincipal = "http://192.168.0.104:1337"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_veterinario)

        btn_registrar_veterinario
            .setOnClickListener{
                //irInicioSesionVeterinario()
                registrarVeterinario()
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


    fun obtenerValoresObj():Usuario{
        val cedulaVet=edt_cedula_registro_veterinario.getText().toString()
        val nombreVet=edt_nombre_registro_veterinario.getText().toString()
        val apellidoVet=edt_apellido_registro_veterinario.getText().toString()
        val celularVet=edt_celular_registro_veterinario.getText().toString()
        val experienciaVet=edt_experi_registro_veterinario.getText().toString()
        val usuarioVet=edt_usuario_registro_veterinario.getText().toString()
        val contraseniaVet =edt_contraseni_registro_veterinario.getText().toString()
        val rol = "Veterinario"
        val correoVet=""
        val createdAt=12345325346
        val updatedAt=12345234234
        val id=0

        val usuarioVeterinario=Usuario(
                                    createdAt,
                                    updatedAt,
                                    id,
                                    cedulaVet,
                                    nombreVet,
                                    apellidoVet,
                                    celularVet,
                                    correoVet,
                                    experienciaVet,
                                    contraseniaVet,
                                    usuarioVet,
                                    rol,
                            null
        )

        return usuarioVeterinario
    }

    fun registrarVeterinario(){
        val usuario=obtenerValoresObj()
        val url = urlPrincipal + "/usuario"

        val parametroUsuario : List<Pair<String,String>> = listOf( //lista de clave valores
        "cedula" to "${usuario.cedula}",
        "nombre" to "${usuario.nombre}",
        "apellido" to "${usuario.apellido}",
        "telefono"  to "${usuario.telefono}",
        "correo" to  "${usuario.correo}",
        "experiencia" to "${usuario.experiencia}",
        "contrasenia" to  "${usuario.contrasenia}",
        "usuario" to "${usuario.usuario}",
        "rol" to "${usuario.rol}"
        )
        url
            .httpPost(parametroUsuario)
            .responseString{request, response, result ->
                when (result){
                    is Result.Failure->{
                        val error = result.getException()
                        Log.i("http-klaxon", "Nombre: ${error}")
                    }
                    is Result.Success->{
                        val usuarioString=result.get()
                        Log.i("http-klaxon", "Nombre: ${usuarioString}")
                    }
                }
            }
        limpiarCampos()
        irInicioSesionVeterinario()
    }

    fun limpiarCampos(){
        edt_cedula_registro_veterinario.setText(null)
        edt_nombre_registro_veterinario.setText(null)
        edt_apellido_registro_veterinario.setText(null)
        edt_celular_registro_veterinario.setText(null)
        edt_experi_registro_veterinario.setText(null)
        edt_usuario_registro_veterinario.setText(null)
        edt_contraseni_registro_veterinario.setText(null)
    }
}