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
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_menu_duenio_mascota.*
import kotlinx.android.synthetic.main.activity_visualizar_historial.*
import kotlin.math.log

class VisualizarHistorialActivity : AppCompatActivity() {
    val urlPrincipal = "http://192.168.0.102:1337"
    lateinit var mascota :MascotaDos
    lateinit var listaDiagnostico:ArrayList<DiagnosticoDos>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_historial)
        listaDiagnostico=arrayListOf()
        mascota= intent.getParcelableExtra<MascotaDos>("mascotaA")

        obtenerDiagnosticos()
        Handler(Looper.getMainLooper()).postDelayed({
            val listaDiagnosticoMascota:ArrayList<DiagnosticoDos>
            listaDiagnosticoMascota = listaDiagnostico
            .filter {
                it.cita!!.mascota==mascota.id

            } as ArrayList<DiagnosticoDos>

        val adaptador= ArrayAdapter(
            this,//contexto
            android.R.layout.simple_list_item_1,//nombre layout
            listaDiagnosticoMascota//lista
        )
        lv_historial_citas.adapter=adaptador
        Log.i("despues de consulta", "Nombre: ${listaDiagnosticoMascota} ")
        }, 3000)

        //adaptador.notifyDataSetChanged()



        btn_back_visualizar_historial
            .setOnClickListener {
                irPerfilDeMascota()
            }
    }

    fun irPerfilDeMascota(){
        val intentExplicito= Intent(
            this,
            PerfilDeMascotaActivity::class.java
        )
        this.startActivity(intentExplicito)
    }

    fun obtenerDiagnosticos() {
        val url = urlPrincipal + "/diagnostico"
        url
            .httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Success -> {
                        val data = result.get()
                        Log.i("http-L", "Data: ${data}")
                        val mascotas= Klaxon()
                            //.converter(Mascota.myConverter)
                            //.parseArray<Mascota>(data)
                            .parseArray<DiagnosticoDos>(data)
                        if(mascotas!=null){
                            mascotas.forEach{
                                Log.i("http-klaxon", "Nombre: ${it.diagnosticoAtencion}  tamaño: ${it.motivoConsultaDiagnostico}")
                                listaDiagnostico.add(it)
                            }
                            //runOnUiThread(Runnable {
                            //    adaptador.notifyDataSetChanged()
                            //})
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