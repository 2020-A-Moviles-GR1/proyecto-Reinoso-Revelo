package com.example.proyecto_veterinaria

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_pantalla_administrador.*
import kotlinx.android.synthetic.main.activity_pantalla_principal_veterinario.*
import android.widget.ArrayAdapter as ArrayAdapter1

class PantallaAdministradorActivity : AppCompatActivity() {
    val urlPrincipal = "http://192.168.0.115:1337"
    lateinit var listaVeterinarios : ArrayList<Usuario>
    lateinit var adaptador: ArrayAdapter1<Usuario>
    lateinit var usuarioA: Usuario
    lateinit var listaUsuarioUnico: ArrayList<Usuario>
    var posicion:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_administrador)
        listaVeterinarios = arrayListOf()
        listaUsuarioUnico = arrayListOf()
        usuarioA = intent.getParcelableExtra<Usuario>("usuarioA")
        adaptador = ArrayAdapter1(
                this,//contexto
                android.R.layout.simple_list_item_1,//nombre layout
                listaVeterinarios//lista
        )
        lv_veterinarios_pant_admin.adapter = adaptador
        lv_veterinarios_pant_admin
                .onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.i("List", "position $position")
            posicion = position
            val veterinario = listaVeterinarios.get(posicion)
            Log.i("http-klaxon", "Veterinario Select:  ${veterinario}")
            //irPerfilDeMascota()
        }
        obtenerVeterinarios()
        btn_eliminra_veterinario_pant_adm
            .setOnClickListener{
                confirmacionDeEliminacion(posicion)//cambia por posicion del listview
            }
        btn_back_pantalla_admin
            .setOnClickListener{
                confirmacionDeSalida()
            }
    }

    fun obtenerVeterinarios() {
        val url = urlPrincipal + "/usuario"
        url
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Success -> {
                            val data = result.get()
                            Log.i("http-L", "Data: ${data}")
                            val veterinarios = Klaxon()
                                    .parseArray<Usuario>(data)
                            if (veterinarios != null) {
                                veterinarios.forEach {
                                    Log.i("http-klaxon", "Nombre: ${it.nombre} Apellido: ${it.apellido}")
                                    listaVeterinarios.add(it)
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

    fun eliminarVeterinario(identificador:Int){
            val url = urlPrincipal + "/usuario/"+identificador
            url
                    .httpDelete()
                    .responseString { request, response, result ->
                        when (result) {
                            is Result.Failure -> {
                                val ex = result.getException()
                                Log.i("http-klaxon", "Error: ${ex.message}")
                            }
                            is Result.Success -> {
                                val data = result.get()
                                Log.i("http-L", "Data: ${data}")
                            }
                        }
                    }
            adaptador.notifyDataSetChanged()
    }

    fun confirmacionDeEliminacion(pos:Int) {
        var vandera=false
        AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Está seguro que desea eliminar este veterinario?")
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón OK pulsado**
                    //val indiceBorra=pos
                    //ServicBDDMemoria.eliminarAlien(indiceBorra)
                    //vandera=true
                    //eliminarUniversoHttp(pos)
                    eliminarVeterinario(pos)
                    this.startActivity(intent)
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón cancel pulsado**
                    vandera=false
                    //Log.i("List","position $vandera")
                })
            .show()
    }

    fun confirmacionDeSalida() {
        var vandera=false
        AlertDialog.Builder(this)
            .setTitle("Confirmación")
            .setMessage("¿Está seguro que desea eliminar este veterinario?")
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón OK pulsado**
                    val intentExplicito= Intent(
                        this,
                        MainActivity::class.java
                    )
                    this.startActivity(intentExplicito)
                    //this.startActivity(intent)
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                    //botón cancel pulsado**
                    vandera=false
                    //Log.i("List","position $vandera")
                })
            .show()
    }
}