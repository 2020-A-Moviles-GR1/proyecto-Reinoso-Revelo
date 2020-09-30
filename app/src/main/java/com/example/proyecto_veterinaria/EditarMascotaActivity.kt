package com.example.proyecto_veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_editar_mascota.*
import kotlinx.android.synthetic.main.activity_registro_cita.*

class EditarMascotaActivity : AppCompatActivity() {
    val urlPrincipal = "http://192.168.0.104:1337"
    lateinit var mascota :MascotaDos
    lateinit var mascotasEditadas :ArrayList<MascotaDos>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mascota)
        mascotasEditadas=arrayListOf()
        mascota= intent.getParcelableExtra<MascotaDos>("mascotaA")

        edt_nombre_editar_mascota.setText(mascota.nombreMascota)
        edt_peso_editar_mascota.setText(mascota.pesoMascota)
        edt_edad_editar_mascota.setText(mascota.edadMascota)
        edt_raza_editar_mascota.setText(mascota.razaMascota)
        edt_especie_editar_mascota.setText(mascota.especieMascota)
        edt_fecha_nacimiento_editar_mascota.setText(mascota.fechaNacimientoMascota)

        btn_back_editar_mascota
            .setOnClickListener {
                irPerfilDeMascota()
            }
        btn_editar_mascota
            .setOnClickListener {
                actualizarMascota()
                Handler(Looper.getMainLooper()).postDelayed({
                    obtenerMascotas()
                }, 3000)
                Handler(Looper.getMainLooper()).postDelayed({
                    irPerfilDeMascota()
                    limpiarCampos()
                }, 7000)
            }
    }

    fun irPerfilDeMascota(){
        var mascotaEditada:MascotaDos
        val intentExplicito= Intent(
            this,
            PerfilDeMascotaActivity::class.java
        )
        mascotasEditadas
            .forEach{
                if(it.id==mascota.id){
                    mascotaEditada=it
                    //Log.i("MascotaEditada", "peso antes de intent: ${mascotaEditada.pesoMascota}")
                    intentExplicito.putExtra("mascotaA",mascotaEditada)
                    this.startActivity(intentExplicito)
                }
            }
        val peso=mascotasEditadas[0].pesoMascota
        //Log.i("MascotaEditada", "peso antes de intent: ${peso}")

    }

    fun actualizarMascota() {

        val nombreMas=edt_nombre_editar_mascota.getText().toString()
        val pesoMas=edt_peso_editar_mascota.getText().toString()
        val edadMas= edt_edad_editar_mascota.getText().toString()
        val razaMas= edt_raza_editar_mascota.getText().toString()
        val especieMas=edt_especie_editar_mascota.getText().toString()
        val fechaNaciMas=edt_fecha_nacimiento_editar_mascota.getText().toString()

        val url = urlPrincipal + "/mascota/"+mascota.id
        val parametroMascota: List<Pair<String, String>> = listOf( //lista de clave valores

            "nombreMascota" to "${nombreMas}",
            "pesoMascota" to "${pesoMas}",
            "edadMascota" to "${edadMas}",
            "razaMascota" to "${razaMas}",
            "especieMascota" to "${especieMas}",
            "fechaNacimientoMascota" to "${fechaNaciMas}"
        )
        url
            .httpPut(parametroMascota)
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val error = result.getException()
                        Log.i("http-klaxon", "Nombre: ${error}")
                    }
                    is Result.Success -> {
                        val mascotaString = result.get()
                        Log.i("http-klaxon", "Nombre: ${mascotaString}")
                    }
                }
            }
    }

    fun limpiarCampos(){
        edt_nombre_editar_mascota.setText(null)
        edt_peso_editar_mascota.setText(null)
        edt_edad_editar_mascota.setText(null)
        edt_raza_editar_mascota.setText(null)
        edt_especie_editar_mascota.setText(null)
        edt_fecha_nacimiento_editar_mascota.setText(null)
    }

    fun obtenerMascotas(){
        //Log.i("MascotaEditada", "antes de peticion: ${idMascota}")
        //val url = urlPrincipal + "/mascota/"+idMascota
        val url = urlPrincipal + "/mascota"
        url
            .httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.get()
                        Log.i("MascotaEditada", "antes del parse de Klaxon: ${data}")
                        val mascotas= Klaxon()
                            .parseArray<MascotaDos>(data)
                        Log.i("MascotaEditada", "despues del parse Klaxon: ${mascotas}")
                        if(mascotas!=null){
                            mascotas.forEach{
                                Log.i("MascotaEditada", "tras en peticion: ${it.nombreMascota}  tamaño: ${it.pesoMascota}")
                                mascotasEditadas.add(it)
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