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
    val urlPrincipal = "http://192.168.0.102:1337"
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
                Log.i("MascotaEditada", "idates de la fucnionObtenrer: ${mascota.id}")
                obtenerMascotas(mascota.id)
                Handler(Looper.getMainLooper()).postDelayed({
                    irPerfilDeMascota()
                    limpiarCampos()
                }, 6000)
                //irPerfilDeMascota()
                //limpiarCampos()
            }
    }

    fun irPerfilDeMascota(){

        val intentExplicito= Intent(
            this,
            PerfilDeMascotaActivity::class.java
        )
        intentExplicito.putExtra("mascotaA",mascotasEditadas[0])
        this.startActivity(intentExplicito)
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

    fun obtenerMascotas(idMascota:Int){
        Log.i("MascotaEditada", "Nombre: ${idMascota}")
        val url = urlPrincipal + "/mascota/"+idMascota
        url
            .httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.get()
                        val mascotas= Klaxon()
                            .parseArray<MascotaDos>(data)
                        if(mascotas!=null){
                            mascotas.forEach{
                                Log.i("MascotaEditada", "Nombre: ${it.nombreMascota}  tamaño: ${it.pesoMascota}")
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