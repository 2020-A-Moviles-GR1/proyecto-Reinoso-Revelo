package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_editar_mascota.*
import kotlinx.android.synthetic.main.activity_editar_perfil_veterinario.*

class EditarPerfilVeterinarioActivity : AppCompatActivity() {
    val urlPrincipal = "http://192.168.0.104:1337"
    lateinit var listaCitas: ArrayList<CitaDos>
    lateinit var adaptador: ArrayAdapter<CitaDos>
    lateinit var usuario: Usuario
    lateinit var usuarioEditado: Usuario
    lateinit var usuariosEditados: ArrayList<Usuario>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil_veterinario)

        listaCitas = arrayListOf()
        adaptador = ArrayAdapter(
                this,//contexto
                android.R.layout.simple_list_item_1,//nombre layout
                listaCitas//lista
        )
        usuariosEditados = arrayListOf()
        usuario = intent.getParcelableExtra<Usuario>("usuarioA")

        edt_cedula_editad_veterinario.setText((usuario.cedula))
        edt_nombre_editad_veterinario.setText(usuario.nombre)
        edt_apellido_editad_veterinario.setText(usuario.apellido)
        edt_celular_editad_veterinario.setText(usuario.telefono)
        edt_anios_editad_veterinario.setText(usuario.experiencia)
        edt_usuario_editad_veterinario.setText(usuario.usuario)
        edt_contrasenia_editad_veterinario.setText(usuario.contrasenia)

        btn_back_edit_veterinario
                .setOnClickListener {
                    irPantallaPrincipalVeterinario()
                }

        btn_editar_perfil_veterinario
                .setOnClickListener {
                    actualizarVeterinario()
                    Handler(Looper.getMainLooper()).postDelayed({
                        obtenerVeterinario()
                    }, 1000)
                    Handler(Looper.getMainLooper()).postDelayed({
                        irPantallaPrincipalVeterinario()
                        limpiarCampos()
                    }, 1000)
                }
    }

    fun obtenerCitas() {
        val url = urlPrincipal + "/cita"
        url
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            val data = result.get()
                            Log.i("http-L", "Data: ${data}")
                            val citas = Klaxon()
                                    .parseArray<CitaDos>(data)
                            if (citas != null) {
                                citas.forEach {
                                    Log.i("http-klaxon", "Estado: ${it.estadoAtencionCita}  Mascota: ${it.mascota?.nombreMascota} Raza: ${it.mascota?.razaMascota}")
                                    listaCitas.add(it)
                                }
                                runOnUiThread(Runnable {
                                    adaptador.notifyDataSetChanged()
                                })
                            }
                        }
                        is Result.Failure -> {
                            val ex = result.getException()
                            Log.i("http-klaxon", "Error: ${ex.message}")
                        }
                    }
                }
    }

    fun irPantallaPrincipalVeterinario(){
        var cita: CitaDos
        val intentExplicito = Intent(
              this,
            PantallaPrincipalVeterinarioActivity::class.java
        )
        listaCitas
              .forEach {
                  cita = it
                  Log.i("Citas", "peso antes de intent: ${cita.estadoAtencionCita}")
                  intentExplicito.putExtra("citaA", cita)
        }
        intentExplicito.putExtra("usuarioA",usuario)
        this.startActivity(intentExplicito)
    }

    fun actualizarVeterinario() {

        val cedulaV = edt_cedula_editad_veterinario.getText().toString()
        val nombreV = edt_nombre_editad_veterinario.getText().toString()
        val apellidoV = edt_apellido_editad_veterinario.getText().toString()
        val celularV = edt_celular_editad_veterinario.getText().toString()
        val experienciaV = edt_anios_editad_veterinario.getText().toString()
        val usuarioV = edt_usuario_editad_veterinario.getText().toString()
        val contraseniaV = edt_contrasenia_editad_veterinario.getText().toString()

        val url = urlPrincipal + "/usuario/"+usuario.id
        val parametroUsuario: List<Pair<String, String>> = listOf( //lista de clave valores

                "cedula" to "${cedulaV}",
                "nombre" to "${nombreV}",
                "apellido" to "${apellidoV}",
                "telefono" to "${celularV}",
                "esperiencia" to "${experienciaV}",
                "usuario" to "${usuarioV}",
                "contrasenia" to "${contraseniaV}"
        )
        url
                .httpPut(parametroUsuario)
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val error = result.getException()
                            Log.i("http-klaxon", "Nombre: ${error}")
                        }
                        is Result.Success -> {
                            val usuarioString = result.get()
                            Log.i("http-klaxon", "Nombre usuario: ${usuarioString}")
                        }
                    }
                }
    }

    fun limpiarCampos() {
        edt_cedula_editad_veterinario.setText((null))
        edt_nombre_editad_veterinario.setText(null)
        edt_apellido_editad_veterinario.setText(null)
        edt_celular_editad_veterinario.setText(null)
        edt_anios_editad_veterinario.setText(null)
        edt_usuario_editad_veterinario.setText(null)
        edt_contrasenia_editad_veterinario.setText(null)
    }

    fun obtenerVeterinario() {
        //Log.i("MascotaEditada", "antes de peticion: ${idMascota}")
        //val url = urlPrincipal + "/mascota/"+idMascota
        val url = urlPrincipal + "/usuario"
        url
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            val data = result.get()
                            Log.i("UsuarioEditado", "antes del parse de Klaxon: ${data}")
                            val usuarios = Klaxon()
                                    .parseArray<Usuario>(data)
                            Log.i("UsuarioEditado2", "despues del parse Klaxon: ${usuarios}")
                            if (usuarios != null) {
                                usuarios.forEach {
                                    Log.i("UsuarioEditado", "tras en peticion: ${it.nombre}  rol: ${it.apellido}")
                                    usuariosEditados.add(it)
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